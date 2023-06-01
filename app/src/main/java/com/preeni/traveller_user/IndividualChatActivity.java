package com.preeni.traveller_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.preeni.traveller_user.adapter.ChatIndividualAdapter;
import com.preeni.traveller_user.adapter.ChatUsersAdapter;
import com.preeni.traveller_user.adapter.ImagesAdapter;
import com.preeni.traveller_user.constant.Constants;
import com.preeni.traveller_user.model.ImagesDataList;
import com.preeni.traveller_user.model.Massage;
import com.preeni.traveller_user.model.MassageData;
import com.preeni.traveller_user.model.User;
import com.preeni.traveller_user.network.ApiClient;
import com.preeni.traveller_user.network.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualChatActivity extends AppCompatActivity {

    private ImageView iv_send;
    private EditText et_chat_text;
    private RecyclerView rv_chat;
    private TextView tv_name;
    private ImageView iv_menu;

    String user_name;
    String name;
    String receiverId, senderId;
    String senderRoom, receiverRoom;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String fcmToken ="";
    private String res_fcm_token ="";

    private ArrayList<Map<String, String>> chatMap = new ArrayList<>();
    private ArrayList<Massage> massagesList = new ArrayList<>();
    private ChatIndividualAdapter chatIndividualAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_chat);

        rv_chat = findViewById(R.id.rv_chat);
        iv_send = findViewById(R.id.iv_send);
        et_chat_text = findViewById(R.id.et_chat_text);
        tv_name = findViewById(R.id.tv_name);
        iv_menu = findViewById(R.id.iv_menu);

        receiverId = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        res_fcm_token = getIntent().getStringExtra("res_fcm_token");
        user_name = getIntent().getStringExtra("user_name");
        if (name != null){
            tv_name.setText(name);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        senderRoom = FirebaseAuth.getInstance().getUid()+receiverId;
        receiverRoom = receiverId+FirebaseAuth.getInstance().getUid();

        senderId = firebaseAuth.getUid();
        SharedPreferences prefs = getSharedPreferences(Constants.SHARE_PREFERENCE_TAG, MODE_PRIVATE);
        fcmToken = prefs.getString(Constants.FCM_TOKEN, "");
        Log.d("TAG", "onCreate: fcmToken " + fcmToken);

        rv_chat.setLayoutManager(new LinearLayoutManager(IndividualChatActivity.this));
        chatIndividualAdapter = new ChatIndividualAdapter(IndividualChatActivity.this, massagesList, senderId);
        rv_chat.setAdapter(chatIndividualAdapter);

       firebaseFirestore
               .collection("chats")
               .whereEqualTo("senderId", senderId)
               .whereEqualTo("receiverId", receiverId)
               .addSnapshotListener(eventListener);
        firebaseFirestore
                .collection("chats")
                .whereEqualTo("senderId", receiverId)
                .whereEqualTo("receiverId", senderId)
                .addSnapshotListener(eventListener);

        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String massage = et_chat_text.getText().toString().trim();
                if (massage.length() > 0){
                    sendMassage(massage);
                }
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("user_id", senderId);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });
    }

    private final EventListener<QuerySnapshot> eventListener = ((value, error) -> {
        if (error != null){
            return;
        }
        else {
            int count = massagesList.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                //if (documentChange.getType() == DocumentChange.Type.ADDED){
                    Massage chatMassage = new Massage();
                    chatMassage.setSenderId(documentChange.getDocument().getString("senderId"));
                    chatMassage.setReceiverId(documentChange.getDocument().getString("receiverId"));
                    chatMassage.setMassage(documentChange.getDocument().getString("massage"));
                    chatMassage.setTimestamp(documentChange.getDocument().getString("timestamp"));
                    massagesList.add(chatMassage);
              //  }
            }
            Collections.sort(massagesList, (obj1, obj2) -> obj1.getTimestamp().compareTo(obj2.getTimestamp()));
            if (count == 0){
                chatIndividualAdapter.notifyDataSetChanged();
            }
            else {
                chatIndividualAdapter.notifyItemRangeInserted(massagesList.size(), massagesList.size());
                rv_chat.smoothScrollToPosition(massagesList.size() - 1);
            }

            rv_chat.setVisibility(View.VISIBLE);
        }
    });

    private void sendMassage(String massage) {
        Massage massageModel = new Massage(receiverId, FirebaseAuth.getInstance().getUid(), massage, String.valueOf(System.currentTimeMillis()/1000));
        DocumentReference docRefReceive = firebaseFirestore.collection("chats").document();
        docRefReceive.set(massageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                try {
                    JSONArray tokens = new JSONArray();
                    tokens.put(res_fcm_token);

                    JSONObject data = new JSONObject();
                    data.put("user_id", firebaseAuth.getUid());
                    data.put("name", user_name);
                    data.put(Constants.FCM_TOKEN, fcmToken);
                    data.put(Constants.KEY_MESSAGE, et_chat_text.getText().toString());

                    JSONObject body = new JSONObject();
                    body.put(Constants.REMOTE_MSG_DATA, data);
                    body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

                    sendNotification(body.toString());
                    et_chat_text.setText("");

                }catch (Exception exception){
                    showToast(exception.getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void showToast(String massage){
        Toast.makeText(getApplicationContext(), massage, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String massageBody){
        ApiClient.getClient().create(ApiService.class).sendMassage(
                Constants.getRemoteMsgHeaders(),
                massageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    try {
                        if (response.body() != null){
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray result = responseJson.getJSONArray("results");
                            if (responseJson.getInt("failure") == 1){
                                JSONObject error = (JSONObject) result.get(0);
                                showToast(error.getString("error"));
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                  //  showToast("Notification sent successfully");
                }
                else {
                    showToast("Error: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }
}
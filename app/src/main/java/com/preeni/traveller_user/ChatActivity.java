package com.preeni.traveller_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.preeni.traveller_user.adapter.ChatUsersAdapter;
import com.preeni.traveller_user.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ImageView iv_menu;
    private RecyclerView rv_user;

    private String user_id = "";
    private String user_name = "";

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    private ArrayList<User> users_list_all = new ArrayList<>();
    private ArrayList<User> users_list = new ArrayList<>();

    private ChatUsersAdapter chatUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        iv_menu = findViewById(R.id.iv_menu);
        rv_user = findViewById(R.id.rv_user);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        user_name = getIntent().getStringExtra("user_name");
        user_id = getIntent().getStringExtra("user_id");
        progressDialog = new ProgressDialog(this);

        getUsers();

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });
    }

    private void getUsers() {

       firebaseFirestore.collection("users").get().
               addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       if (queryDocumentSnapshots.isEmpty()) {
                           Log.d("TAG", "onSuccess: LIST EMPTY");
                           return;
                       } else {
                           // Convert the whole Query Snapshot to a list
                           // of objects directly! No need to fetch each
                           // document.
                           List<User> types = queryDocumentSnapshots.toObjects(User.class);
                           users_list_all.addAll(types);
                           // Add all to your list
                           for (User user : users_list_all) {
                               if (!user_id.equals(user.getUser_id())){
                                   users_list.add(user);
                               }
                           }
                           Log.d("TAG", "onSuccess: " + users_list);
                           rv_user.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                           chatUsersAdapter = new ChatUsersAdapter(ChatActivity.this, users_list, new ChatUsersAdapter.itemClickListner() {
                               @Override
                               public void onItemClick(int position) {
                                   Intent intent = new Intent(getApplicationContext(), IndividualChatActivity.class);
                                   intent.putExtra("id", users_list.get(position).getUser_id());
                                   intent.putExtra("name", users_list.get(position).getName());
                                   intent.putExtra("user_name", user_name);
                                   intent.putExtra("res_fcm_token", users_list.get(position).getFcmToken());
                                   startActivity(intent);
                               }
                           });
                           rv_user.setAdapter(chatUsersAdapter);

                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {

                   }
               });
    }
}
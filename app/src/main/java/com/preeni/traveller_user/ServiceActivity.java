package com.preeni.traveller_user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.preeni.traveller_user.adapter.PostAdapter;
import com.preeni.traveller_user.adapter.ServiceAdapter;
import com.preeni.traveller_user.model.PostData;
import com.preeni.traveller_user.model.ServiceData;

import java.util.ArrayList;
import java.util.Map;

public class ServiceActivity extends AppCompatActivity {

    private ImageView iv_add;
    private ImageView iv_menu;
    private RecyclerView rv_srv;

    private String user_id = "";
    private String user_name = "";

    private ArrayList<Map<String, String>> serviceMap = new ArrayList<>();
    private ArrayList<ServiceData> serviceData = new ArrayList<>();

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        iv_add = findViewById(R.id.imageView2);
        rv_srv = findViewById(R.id.rv_srv);
        iv_menu = findViewById(R.id.iv_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        user_name = getIntent().getStringExtra("user_name");
        user_id = getIntent().getStringExtra("user_id");
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        DocumentReference documentReferenceImages = firebaseFirestore.collection("servicePosts").document("servicePostsList");
        documentReferenceImages.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                serviceMap = (ArrayList<Map<String, String>>) (documentSnapshot.get("serviceDataArrayList"));
                serviceData.clear();
                if(serviceMap != null) {
                    Log.d("TAG", "onEvent: " + serviceMap);
                    for (int x = 0; x < serviceMap.size(); x++) {
                        String post_text = serviceMap.get(x).get("post_text");
                        String post_url = serviceMap.get(x).get("post_url");
                        String likes = serviceMap.get(x).get("likes");
                        String timeStamp = serviceMap.get(x).get("timeStamp");
                        String user_id = serviceMap.get(x).get("user_id");
                        String user_name = serviceMap.get(x).get("user_name");
                        String service_type = serviceMap.get(x).get("service_type");

                        ServiceData service = new ServiceData(post_text, post_url, likes, new ArrayList<>(), timeStamp, user_id, user_name, service_type);
                        serviceData.add(service);

                        progressDialog.cancel();

                    }
                    //setImages List View
                    rv_srv.setLayoutManager(new LinearLayoutManager(ServiceActivity.this));
                    ServiceAdapter postAdapter = new ServiceAdapter(ServiceActivity.this, serviceData);
                    rv_srv.setAdapter(postAdapter);
                }
                else {
                    progressDialog.cancel();
                }
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ServicePostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", user_id);
                bundle.putString("user_name", user_name);
                bundle.putParcelableArrayList("serviceData", serviceData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
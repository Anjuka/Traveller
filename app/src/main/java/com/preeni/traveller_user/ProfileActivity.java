package com.preeni.traveller_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.preeni.traveller_user.model.User;
import com.preeni.traveller_user.model.UserChatList;
import com.preeni.traveller_user.model.UserFriendList;
import com.preeni.traveller_user.model.UserPostList;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_menu;
    private TextView tv_name;
    private TextView tv_user_type;
    private TextView tv_email;
    private TextView tv_mobile;
    private TextView tv_bio;
    private TextView tv_country;
    private ConstraintLayout cl_update_bio;
    private EditText et_bio;
    private Button btn_update_bio;

    private ProgressDialog progressDialog;


    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String userID = "";

    private ArrayList<User> usersDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(this);

        iv_menu = findViewById(R.id.iv_menu);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_bio = findViewById(R.id.tv_bio);
        tv_country = findViewById(R.id.tv_country);
        tv_user_type = findViewById(R.id.tv_user_type);
        tv_mobile = findViewById(R.id.tv_tp);
        cl_update_bio = findViewById(R.id.cl_update_bio);
        et_bio = findViewById(R.id.et_bio);
        btn_update_bio = findViewById(R.id.btn_update_bio);

        iv_menu.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        showProgressDialog("Loading...");

        firebaseFirestore.collection("users").whereEqualTo("user_id", userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<User> user = queryDocumentSnapshots.toObjects(User.class);
                        usersDetails.clear();
                        usersDetails.addAll(user);

                        tv_name.setText(usersDetails.get(0).getName());
                        tv_email.setText(usersDetails.get(0).getEmail());
                        tv_mobile.setText(usersDetails.get(0).getTp());
                        tv_country.setText(usersDetails.get(0).getCountry());
                        tv_bio.setText(usersDetails.get(0).getBio());
                        tv_user_type.setText("User - " + userID);

                        hideProgressDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                    }
                });

        tv_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_update_bio.setVisibility(View.VISIBLE);
                et_bio.setText("");
            }
        });

        cl_update_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_update_bio.setVisibility(View.GONE);
            }
        });

        btn_update_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProgressDialog("Updating...");

                String bio = et_bio.getText().toString();

                User user = new User(firebaseAuth.getCurrentUser().getUid(), usersDetails.get(0).getEmail(),
                        usersDetails.get(0).getName(), usersDetails.get(0).getTp(), usersDetails.get(0).getCountry(),
                        bio, new ArrayList<UserFriendList>(), new ArrayList<UserPostList>(), new ArrayList<UserChatList>());
                DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        hideProgressDialog();
                        Toast.makeText(ProfileActivity.this, "Bio Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(getIntent());
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Toast.makeText(ProfileActivity.this, "Bio not updated...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.iv_menu:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                finish();
                overridePendingTransition(0, 0);
                break;
        }
    }

    //Progress Bar
    public void showProgressDialog(String message) {

        if (null != progressDialog) {

            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    //Hide Progress Bar
    public void hideProgressDialog() {

        if (null != progressDialog)
            progressDialog.dismiss();
    }
}
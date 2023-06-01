package com.preeni.traveller_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.preeni.traveller_user.model.User;
import com.preeni.traveller_user.model.UserChatList;
import com.preeni.traveller_user.model.UserFriendList;
import com.preeni.traveller_user.model.UserPostList;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    private ConstraintLayout cl_main;
    private TextView tv_login;
    private EditText et_mail;
    private EditText et_password;
    private EditText et_password_re;
    private EditText et_name;
    private EditText et_tel;
    private CountryCodePicker et_country;
    private Button btn_register;

    private String email = "";
    private String pass = "";
    private String pass_re = "";
    private String name = "";
    private String tp = "";
    private String country = "Sri Lanka";

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);

        cl_main = findViewById(R.id.cl_main);
        tv_login = findViewById(R.id.tv_login);
        et_mail = findViewById(R.id.et_mail);
        et_password = findViewById(R.id.et_password);
        et_password_re = findViewById(R.id.et_password_re);
        et_name = findViewById(R.id.et_name);
        et_tel = findViewById(R.id.et_tel);
        et_country = findViewById(R.id.et_country);
        btn_register = findViewById(R.id.btn_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        et_country.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Toast.makeText(RegistrationActivity.this, "Updated " + et_country.getSelectedCountryName(), Toast.LENGTH_SHORT).show();
                country = et_country.getSelectedCountryName();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = et_mail.getText().toString().trim();
                pass = et_password.getText().toString().trim();
                pass_re = et_password_re.getText().toString().trim();
                name = et_name.getText().toString().trim();
                tp = et_tel.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty() || pass_re.isEmpty() || name.isEmpty() || tp.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Fill the information", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pass.equals(pass_re)){
                        //register user
                        registerUser(email, pass, name, tp);
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Password is not matched", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //register user
    private void registerUser(String email, String pass, String name, String tp) {
        showProgressDialog("Registering...");

        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                User user = new User(firebaseAuth.getCurrentUser().getUid(), email, name, tp, country, "", new ArrayList<UserFriendList>(), new ArrayList<UserPostList>(), new ArrayList<UserChatList>());
                DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        hideProgressDialog();
                        showSnackMSG(cl_main, "User Account is created...");

                        Intent intent = new Intent(getApplicationContext(),  MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        hideProgressDialog();
                        showSnackMSG(cl_main, "Something went wrong...");
                        Log.d("TAG", "onFailure: "+e.getMessage());
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    //msg box
    private void showSnackMSG(ConstraintLayout cl_main, String txt) {
        Snackbar snackbar = Snackbar.make(cl_main, txt, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    //hide keyboard
    private void hideKeyboard(ConstraintLayout view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
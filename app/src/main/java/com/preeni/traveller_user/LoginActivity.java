package com.preeni.traveller_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout cl_main;
    private TextView tv_register;
    private Button btn_login;
    private EditText et_mail;
    private EditText et_password;

    private String email = "";
    private String pass = "";

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    progressDialog = new ProgressDialog(this);

    cl_main = findViewById(R.id.cl_main);
    tv_register = findViewById(R.id.tv_register);
    btn_login = findViewById(R.id.btn_login);
    et_mail = findViewById(R.id.et_mail);
    et_password = findViewById(R.id.et_password);

        cl_main.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    firebaseAuth = FirebaseAuth.getInstance();
    firebaseFirestore = FirebaseFirestore.getInstance();
}

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cl_main:
                hideKeyboard(cl_main);
                break;

            case R.id.tv_register:
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
                break;

            case R.id.btn_login:

                email = et_mail.getText().toString().trim();
                pass = et_password.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, "Fill the details to proceed...", Toast.LENGTH_SHORT).show();
                } else {
                    login(email, pass);
                }

                break;
        }
    }

    private void login(String email, String pass) {

        showProgressDialog("Login...");
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                hideProgressDialog();

                showSnackMSG(cl_main,"Login Success...");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                showSnackMSG(cl_main, "Login Failed...");
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
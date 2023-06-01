package com.preeni.traveller_user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.preeni.traveller_user.adapter.GuidelineAdapter;
import com.preeni.traveller_user.model.putPDF;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class GuidelineActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_menu;
    private GridView gv_guideline;
    PDFView pdfView;
    ConstraintLayout cl_pdf_view;
    ImageView close_pdf;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String displayName = "";
    String userID = "";

    ProgressDialog progressDialog;

    private GuidelineAdapter guidelineAdapter;

    private ArrayList<putPDF> guidelineDataList = new ArrayList<>();
    private ArrayList<Map<String, String>> guidelineDataMapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline);

        progressDialog = new ProgressDialog(this);

        iv_menu = findViewById(R.id.iv_menu);
        gv_guideline = findViewById(R.id.gv_guideline);
        pdfView = findViewById(R.id.pdfView);
        cl_pdf_view = findViewById(R.id.cl_pdf_view);
        close_pdf = findViewById(R.id.close_pdf);

        iv_menu.setOnClickListener(this);
        close_pdf.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadGuideline");
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        DocumentReference documentReferenceGuide = firebaseFirestore.collection("guidelines").document("admin_guidelines");
        documentReferenceGuide.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                guidelineDataMapList = (ArrayList<Map<String, String>>) (documentSnapshot.get("guidelineDataList"));
                Log.d("TAG", "onEvent: ");
                if(guidelineDataMapList != null) {
                    for (int x = 0; x < guidelineDataMapList.size(); x++) {
                        String name = guidelineDataMapList.get(x).get("name");
                        String url = guidelineDataMapList.get(x).get("url");

                        putPDF putPDF = new putPDF(name, url);
                        guidelineDataList.add(putPDF);
                    }
                }
                Log.d("TAG", "onCreate: guidelineDataList " + guidelineDataList);
                guidelineAdapter = new GuidelineAdapter(GuidelineActivity.this, guidelineDataList);
                gv_guideline.setAdapter(guidelineAdapter);
            }
        });

        gv_guideline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //show pdf
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                cl_pdf_view.setVisibility(View.VISIBLE);
                pdfView.fromUri(Uri.parse(guidelineDataList.get(i).getUrl())).load();

                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {

                        try {
                            InputStream input = new URL(guidelineDataList.get(i).getUrl()).openStream();
                            pdfView.fromStream(input).load();
                            progressDialog.cancel();
                        } catch (IOException e) {
                            e.printStackTrace();
                            progressDialog.cancel();
                        }

                        return null;
                    }
                }.execute();
            }
        });

       /* gv_guideline.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GuidelineActivity.this);
                builder.setMessage("Are you sure to delete this guideline?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.iv_menu:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                finish();
                break;

            case R.id.close_pdf:
                cl_pdf_view.setVisibility(View.GONE);
                break;
        }
    }
}
package com.preeni.traveller_user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.preeni.traveller_user.adapter.ImagesAdapter;
import com.preeni.traveller_user.model.ImagesDataList;

import java.util.ArrayList;
import java.util.Map;

public class ImagesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add_img;
    public Uri url;
    private ConstraintLayout ll_img_select;
    private ImageView iv_close;
    private ImageView iv_menu;
    private ImageView btn_cam;
    private ImageView btn_gall;
    private EditText et_file_name;
    private GridView gv_images;

    private String img_url = "";
    private String name = "";

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private ProgressDialog progressDialog;

    private ArrayList<ImagesDataList> imagesDataLists = new ArrayList<>();
    private ArrayList<Map<String, String>> imageMap = new ArrayList<>();

    private ImagesAdapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        gv_images = findViewById(R.id.gv_img);
        iv_menu = findViewById(R.id.iv_menu);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        DocumentReference documentReferenceImages = firebaseFirestore.collection("images").document("admin_images");
        documentReferenceImages.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                imageMap = (ArrayList<Map<String, String>>) (documentSnapshot.get("imagesDataLists"));
                if(imageMap != null) {
                    Log.d("TAG", "onEvent: " + imageMap);
                    for (int x = 0; x < imageMap.size(); x++) {
                        String image_name = imageMap.get(x).get("img_name");
                        String image_url = imageMap.get(x).get("img_url");

                        ImagesDataList imagesDataList = new ImagesDataList(image_name, image_url);
                        imagesDataLists.add(imagesDataList);

                    }
                    //setImages List View
                    imagesAdapter = new ImagesAdapter(ImagesActivity.this, imagesDataLists);
                    gv_images.setAdapter(imagesAdapter);
                }
            }
        });

        iv_menu.setOnClickListener(this);
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
        }
    }
}
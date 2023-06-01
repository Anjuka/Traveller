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
import android.widget.MediaController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.preeni.traveller_user.adapter.VideosAdapter;
import com.preeni.traveller_user.model.VideoDataList;

import java.util.ArrayList;
import java.util.Map;

public class VideoActivity extends AppCompatActivity {

    private Button btn_add_video;
    private ConstraintLayout ll_img_select;
    private ImageView iv_close;
    private ImageView iv_menu;
    private ImageView btn_cam;
    private ImageView btn_gall;
    private EditText et_file_name;
    private GridView gv_video;

    private MediaController mediaController;
    Uri selected_uri;
    public Uri url;
    private String name ="";
    private String vd_url = "";

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private ProgressDialog progressDialog;

    private ArrayList<VideoDataList> videoDataLists = new ArrayList<>();
    private ArrayList<Map<String, String>> videoMap = new ArrayList<>();

    private VideosAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        gv_video = findViewById(R.id.gv_video);
        iv_menu = findViewById(R.id.iv_menu);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        mediaController = new MediaController(this);

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                finish();
            }
        });

        DocumentReference documentReferencePlans = firebaseFirestore.collection("videos").document("admin_videos");
        documentReferencePlans.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                videoMap = (ArrayList<Map<String, String>>) (documentSnapshot.get("videoDataLists"));
                if(videoMap != null) {
                    Log.d("TAG", "onEvent: " + videoMap);
                    for (int x = 0; x < videoMap.size(); x++) {
                        String vd_name = videoMap.get(x).get("vd_name");
                        String vd_url = videoMap.get(x).get("vd_url");

                        VideoDataList videoDataList = new VideoDataList(vd_name, vd_url);
                        videoDataLists.add(videoDataList);

                    }
                    //setImages List View
                    videosAdapter = new VideosAdapter(VideoActivity.this, videoDataLists);
                    gv_video.setAdapter(videosAdapter);
                }
            }
        });
    }
}
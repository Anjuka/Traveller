package com.preeni.traveller_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.preeni.traveller_user.adapter.PostAdapter;
import com.preeni.traveller_user.helper.RequestUserPermission;
import com.preeni.traveller_user.model.CommentContent;
import com.preeni.traveller_user.model.CommentData;
import com.preeni.traveller_user.model.LikeData;
import com.preeni.traveller_user.model.Post;
import com.preeni.traveller_user.model.PostData;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PostCreateActivity extends AppCompatActivity {

    private Button btn_post_create;
    private LinearLayout ll_select_img;
    private ImageView iv_post_pic;
    private EditText et_post_txt;

    private String img_url = "";
    private String post_txt = "";
    public Uri url;

    private String user_name ="";
    private String user_id ="";

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private ProgressDialog progressDialog;

    private ArrayList<PostData> postDataList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        progressDialog = new ProgressDialog(this);

        ll_select_img = findViewById(R.id.ll_select_img);
        iv_post_pic = findViewById(R.id.iv_post_pic);
        et_post_txt = findViewById(R.id.et_post_txt);
        btn_post_create = findViewById(R.id.btn_post_create);

        iv_post_pic.setEnabled(false);
        btn_post_create.setEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_name =getIntent().getExtras().getString("user_name");
        user_id = getIntent().getExtras().getString("user_id");
       // postDataList = getIntent().getExtras().getParcelableArrayList("postDataList");

        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseFirestore.collection("posts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<Post> posts = querySnapshot.toObjects(Post.class);
                        postDataList.clear();
                        if (posts.get(0).getPostData() != null) {
                            postDataList.addAll(posts.get(0).getPostData());
                            Log.d("TAG", "onSuccess: ");
                        }

                        iv_post_pic.setEnabled(true);
                        btn_post_create.setEnabled(true);
                        progressDialog.cancel();
                    }
                });

        RequestUserPermission requestUserPermission = new RequestUserPermission(this);
        requestUserPermission.verifyStoragePermissions();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.
                PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        // do we have a camera?
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        }

        if (Environment.isExternalStorageManager()) {
            //todo when permission is granted
        } else {
            //request for the permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }

        iv_post_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PostCreateActivity.this);
                builder.setTitle("Post Create");
                builder.setMessage("Select Image From,")
                        .setCancelable(true)
                        .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                selectImages();
                                dialog.cancel();
                            }
                        })
                        .setNeutralButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                captureImages();
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
            }
        });

        btn_post_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Uploading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                post_txt = et_post_txt.getText().toString();

                String timestamp = String.valueOf(System.currentTimeMillis());

                StorageReference post = storageReference.child("images/" + timestamp);
                post.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        post.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                img_url = uri.toString();

                                ArrayList<LikeData> likes = new ArrayList<LikeData>();
                                ArrayList<String> user_likes = new ArrayList<>();
                                likes.add(new LikeData(user_likes, "0"));


                                ArrayList<CommentData> comments = new ArrayList<CommentData>();
                                ArrayList<CommentContent> user_comments = new ArrayList<>();
                                comments.add(new CommentData(user_comments, "0"));

                                PostData postData = new PostData(post_txt, img_url, likes, comments, timestamp, user_id, user_name);
                                postDataList.add(postData);
                                Post postSave = new Post(postDataList);

                                DocumentReference documentReference = firebaseFirestore.collection("posts").document("postsList");
                                documentReference.set(postSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.cancel();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.cancel();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                    }
                });
            }
        });
    }

    private void captureImages() {
        Intent takePicture = new Intent("android.media.action.IMAGE_CAPTURE");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(storageDir + File.separator + "CAPTURE.jpg");
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(),
                "com.preeni.traveller_user.fileprovider", // Over here
                file));
        startActivityForResult(takePicture, 2);
    }

    private void selectImages() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String decodedImgString;

      //  progressDialog.setMessage("Uploading...");
       // progressDialog.show();

        switch(requestCode) {
            case 1:

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                decodedImgString = cursor.getString(columnIndex);
                cursor.close();

                url = Uri.parse("file://" + decodedImgString);
                Log.d("TAG", "onActivityResult: ");

                Glide.with(PostCreateActivity.this)
                        .load(url)
                        .into(iv_post_pic);

                ll_select_img.setVisibility(View.INVISIBLE);

                break;

            case 2:

                if (resultCode == RESULT_OK) {

                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File file = new File(storageDir + File.separator + "CAPTURE.jpg");

                    String fileName;

                    File dir = new File(storageDir + File.separator + "ZharkPictures");

                    try {
                        if (dir.exists()) {
                            System.out.println("Directory Exists");

                        } else {
                            dir.mkdir();
                            System.out.println("Directory Created");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    File newExFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String exFileName = renameFile();
                    String destinationPath = newExFile.getAbsolutePath() + File.separator + exFileName + ".jpg";

                    Log.i("TAG", String.valueOf(destinationPath));

                    File destination = new File(destinationPath);

                    try {
                        FileUtils.copyFile(file, destination);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    url = Uri.fromFile(destination);

                    Glide.with(PostCreateActivity.this)
                            .load(url)
                            .into(iv_post_pic);

                    ll_select_img.setVisibility(View.INVISIBLE);
                }

                break;
        }
    }

    public static String renameFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "JPEG_" + timeStamp + "_";
    }
}
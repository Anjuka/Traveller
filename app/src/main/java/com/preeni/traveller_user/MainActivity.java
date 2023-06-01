package com.preeni.traveller_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.preeni.traveller_user.adapter.CommentsAdapter;
import com.preeni.traveller_user.adapter.PostAdapter;
import com.preeni.traveller_user.constant.Constants;
import com.preeni.traveller_user.model.CommentContent;
import com.preeni.traveller_user.model.CommentData;
import com.preeni.traveller_user.model.LikeData;
import com.preeni.traveller_user.model.Post;
import com.preeni.traveller_user.model.PostData;
import com.preeni.traveller_user.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, PostAdapter.OnItemClickListener, PostAdapter.OnItemClickListenerComment, CommentsAdapter.itemCommentClickListner {

    private static String TAG = "MainActivity";

    private DrawerLayout drawer;
    private ImageView iv_menu;
    private ImageView iv_add_post;
    private NavigationView nv_view;
    private View headerView;
    private TextView tv_user_name;
    private RecyclerView rv_posts;
    private ConstraintLayout cl_comments;
    private ImageView iv_cmt_close;
    private ImageView iv_send;
    private EditText et_chat_text;
    private RecyclerView rv_comments;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    private String userID = "";

    private ArrayList<Map<String, String>> postMap = new ArrayList<>();
    private ArrayList<PostData> postDataList = new ArrayList<>();

    String user_id = "";
    String email = "";
    String name = "";
    String tp = "";
    String country = "";
    String bio = "";

    int post_selected_position =0;

    PostAdapter postAdapter;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);
        iv_menu = findViewById(R.id.iv_menu);
        nv_view = findViewById(R.id.nav_view);
        rv_posts = findViewById(R.id.rv_posts);
        iv_add_post = findViewById(R.id.iv_add_post);
        cl_comments = findViewById(R.id.cl_comments);
        iv_cmt_close = findViewById(R.id.iv_cmt_close);
        et_chat_text = findViewById(R.id.et_chat_text);
        iv_send = findViewById(R.id.iv_send);
        rv_comments = findViewById(R.id.rv_comments);

        headerView = nv_view.getHeaderView(0);
        tv_user_name = headerView.findViewById(R.id.tv_user_name);

        iv_menu.setOnClickListener(this);
        iv_add_post.setOnClickListener(this);
        iv_cmt_close.setOnClickListener(this);
        et_chat_text.setOnClickListener(this);
        iv_send.setOnClickListener(this);
        nv_view.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        sharedpreferences = getSharedPreferences(Constants.SHARE_PREFERENCE_TAG, Context.MODE_PRIVATE);

        DocumentReference documentUserDetails = firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        documentUserDetails.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                 user_id = documentSnapshot.getString("user_id");
                 email = documentSnapshot.getString("email");
                 name = documentSnapshot.getString("name");
                 tp = documentSnapshot.getString("tp");
                 country = documentSnapshot.getString("country");
                 bio = documentSnapshot.getString("bio");

                Log.d("TAG", "onEvent: ");

            }
        });

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

                            //setImages List View
                            rv_posts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            postAdapter = new PostAdapter(MainActivity.this, postDataList, MainActivity.this::onItemClick, userID, MainActivity.this::onItemClickComment);
                            rv_posts.setAdapter(postAdapter);
                        }
                    }
                });

        getToken();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                break;

            case R.id.iv_add_post:

                Intent intent = new Intent(getApplicationContext(), PostCreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", user_id);
                bundle.putString("user_name", name);
                //bundle.putParcelableArrayList("postDataList", postDataList);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.iv_cmt_close:
                cl_comments.setVisibility(View.GONE);
                break;

            case R.id.iv_send:
                Log.d(TAG, "onClick: post_selected_position " + post_selected_position);

                String comment = et_chat_text.getText().toString().trim();
                String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                CommentContent commentContent = new CommentContent(userID, name, comment, time, date);
                ArrayList<CommentData> commentData = postDataList.get(post_selected_position).getComments();
                commentData.get(0).getCommented_user_id().add(commentContent);
                commentData.get(0).setComment_count(String.valueOf(commentData.get(0).getCommented_user_id().size()));

                postDataList.get(post_selected_position).setComments(commentData);
                updateComments();
                break;
        }
    }

    private void updateComments() {
        Post postSave = new Post(postDataList);
        DocumentReference documentReference = firebaseFirestore.collection("posts").document("postsList");
        documentReference.set(postSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                postAdapter.notifyDataSetChanged();
                et_chat_text.setText("");
               // cl_comments.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_profile:
                Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profile);
                finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_video:
                Intent vd = new Intent(getApplicationContext(), VideoActivity.class);
                startActivity(vd);
              //  finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_images:
                Intent img = new Intent(getApplicationContext(), ImagesActivity.class);
                startActivity(img);
              //  finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_travel_plans:
                Intent plan = new Intent(getApplicationContext(), PlanQuestionActivity.class);
                startActivity(plan);
              //  finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_map:
                Intent map = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(map);
               // finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_guid:
                Intent guid = new Intent(getApplicationContext(), GuidelineActivity.class);
                startActivity(guid);
               // finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_services:
                Intent service = new Intent(getApplicationContext(), ServiceActivity.class);
                service.putExtra("user_id", user_id);
                service.putExtra("user_name", name);
                startActivity(service);
                // finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_chat:
                Intent chat = new Intent(getApplicationContext(), ChatActivity.class);
                chat.putExtra("user_id", user_id);
                chat.putExtra("user_name", name);
                startActivity(chat);
                // finish();
                overridePendingTransition(0, 0);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_logout:
                signOut();
                break;
        }

        return true;
    }

    private void showToast(String massage){
        Toast.makeText(getApplicationContext(), massage, Toast.LENGTH_SHORT).show();
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection("users").document(userID);
        documentReference.update(Constants.FCM_TOKEN, token)
                .addOnSuccessListener(unused -> showToast("Token updated successfully"))
                .addOnFailureListener(e -> showToast("Unable to update toke"));

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Constants.FCM_TOKEN, token);
        editor.commit();
        editor.apply();

    }

    private void signOut() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection("users").document(userID);
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    firebaseAuth.signOut();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Constants.FCM_TOKEN, "");
                    editor.commit();
                    editor.apply();
                    Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logout);
                    finish();
                    overridePendingTransition(0, 0);
                    drawer.closeDrawer(GravityCompat.START);
                })
                .addOnFailureListener(e -> showToast("Unable to sign out"));
    }

    @Override
    public void onItemClick(int position) {
        Log.d("TAG", "onItemClick: position " + position);

        boolean isLiked = isLikedCurrentUser(position);

        if (isLiked == true){
            //already liked -> remove like
            Log.d("TAG", "onItemClick: " + postDataList.get(position).getLikes().get(0).getLike_count());
        }
        else {
            //not liked -> add new like
            Log.d("TAG", "onItemClick: " + postDataList.get(position).getLikes().get(0).getLike_count());
        }

        updateLikes(position);
    }

    private void updateLikes(int position) {
        Post postSave = new Post(postDataList);
        DocumentReference documentReference = firebaseFirestore.collection("posts").document("postsList");
        documentReference.set(postSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                postAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private boolean isLikedCurrentUser(int position) {
        for (int x =0; x < postDataList.get(position).getLikes().get(0).getLiked_user_id().size(); x++){
                if (postDataList.get(position).getLikes().get(0).getLiked_user_id().get(x).equals(userID)){
                    postDataList.get(position).getLikes().get(0).getLiked_user_id().remove(x);
                    String new_like_count = String.valueOf(Integer.parseInt(postDataList.get(position).getLikes().get(0).getLike_count()) - 1);
                    postDataList.get(position).getLikes().get(0).setLike_count(new_like_count);
                    return true;
            }
        }
        postDataList.get(position).getLikes().get(0).getLiked_user_id().add(userID);
        String new_like_count = String.valueOf(Integer.parseInt(postDataList.get(position).getLikes().get(0).getLike_count()) + 1);
        postDataList.get(position).getLikes().get(0).setLike_count(new_like_count);
        return false;
    }

    @Override
    public void onItemClickComment(int position) {
        Log.d("TAG", "onItemClickComment: position " + position);
        cl_comments.setVisibility(View.VISIBLE);
        this.post_selected_position = position;

        rv_comments.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        CommentsAdapter commentsAdapter = new CommentsAdapter(MainActivity.this, postDataList.get(position).getComments().get(0).getCommented_user_id(), MainActivity.this::onCommentItemClick);
        rv_comments.setAdapter(commentsAdapter);


    }

    @Override
    public void onCommentItemClick(int postion) {

    }
}
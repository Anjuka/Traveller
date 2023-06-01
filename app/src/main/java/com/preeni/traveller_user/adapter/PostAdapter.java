package com.preeni.traveller_user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.media.Image;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.preeni.traveller_user.PostCreateActivity;
import com.preeni.traveller_user.R;
import com.preeni.traveller_user.model.PostData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context context;
    ArrayList<PostData> post_list;
    LayoutInflater inflater;
    private OnItemClickListener listener;
    private OnItemClickListenerComment listenerComment;
    String user_id;

    public PostAdapter(Context context, ArrayList<PostData> post_list, OnItemClickListener listener, String user_id, OnItemClickListenerComment listenerComment) {
        this.context = context;
        this.post_list = post_list;
        this.listener = listener;
        this.user_id = user_id;
        this.listenerComment = listenerComment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_posts, parent, false);
        return new MyViewHolder(view, listener, listenerComment);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_user_name.setText(post_list.get(position).getUser_name());
        holder.tv_post_text.setText(post_list.get(position).getPost_text());
        holder.tv_likes.setText(post_list.get(position).getLikes().get(0).getLike_count());
        holder.tv_time.setText(getDate(Long.parseLong(post_list.get(position).getTimeStamp())));

        Glide.with(context)
                .load(post_list.get(position).getPost_url())
                .into(holder.iv_post);

        for (int x =0; x < post_list.get(position).getLikes().get(0).getLiked_user_id().size(); x++){
            if (post_list.get(position).getLikes().get(0).getLiked_user_id().get(x).equals(user_id)){
                holder.iv_like.setBackgroundResource(R.drawable.heart_fill);
               // holder.iv_like.setBackgroundTintList(ColorStateList.valueOf(R.color.transparent));
            }
            else {
                holder.iv_like.setBackgroundResource(R.drawable.heart_no_fill);
               // holder.iv_like.setBackgroundTintList(ColorStateList.valueOf(R.color.white));
            }
        }

        if (post_list.get(position).getLikes().get(0).getLike_count().equals("0")){
            holder.iv_like.setBackgroundResource(R.drawable.heart_no_fill);
        }
    }

    @Override
    public int getItemCount() {
        return post_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_user_name = itemView.findViewById(R.id.tv_user_name);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_likes = itemView.findViewById(R.id.tv_likes);
        TextView tv_post_text = itemView.findViewById(R.id.tv_post_text);
        ImageView iv_post = itemView.findViewById(R.id.iv_post);
        ImageView iv_like = itemView.findViewById(R.id.iv_like);
        LinearLayout ll_like = itemView.findViewById(R.id.ll_like);
        LinearLayout ll_comment = itemView.findViewById(R.id.ll_comment);

        private OnItemClickListener listener;
        private OnItemClickListenerComment listenerComment;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemClickListenerComment listenerComment) {
            super(itemView);
            this.listener = listener;
            this.listenerComment = listenerComment;

            ll_like.setOnClickListener(this);
            ll_comment.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.ll_like:
                    listener.onItemClick(getAdapterPosition());
                    break;
                case R.id.ll_comment:
                    listenerComment.onItemClickComment(getAdapterPosition());
                    break;
            }
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        //cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemClickListenerComment {
        void onItemClickComment(int position);
    }
}


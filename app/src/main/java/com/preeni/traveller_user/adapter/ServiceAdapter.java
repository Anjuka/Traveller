package com.preeni.traveller_user.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.preeni.traveller_user.R;
import com.preeni.traveller_user.model.PostData;
import com.preeni.traveller_user.model.ServiceData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    Context context;
    ArrayList<ServiceData> post_list;
    LayoutInflater inflater;

    public ServiceAdapter(Context context, ArrayList<ServiceData> post_list) {
        this.context = context;
        this.post_list = post_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_posts, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_user_name.setText(post_list.get(position).getUser_name());
        holder.tv_post_text.setText(post_list.get(position).getPost_text());
        holder.tv_likes.setText(post_list.get(position).getLikes());
        holder.tv_time.setText(getDate(Long.parseLong(post_list.get(position).getTimeStamp())));

        Glide.with(context)
                .load(post_list.get(position).getPost_url())
                .into(holder.iv_post);
    }

    @Override
    public int getItemCount() {
        return post_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user_name = itemView.findViewById(R.id.tv_user_name);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_likes = itemView.findViewById(R.id.tv_likes);
        TextView tv_post_text = itemView.findViewById(R.id.tv_post_text);
        ImageView iv_post = itemView.findViewById(R.id.iv_post);

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        //cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}


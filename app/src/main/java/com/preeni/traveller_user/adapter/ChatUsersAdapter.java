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
import com.preeni.traveller_user.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> users_list;
    LayoutInflater inflater;
    itemClickListner itemClickListner;

    public ChatUsersAdapter(Context context, ArrayList<User> users_list, itemClickListner itemClickListner) {
        this.context = context;
        this.users_list = users_list;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_chat_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(users_list.get(position).getName());
        holder.tv_last_txt.setText(getDate(System.currentTimeMillis()/1000));

        holder.itemView.setOnClickListener(view -> { itemClickListner.onItemClick(position); });
    }

    @Override
    public int getItemCount() {
        return users_list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name = itemView.findViewById(R.id.tv_name);
        TextView tv_last_txt = itemView.findViewById(R.id.tv_last_txt);

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("HH:mm", cal).toString();
        return date;
    }

    public interface itemClickListner{
        void onItemClick(int postion); }
}


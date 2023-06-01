package com.preeni.traveller_user.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.preeni.traveller_user.R;
import com.preeni.traveller_user.model.CommentContent;
import com.preeni.traveller_user.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    Context context;
    ArrayList<CommentContent> users_list;
    LayoutInflater inflater;
    itemCommentClickListner itemClickListner;

    public CommentsAdapter(Context context, ArrayList<CommentContent> users_list, itemCommentClickListner itemClickListner) {
        this.context = context;
        this.users_list = users_list;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_comm_user.setText(users_list.get(position).getCommented_user_name());
        holder.tv_comment.setText(users_list.get(position).getComment());
        holder.tv_date.setText(users_list.get(position).getDate() + "  " + users_list.get(position).getTime());

        holder.itemView.setOnClickListener(view -> { itemClickListner.onCommentItemClick(position); });
    }

    @Override
    public int getItemCount() {
        return users_list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_comm_user = itemView.findViewById(R.id.tv_comm_user);
        TextView tv_comment = itemView.findViewById(R.id.tv_comment);
        TextView tv_date = itemView.findViewById(R.id.tv_date);

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface itemCommentClickListner{
        void onCommentItemClick(int postion); }
}


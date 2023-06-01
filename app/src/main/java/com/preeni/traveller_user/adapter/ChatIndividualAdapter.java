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
import com.preeni.traveller_user.model.Massage;
import com.preeni.traveller_user.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChatIndividualAdapter extends RecyclerView.Adapter<ChatIndividualAdapter.MyViewHolder> {

    Context context;
    ArrayList<Massage> massageList;
    LayoutInflater inflater;
    String sender_id;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ChatIndividualAdapter(Context context, ArrayList<Massage> massageList, String sender_id) {
        this.context = context;
        this.massageList = massageList;
        this.sender_id =  sender_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_SENT) {
            View view = inflater.inflate(R.layout.item_send_msg, parent, false);
            return new MyViewHolder(view);
        }
        else {
            View view = inflater.inflate(R.layout.item_received_msg, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT){
            holder.tv_massage.setText(massageList.get(position).getMassage());
        }
        else {
            holder.tv_massage.setText(massageList.get(position).getMassage());
        }
    }

    @Override
    public int getItemCount() {
        return massageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (massageList.get(position).getSenderId().equals(sender_id)){
            return VIEW_TYPE_SENT;
        }
        else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_massage = itemView.findViewById(R.id.tv_massage);

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class SentMassageViewHolder extends RecyclerView.ViewHolder {

        TextView tv_massage = itemView.findViewById(R.id.tv_massage);

        public SentMassageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class ReceivedMassageViewHolder extends RecyclerView.ViewHolder {

        TextView tv_massage = itemView.findViewById(R.id.tv_massage);

        public ReceivedMassageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("HH:mm", cal).toString();
        return date;
    }
}


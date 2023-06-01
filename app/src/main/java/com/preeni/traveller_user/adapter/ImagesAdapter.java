package com.preeni.traveller_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.preeni.traveller_user.R;
import com.preeni.traveller_user.model.ImagesDataList;

import java.util.ArrayList;

public class ImagesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ImagesDataList> guidelineList;

    public ImagesAdapter(Context context, ArrayList<ImagesDataList> guidelineList) {
        this.context = context;
        this.guidelineList = guidelineList;
    }

    @Override
    public int getCount() {
        return guidelineList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_image, null);
        }

        ConstraintLayout parentItemView = convertView.findViewById(R.id.parentItemView);
        ImageView iv_image = convertView.findViewById(R.id.iv_image);
        TextView tv_image_name = convertView.findViewById(R.id.tv_image_name);

        tv_image_name.setText(guidelineList.get(position).getImg_name());

        Glide.with(context)
                .load(guidelineList.get(position).getImg_url())
                .into(iv_image);

        return convertView;
    }
}

package com.preeni.traveller_user.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.preeni.traveller_user.R;
import com.preeni.traveller_user.model.VideoDataList;

import java.util.ArrayList;

public class VideosAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<VideoDataList> guidelineList;

    public VideosAdapter(Context context, ArrayList<VideoDataList> guidelineList) {
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
            convertView = inflater.inflate(R.layout.item_vd, null);
        }

        ConstraintLayout parentItemView = convertView.findViewById(R.id.parentItemView);
        VideoView iv_image = convertView.findViewById(R.id.iv_image);
        TextView tv_image_name = convertView.findViewById(R.id.tv_image_name);

        tv_image_name.setText(guidelineList.get(position).getVd_name());

        MediaController mediaController= new MediaController(context);
        mediaController.setAnchorView(iv_image);


        //Setting MediaController and URI, then starting the videoView
        iv_image.setMediaController(mediaController);
        iv_image.setVideoURI(Uri.parse(guidelineList.get(position).getVd_url()));
        iv_image.requestFocus();
        iv_image.start();

        return convertView;
    }
}

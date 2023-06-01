package com.preeni.traveller_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.preeni.traveller_user.R;
import com.preeni.traveller_user.model.putPDF;

import java.util.ArrayList;

public class GuidelineAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<putPDF> guidelineList;

    public GuidelineAdapter(Context context, ArrayList<putPDF> guidelineList) {
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
            convertView = inflater.inflate(R.layout.item_guideline, null);
        }

        ConstraintLayout parentItemView = convertView.findViewById(R.id.parentItemView);
        TextView tv_guideline_name = convertView.findViewById(R.id.tv_guideline_name);
        TextView tv_guideline_date = convertView.findViewById(R.id.tv_guideline_date);

        tv_guideline_name.setText(guidelineList.get(position).getName());
        tv_guideline_date.setText("Guideline " + String.valueOf(position + 1));

        return convertView;
    }
}

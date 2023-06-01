package com.preeni.traveller_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.preeni.traveller_user.R;
import com.preeni.traveller_user.model.PlanDataList;

import java.util.ArrayList;

public class PlansAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<PlanDataList> guidelineList;

    public PlansAdapter(Context context, ArrayList<PlanDataList> guidelineList) {
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
            convertView = inflater.inflate(R.layout.item_plan, null);
        }

        ConstraintLayout parentItemView = convertView.findViewById(R.id.parentItemView);
        TextView tv_guideline_name = convertView.findViewById(R.id.tv_guideline_name);
        TextView tv_guideline_date = convertView.findViewById(R.id.tv_guideline_date);
        TextView tv_guideline_description = convertView.findViewById(R.id.tv_guideline_description);
        TextView tv_plan_code = convertView.findViewById(R.id.tv_plan_code);

        tv_plan_code.setText(guidelineList.get(position).getPlane_code());
        tv_guideline_name.setText(guidelineList.get(position).getRouting());
        tv_guideline_date.setText(guidelineList.get(position).getDuration() + " Days");
        tv_guideline_description.setText(guidelineList.get(position).getDescription());
        return convertView;
    }
}

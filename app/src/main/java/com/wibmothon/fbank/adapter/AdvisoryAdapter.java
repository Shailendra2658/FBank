package com.wibmothon.fbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.model.AdvisoryModel;
import com.wibmothon.fbank.model.InProgressGoalsModel;

import java.util.List;

public class AdvisoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<AdvisoryModel> advisoryModels;
    Context context;

    public AdvisoryAdapter(List<AdvisoryModel> advisoryModels, Context context) {
        this.advisoryModels = advisoryModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advisory_recyclerview_item, parent, false);
                return new DiscoverType1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AdvisoryModel advisoryModel = advisoryModels.get(position);
        if (advisoryModel != null) {

            if (position == 0) {
                ((DiscoverType1ViewHolder) holder).goalYrTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                ((DiscoverType1ViewHolder) holder).goalYrTv.setTextColor(context.getResources().getColor(R.color.red_color));
            }

            ((DiscoverType1ViewHolder) holder).goalYrTv.setText(advisoryModel.getAdvisoryHeader());
            ((DiscoverType1ViewHolder) holder).goalYrTv2.setText(advisoryModel.getGoalYrTv2());
            ((DiscoverType1ViewHolder) holder).txt_title.setText(advisoryModel.getAdvisoryTitle());
            ((DiscoverType1ViewHolder) holder).txt_subtitle.setText(advisoryModel.getAdvisorySubTitle());
            ((DiscoverType1ViewHolder) holder).item_image.setImageResource(advisoryModel.getAdvisoryDrawable());
        }
    }

    @Override
    public int getItemCount() {
        return advisoryModels.size();
    }

    private class DiscoverType1ViewHolder extends RecyclerView.ViewHolder {

        TextView goalYrTv;
        TextView goalYrTv2;
        TextView txt_title;
        ImageView item_image;
        TextView txt_subtitle;

        public DiscoverType1ViewHolder(@NonNull View itemView) {
            super(itemView);
            goalYrTv = itemView.findViewById(R.id.goalYrTv);
            goalYrTv2 = itemView.findViewById(R.id.goalYrTv2);
            txt_title = itemView.findViewById(R.id.txt_title);
            item_image = itemView.findViewById(R.id.item_image);
            txt_subtitle = itemView.findViewById(R.id.txt_subtitle);
        }
    }
}

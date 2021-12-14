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
import com.wibmothon.fbank.model.InProgressGoalsModel;

import java.util.List;

public class InProgressGoalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<InProgressGoalsModel> progressGoalsModels;
    Context context;

    public InProgressGoalAdapter(List<InProgressGoalsModel> progressGoalsModels, Context context) {
        this.progressGoalsModels = progressGoalsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inprogress_goals_recyclerview_item, parent, false);
                return new DiscoverType1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InProgressGoalsModel discoverServices = progressGoalsModels.get(position);
        if (discoverServices != null) {
            ((DiscoverType1ViewHolder) holder).goalYrTv.setText(discoverServices.getGoalsYear());
            ((DiscoverType1ViewHolder) holder).txt_title.setText(discoverServices.getGoalsTitle());
            ((DiscoverType1ViewHolder) holder).txt_subtitle.setText(discoverServices.getGoalsSubTitle());
            ((DiscoverType1ViewHolder) holder).achievedPercentageTv.setText(discoverServices.getAchievedPercentage());
            ((DiscoverType1ViewHolder) holder).pendingPercentageTv.setText(discoverServices.getPendingPercentage());
        }
    }

    @Override
    public int getItemCount() {
        return progressGoalsModels.size();
    }

    private class DiscoverType1ViewHolder extends RecyclerView.ViewHolder {

        TextView goalYrTv;
        TextView txt_title;
        ImageView item_image;
        TextView txt_subtitle;
        TextView achievedPercentageTv;
        TextView pendingPercentageTv;

        public DiscoverType1ViewHolder(@NonNull View itemView) {
            super(itemView);
            goalYrTv = itemView.findViewById(R.id.goalYrTv);
            txt_title = itemView.findViewById(R.id.txt_title);
            item_image = itemView.findViewById(R.id.item_image);
            txt_subtitle = itemView.findViewById(R.id.txt_subtitle);
            achievedPercentageTv = itemView.findViewById(R.id.achievedPercentageTv);
            pendingPercentageTv = itemView.findViewById(R.id.pendingPercentageTv);
        }
    }
}

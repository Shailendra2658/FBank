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
import com.wibmothon.fbank.model.LCashModel;
import com.wibmothon.fbank.model.LiabilityModel;

import java.util.List;

public class LiabilityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<LiabilityModel> liabilityModels;
    Context context;

    public LiabilityAdapter(List<LiabilityModel> liabilityModels, Context context) {
        this.liabilityModels = liabilityModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.libality_recyclerview_item, parent, false);
                return new DiscoverType1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LiabilityModel lCashModel = liabilityModels.get(position);
        if (lCashModel != null) {
            ((DiscoverType1ViewHolder) holder).liabilityTitleTv.setText(lCashModel.getLiabilityTitle());
            ((DiscoverType1ViewHolder) holder).liabilitySubTitle.setText(lCashModel.getLiabilitySubTitle());
            ((DiscoverType1ViewHolder) holder).liabilitySubTitle2.setText(lCashModel.getLiabilitySubTitle2());
            ((DiscoverType1ViewHolder) holder).achievedTv.setText(lCashModel.getAchievedTitle());
            ((DiscoverType1ViewHolder) holder).achievedPercentageTv.setText(lCashModel.getAchievedPercentageTv());
            ((DiscoverType1ViewHolder) holder).pendingTv.setText(lCashModel.getPendingTv());
            ((DiscoverType1ViewHolder) holder).pendingPercentageTv.setText(lCashModel.getPendingPercentageTv());
            ((DiscoverType1ViewHolder) holder).imageSrc.setImageResource(lCashModel.getImageSrc());

            if (position == 0 || position == 1) {
                ((DiscoverType1ViewHolder) holder).achievedTv.setTextColor(context.getResources().getColor(R.color.achieved_green));
                ((DiscoverType1ViewHolder) holder).achievedPercentageTv.setVisibility(View.VISIBLE);
                ((DiscoverType1ViewHolder) holder).pendingPercentageTv.setVisibility(View.VISIBLE);
                ((DiscoverType1ViewHolder) holder).pendingTv.setTextColor(context.getResources().getColor(R.color.pending_green));
            } else {
                ((DiscoverType1ViewHolder) holder).achievedTv.setTextColor(context.getResources().getColor(R.color.black));
                ((DiscoverType1ViewHolder) holder).achievedPercentageTv.setVisibility(View.GONE);
                ((DiscoverType1ViewHolder) holder).pendingPercentageTv.setVisibility(View.GONE);
                ((DiscoverType1ViewHolder) holder).pendingTv.setTextColor(context.getResources().getColor(R.color.pending_green));
            }
        }
    }

    private class DiscoverType1ViewHolder extends RecyclerView.ViewHolder {

        TextView liabilityTitleTv;
        TextView liabilitySubTitle;
        TextView liabilitySubTitle2;
        TextView achievedTv;
        TextView achievedPercentageTv;
        TextView pendingTv;
        TextView pendingPercentageTv;
        View view3;
        ImageView imageSrc;

        public DiscoverType1ViewHolder(@NonNull View itemView) {
            super(itemView);
            liabilityTitleTv = itemView.findViewById(R.id.liabilityTitleTv);
            liabilitySubTitle = itemView.findViewById(R.id.liabilitySubTitle);
            liabilitySubTitle2 = itemView.findViewById(R.id.liabilitySubTitle2);
            achievedTv = itemView.findViewById(R.id.btn_perform_action);
            achievedPercentageTv = itemView.findViewById(R.id.achievedPercentageTv);
            view3 = itemView.findViewById(R.id.view3);
            pendingTv = itemView.findViewById(R.id.pendingTv);
            pendingPercentageTv = itemView.findViewById(R.id.pendingPercentageTv);
            imageSrc = itemView.findViewById(R.id.item_image);
        }
    }

    @Override
    public int getItemCount() {
        return liabilityModels.size();
    }
}

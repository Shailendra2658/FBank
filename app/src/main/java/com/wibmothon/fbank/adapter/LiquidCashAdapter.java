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
import com.wibmothon.fbank.model.DashboardModel;
import com.wibmothon.fbank.model.LCashModel;

import java.util.List;

public class LiquidCashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<LCashModel> lCashModels;
    Context context;

    public LiquidCashAdapter(List<LCashModel> lCashModels, Context context) {
        this.lCashModels = lCashModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lc_recyclerview_item, parent, false);
                return new DiscoverType1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LCashModel lCashModel = lCashModels.get(position);
        if (lCashModel != null) {
            ((DiscoverType1ViewHolder) holder).LCTitle.setText(lCashModel.getLCTitle());

            if (position == 0) {
                ((DiscoverType1ViewHolder) holder).LCSubTitle1.setText(lCashModel.getLCSubTitle1());
                ((DiscoverType1ViewHolder) holder).LCSubTitle1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                ((DiscoverType1ViewHolder) holder).LCSubTitle1.setText(lCashModel.getLCSubTitle1());
                ((DiscoverType1ViewHolder) holder).LCSubTitle1.setTextColor(context.getResources().getColor(R.color.black));
            }


            ((DiscoverType1ViewHolder) holder).LCSubTitle2.setText(lCashModel.getLCSubTitle2());
            ((DiscoverType1ViewHolder) holder).payTitle.setText(lCashModel.getPayTitle());
            ((DiscoverType1ViewHolder) holder).imageSrc.setImageResource(lCashModel.getImageSrc());
        }
    }

    private class DiscoverType1ViewHolder extends RecyclerView.ViewHolder {

        TextView LCTitle;
        TextView LCSubTitle1;
        TextView LCSubTitle2;
        TextView payTitle;
        ImageView imageSrc;

        public DiscoverType1ViewHolder(@NonNull View itemView) {
            super(itemView);
            LCTitle = itemView.findViewById(R.id.txt_title);
            LCSubTitle1 = itemView.findViewById(R.id.txt_subtitle);
            LCSubTitle2 = itemView.findViewById(R.id.LCSubTitle2);
            payTitle = itemView.findViewById(R.id.btn_perform_action);
            imageSrc = itemView.findViewById(R.id.item_image);
        }
    }

    @Override
    public int getItemCount() {
        return lCashModels.size();
    }
}

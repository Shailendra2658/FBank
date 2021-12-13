package com.wibmothon.fbank.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.model.DashboardModel;

import java.util.List;

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DashboardModel> dashboardModelList;
    Context context;

    public DashboardRecyclerViewAdapter(List<DashboardModel> dashboardModelList, Context context) {
        this.dashboardModelList = dashboardModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
                return new DiscoverType1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DashboardModel discoverServices = dashboardModelList.get(position);
        if (discoverServices != null) {
            ((DiscoverType1ViewHolder) holder).txt_title.setText(discoverServices.getTitleText());
            ((DiscoverType1ViewHolder) holder).txt_subtitle.setText(discoverServices.getSubTitle());
        }
    }

    @Override
    public int getItemCount() {
        return dashboardModelList.size();
    }

    private class DiscoverType1ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title;
        TextView txt_subtitle;
        ImageView item_image;
        TextView btn_perform_action;

        public DiscoverType1ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_subtitle = itemView.findViewById(R.id.txt_subtitle);
            item_image = itemView.findViewById(R.id.item_image);
            btn_perform_action = itemView.findViewById(R.id.btn_perform_action);
        }
    }
}

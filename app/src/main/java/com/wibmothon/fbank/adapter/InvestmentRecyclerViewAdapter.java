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
import com.wibmothon.fbank.model.InvestmentModel;

import java.util.List;

public class InvestmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<InvestmentModel> investmentModels;
    Context context;

    public InvestmentRecyclerViewAdapter(List<InvestmentModel> investmentModels, Context context) {
        this.investmentModels = investmentModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_recyclerview_item, parent, false);
                return new DiscoverType1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InvestmentModel investmentModel = investmentModels.get(position);
        if (investmentModel != null) {
            ((DiscoverType1ViewHolder) holder).investItemTitleTv.setText(investmentModel.getInvestmentTitle());
            ((DiscoverType1ViewHolder) holder).investItemIv.setImageResource(investmentModel.getImageSrc());
        }
    }

    @Override
    public int getItemCount() {
        return investmentModels.size();
    }

    private class DiscoverType1ViewHolder extends RecyclerView.ViewHolder {

        TextView investItemTitleTv;
        ImageView investItemIv;

        public DiscoverType1ViewHolder(@NonNull View itemView) {
            super(itemView);
            investItemTitleTv = itemView.findViewById(R.id.investItemTitleTv);
            investItemIv = itemView.findViewById(R.id.investItemIv);
        }
    }
}


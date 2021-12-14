package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.imgBack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.DashboardRecyclerViewAdapter;
import com.wibmothon.fbank.adapter.LiquidCashAdapter;
import com.wibmothon.fbank.model.DashboardModel;
import com.wibmothon.fbank.model.LCashModel;

import java.util.ArrayList;
import java.util.List;

public class LiquidCashFragment extends Fragment {

    RecyclerView recyclerViewLC;

    String[] LCTitle = {"Bank Accounts", "Spend Analayzer", "Split Pay", "Bill Payments"};
    String[] LCSubTitle1 = {"3 Savings A/c & 1 Current A/c",
            "Last Month - Rs 30 K",
            "Total Balance - Rs 10 L",
            "Electricity Bill - Rs 2 K"};
    String[] LCSubTitle2 = {"Total Balance - Rs 10 L",
            "Current Month - Rs 15 K",
            "Outstanding Amount - Rs 5 K",
            "Mobile Recharge - Rs 500"};
    String[] payTitle = {"View All >",
            "View Spends >",
            "Pay >",
            "Pay >"};

    List<LCashModel> lCashModels;
    ImageView downArrowImgView;
    CardView cardViewLc;
    boolean isShown = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cash_management, container, false);

        imgBack.setVisibility(View.VISIBLE);

        imgBack.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                    homeFragment).commit();
            imgBack.setVisibility(View.INVISIBLE);
        });

        lCashModels = new ArrayList<>();

        for (int i = 0; i < LCTitle.length; i++) {
            LCashModel lCashModel = new LCashModel();
            lCashModel.setLCTitle(LCTitle[i]);
            lCashModel.setLCSubTitle1(LCSubTitle1[i]);
            lCashModel.setLCSubTitle2(LCSubTitle2[i]);
            lCashModel.setPayTitle(payTitle[i]);
            lCashModels.add(lCashModel);
        }

        recyclerViewLC = view.findViewById(R.id.recyclerViewLC);
        downArrowImgView = view.findViewById(R.id.downArrowImgView);
        cardViewLc = view.findViewById(R.id.cardViewLc);

        LiquidCashAdapter adapter = new LiquidCashAdapter(lCashModels, getActivity());
        recyclerViewLC.setHasFixedSize(true);
        recyclerViewLC.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLC.setAdapter(adapter);

        downArrowImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShown) {
                    cardViewLc.setVisibility(View.VISIBLE);
                    isShown = false;
                    downArrowImgView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
                } else {
                    cardViewLc.setVisibility(View.GONE);
                    isShown = true;
                    downArrowImgView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));
                }
            }
        });

        return view;
    }
}
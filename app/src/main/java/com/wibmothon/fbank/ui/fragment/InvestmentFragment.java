package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.imgBack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.InvestmentRecyclerViewAdapter;
import com.wibmothon.fbank.model.InvestmentModel;

import java.util.ArrayList;
import java.util.List;

public class InvestmentFragment extends Fragment {

    RecyclerView investmentRecyclerView;
    String[] titleStr = {"Mutual Funds", "Debt",
            "In Stocks", "Gold",
            "Real Estate", "PPF",
            "FD", "NPS",
            "Govt.Bonds", "PPF",
            "Crypto"};

    int[] imageId = {R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24};

    List<InvestmentModel> investmentModel;
    NestedScrollView nestedScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_investment_fragment, container, false);

        imgBack.setVisibility(View.VISIBLE);

        investmentModel = new ArrayList<>();

        for (int i = 0; i < titleStr.length; i++) {
            InvestmentModel investmentModels = new InvestmentModel();
            investmentModels.setInvestmentTitle(titleStr[i]);
            investmentModels.setImageSrc(imageId[i]);
            investmentModel.add(investmentModels);
        }

        investmentRecyclerView = view.findViewById(R.id.investmentRecyclerView);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

        InvestmentRecyclerViewAdapter adapter = new InvestmentRecyclerViewAdapter(investmentModel, getActivity());
        investmentRecyclerView.setHasFixedSize(false);
        investmentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        investmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        investmentRecyclerView.setAdapter(adapter);


        imgBack.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                    homeFragment).commit();
            imgBack.setVisibility(View.INVISIBLE);
        });

        return view;
    }
}
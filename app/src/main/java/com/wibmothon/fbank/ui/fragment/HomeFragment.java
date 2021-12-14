package com.wibmothon.fbank.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.DashboardRecyclerViewAdapter;
import com.wibmothon.fbank.model.DashboardModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    String[] titleStr = {"Investments", "Insurance", "Liquid Cash", "Liability"};
    String[] subTitleStr = {"View your total investments & grow your money",
            "Build a safety net for you & your loved ones",
            "Manage your bills and other expenses",
            "Manage your loan & EMIs"};

    List<DashboardModel> dashboardModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        dashboardModelList = new ArrayList<>();

        for (int i = 0; i < titleStr.length; i++) {
            DashboardModel dashboardModel = new DashboardModel();
            dashboardModel.setTitleText(titleStr[i]);
            dashboardModel.setSubTitle(subTitleStr[i]);
            dashboardModelList.add(dashboardModel);
        }

        recyclerView = view.findViewById(R.id.recyclerView);

        DashboardRecyclerViewAdapter adapter = new DashboardRecyclerViewAdapter(dashboardModelList, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        InvestmentFragment investmentFragment = new InvestmentFragment();
        LiquidCashFragment cashManagementFragment = new LiquidCashFragment();

        recyclerView.addOnItemTouchListener(new DashboardRecyclerViewAdapter.RecyclerTouchListener(getActivity(), recyclerView, new DashboardRecyclerViewAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, investmentFragment).commit();
                } else if (position == 2) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, cashManagementFragment).commit();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }
}
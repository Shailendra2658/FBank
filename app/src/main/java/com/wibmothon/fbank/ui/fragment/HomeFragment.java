package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.fab;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.DashboardRecyclerViewAdapter;
import com.wibmothon.fbank.model.DashboardModel;
import com.wibmothon.fbank.model.LiabilityModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    String[] titleStr = {"Investments", "Insurance", "Liquid Cash", "Liability"};
    String[] subTitleStr = {"View your total investments & grow your money",
            "Build a safety net for you & your loved ones",
            "Manage your bills and other expenses",
            "Manage your loan & EMIs"};

    String[] titleSubText1 = {"₹ 11 L", "2 Insurance", "₹ 5 L", "₹ 2 L"};
    String[] titleSubText2 = {"+1L", "0", "0", "@ 12%"};

    int[] dashboardDrawable = new int[]{
            R.drawable.ic_dashboard1,
            R.drawable.ic_dashboard2,
            R.drawable.ic_dashboard3,
            R.drawable.ic_dashboard4,
    };

    List<DashboardModel> dashboardModelList;
    ConstraintLayout dashboardParentConst;
    NestedScrollView dashboardNested;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        dashboardModelList = new ArrayList<>();

        for (int i = 0; i < titleStr.length; i++) {
            DashboardModel dashboardModel = new DashboardModel();
            dashboardModel.setTitleText(titleStr[i]);
            dashboardModel.setSubTitle(subTitleStr[i]);
            dashboardModel.setImageSrc(dashboardDrawable[i]);
            dashboardModel.setTitleSubText1(titleSubText1[i]);
            dashboardModel.setTitleSubText2(titleSubText2[i]);
            dashboardModelList.add(dashboardModel);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        dashboardParentConst = view.findViewById(R.id.dashboardParentConst);

        dashboardNested = view.findViewById(R.id.dashboardNested);

        DashboardRecyclerViewAdapter adapter = new DashboardRecyclerViewAdapter(dashboardModelList, getActivity());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        InvestmentFragment investmentFragment = new InvestmentFragment();
        LiquidCashFragment cashManagementFragment = new LiquidCashFragment();
        LiabilityFragment liabilityFragment = new LiabilityFragment();

        recyclerView.addOnItemTouchListener(new DashboardRecyclerViewAdapter.RecyclerTouchListener(getActivity(), recyclerView, new DashboardRecyclerViewAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, investmentFragment).commit();
                } else if (position == 2) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, cashManagementFragment).commit();
                } else if (position == 3) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, liabilityFragment).commit();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        dashboardNested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int dx, int dy, int oldScrollX, int oldScrollY) {
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                } else {
                    fab.show();
                }
                View view = (View) dashboardNested.getChildAt(dashboardNested.getChildCount() - 1);
                int diff = (view.getBottom() - (v.getHeight() + dashboardNested
                        .getScrollY()));

                if (diff == 0) {
                    fab.show();
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }

}
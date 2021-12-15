package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.bottomNavigationView;
import static com.wibmothon.fbank.ui.Dashboard.fab;
import static com.wibmothon.fbank.ui.Dashboard.headerCircleImage;
import static com.wibmothon.fbank.ui.Dashboard.headerImage;
import static com.wibmothon.fbank.ui.Dashboard.imgBack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.AdvisoryAdapter;
import com.wibmothon.fbank.adapter.InProgressGoalAdapter;
import com.wibmothon.fbank.model.AdvisoryModel;
import com.wibmothon.fbank.model.InProgressGoalsModel;

import java.util.ArrayList;
import java.util.List;

public class AdvisoryFragment extends Fragment {

    String[] advisoryHeader = {"Advisory ",
            "Alert ",
            "Alert "};
    String[] titleAppand = {"• 15th December 2021", "• 10th December 2021", "• 28th November 2021"};
    String[] advisoryTitle = {"Save Rs 5476.52 on commisions",
            "Your Daily Portfolio Report",
            "New PAYTM IPO coming soon"};
    String[] advisorySubTitle = {"Switch to direct mutual fund -\nICICI Flexicap fund growth",
            "Your Indian stocks and Mutual\nFund portfolio moved up by ₹ 20K",
            "IPO opens tomorrow, should you\nsubscribe?"};

    int[] advisoryDrawable = new int[]{
            R.drawable.ic_adviosry1,
            R.drawable.ic_adviosry2,
            R.drawable.ic_adviosry3,
    };

    List<AdvisoryModel> advisoryModels;
    RecyclerView advisoryRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_advisory_fragment, container, false);

        advisoryModels = new ArrayList<>();

        for (int i = 0; i < advisoryHeader.length; i++) {
            AdvisoryModel advisoryModel = new AdvisoryModel();
            advisoryModel.setAdvisoryHeader(advisoryHeader[i]);
            advisoryModel.setGoalYrTv2(titleAppand[i]);
            advisoryModel.setAdvisoryTitle(advisoryTitle[i]);
            advisoryModel.setAdvisorySubTitle(advisorySubTitle[i]);
            advisoryModel.setAdvisoryDrawable(advisoryDrawable[i]);
            advisoryModels.add(advisoryModel);
        }

        advisoryRecyclerView = view.findViewById(R.id.advisoryRecyclerView);

        AdvisoryAdapter adapter = new AdvisoryAdapter(advisoryModels, getActivity());
        advisoryRecyclerView.setHasFixedSize(true);
        advisoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        advisoryRecyclerView.setAdapter(adapter);

        imgBack.setVisibility(View.VISIBLE);
        headerCircleImage.setVisibility(View.VISIBLE);
        headerImage.setVisibility(View.GONE);

        imgBack.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                    homeFragment).commit();
            imgBack.setVisibility(View.INVISIBLE);
            headerCircleImage.setVisibility(View.INVISIBLE);
            headerImage.setVisibility(View.VISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.bottom_dashboard);
        });

        advisoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
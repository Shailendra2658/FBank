package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.bottomNavigationView;
import static com.wibmothon.fbank.ui.Dashboard.fab;
import static com.wibmothon.fbank.ui.Dashboard.headerCircleImage;
import static com.wibmothon.fbank.ui.Dashboard.headerImage;
import static com.wibmothon.fbank.ui.Dashboard.imgBack;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.AdvisoryAdapter;
import com.wibmothon.fbank.adapter.InProgressGoalAdapter;
import com.wibmothon.fbank.model.AdvisoryModel;
import com.wibmothon.fbank.model.InProgressGoalsModel;
import com.wibmothon.fbank.model.UserData;

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

    TextView memberTv;
    TextView memberTvCircle;
    ImageView downArrowMember;

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

        memberTv = view.findViewById(R.id.memberTv);
        memberTvCircle = view.findViewById(R.id.memberTvCircle);
        downArrowMember = view.findViewById(R.id.downArrowMember);

        memberTv.setText("Vijay P");
        memberTvCircle.setText("VP");

        downArrowMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity());
            }
        });

        return view;
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.customdialog);

        ImageView downArrow1ImgView = dialog.findViewById(R.id.downArrow1ImgView);
        ImageView downArrow2ImgView = dialog.findViewById(R.id.downArrow2ImgView);
        ImageView downArrow3ImgView = dialog.findViewById(R.id.downArrow3ImgView);

        downArrow1ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText("Vijay P");
                memberTvCircle.setText("VP");
                dialog.dismiss();
            }
        });

        downArrow2ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText("Shilpa P");
                memberTvCircle.setText("SP");
                dialog.dismiss();
            }
        });

        downArrow3ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText("Rama N");
                memberTvCircle.setText("RN");
                Log.d("userDataValues", "UserData " + UserData.rBal);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
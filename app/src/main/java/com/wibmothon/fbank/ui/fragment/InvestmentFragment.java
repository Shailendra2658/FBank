package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.bottomNavigationView;
import static com.wibmothon.fbank.ui.Dashboard.fab;
import static com.wibmothon.fbank.ui.Dashboard.headerCircleImage;
import static com.wibmothon.fbank.ui.Dashboard.headerImage;
import static com.wibmothon.fbank.ui.Dashboard.imgBack;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.InvestmentRecyclerViewAdapter;
import com.wibmothon.fbank.model.InvestmentModel;
import com.wibmothon.fbank.model.UserData;

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

    String[] investmentSubTv = {"₹ 2L", "₹ 1L",
            "₹ 1L", "0",
            "₹ 7L", "0",
            "0", "0",
            "0", "0",
            "0"};

    int[] imageId = {R.drawable.ic_investments1,
            R.drawable.ic_investments2,
            R.drawable.ic_investments3,
            R.drawable.ic_investments4,
            R.drawable.ic_investments5,
            R.drawable.ic_investments6,
            R.drawable.ic_investments7,
            R.drawable.ic_investments8,
            R.drawable.ic_investments9,
            R.drawable.ic_investments10,
            R.drawable.ic_investments11};

    List<InvestmentModel> investmentModel;
    NestedScrollView nestedScrollView;

    ImageView downArrowImgView;
    CardView cardViewLc;
    boolean isShown = true;

    TextView memberTv;
    TextView memberTvCircle;
    ImageView downArrowMember;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_investment_fragment, container, false);

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

        investmentModel = new ArrayList<>();

        for (int i = 0; i < titleStr.length; i++) {
            InvestmentModel investmentModels = new InvestmentModel();
            investmentModels.setInvestmentTitle(titleStr[i]);
            investmentModels.setImageSrc(imageId[i]);
            investmentModels.setInvestmentSubTv(investmentSubTv[i]);
            investmentModel.add(investmentModels);
        }

        investmentRecyclerView = view.findViewById(R.id.investmentRecyclerView);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        downArrowImgView = view.findViewById(R.id.downArrowImgView);
        cardViewLc = view.findViewById(R.id.cardViewLc);

        InvestmentRecyclerViewAdapter adapter = new InvestmentRecyclerViewAdapter(investmentModel, getActivity());
        investmentRecyclerView.setHasFixedSize(false);
        investmentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        investmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        investmentRecyclerView.setAdapter(adapter);

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

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int dx, int dy, int oldScrollX, int oldScrollY) {
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });

        investmentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        memberTv.setText(UserData.name);
        memberTvCircle.setText(UserData.name.toCharArray()[0]+""+ UserData.name.toCharArray()[UserData.name.length()-1]);

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

        ((TextView)  dialog.findViewById(R.id.member1Tv)).setText(UserData.name);
        ((TextView)  dialog.findViewById(R.id.member2Tv)).setText(UserData.sName);
        ((TextView)  dialog.findViewById(R.id.member3Tv)).setText(UserData.rName);

        downArrow1ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText(UserData.name);
                memberTvCircle.setText(UserData.name.toCharArray()[0]+""+ UserData.name.toCharArray()[UserData.name.length()-1]);

                dialog.dismiss();
            }
        });

        downArrow2ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText(UserData.sName);
                memberTvCircle.setText(UserData.sName.toCharArray()[0]+""+ UserData.sName.toCharArray()[UserData.sName.length()-1]);
                dialog.dismiss();
            }
        });

        downArrow3ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText(UserData.rName);
                memberTvCircle.setText(UserData.rName.toCharArray()[0]+""+ UserData.rName.toCharArray()[UserData.rName.length()-1]);
                Log.d("userDataValues", "UserData " + UserData.rBal);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
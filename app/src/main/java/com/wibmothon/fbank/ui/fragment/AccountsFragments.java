package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.bottomNavigationView;
import static com.wibmothon.fbank.ui.Dashboard.fab;
import static com.wibmothon.fbank.ui.Dashboard.headerCircleImage;
import static com.wibmothon.fbank.ui.Dashboard.headerImage;
import static com.wibmothon.fbank.ui.Dashboard.imgBack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.LiquidCashAdapter;
import com.wibmothon.fbank.model.LCashModel;
import com.wibmothon.fbank.model.UserData;

public class AccountsFragments extends Fragment {

    NestedScrollView accountsNestedScroll;
    TextView memberTv;
    TextView memberTvCircle;
    ImageView downArrowMember;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_accounts_fragments, container, false);

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

        accountsNestedScroll = view.findViewById(R.id.accountsNestedScroll);
        downArrowMember = view.findViewById(R.id.downArrowMember);

        accountsNestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int dx, int dy, int oldScrollX, int oldScrollY) {
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                } else {
                    fab.show();
                }
                View view = (View) accountsNestedScroll.getChildAt(accountsNestedScroll.getChildCount() - 1);
                int diff = (view.getBottom() - (accountsNestedScroll.getHeight() + accountsNestedScroll
                        .getScrollY()));

                if (diff == 0) {
                    fab.show();
                }
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
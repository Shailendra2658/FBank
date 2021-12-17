package com.wibmothon.fbank.ui.fragment;

import static com.wibmothon.fbank.ui.Dashboard.bottomNavigationView;
import static com.wibmothon.fbank.ui.Dashboard.fab;
import static com.wibmothon.fbank.ui.Dashboard.headerCircleImage;
import static com.wibmothon.fbank.ui.Dashboard.headerImage;
import static com.wibmothon.fbank.ui.Dashboard.imgBack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.wibmothon.fbank.adapter.LiabilityAdapter;
import com.wibmothon.fbank.adapter.LiquidCashAdapter;
import com.wibmothon.fbank.model.LCashModel;
import com.wibmothon.fbank.model.LiabilityModel;
import com.wibmothon.fbank.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class LiabilityFragment extends Fragment {

    RecyclerView recyclerViewLC;

    String[] LCTitle = {"Education Loan", "Home Loan", "EMI Payments", "List of Credit Cards"};

    String[] LCSubTitle1 = {"Edu Loan EMI",
            "Home Loan EMI",
            "Edu Loan EMI",
            "xxxx-5200(Axis)"};

    String[] LCSubTitle2 = {"Outstanding 2L",
            "Outstanding 20L",
            "Outstanding 2L",
            "Outstanding 2K"};

    String[] achievedTv = {"Achieved",
            "Achieved",
            "Home Loan EMI",
            "xxxx-4609(RBL)"};

    String[] achievedPercentageTv = {"- 55%",
            "- 55%",
            "0",
            "0"};

    String[] pendingTv = {"Pending",
            "Pending",
            "Outstanding 10K",
            "Outstanding 10K"};

    String[] pendingPercentageTv = {"- 45%",
            "- 45%",
            "0",
            "0"};

    int[] dashboardDrawable = new int[]{
            R.drawable.ic_lia1,
            R.drawable.ic_lia2,
            R.drawable.ic_lia3,
            R.drawable.ic_lia4,
    };

    List<LiabilityModel> liabilityModels;
    ImageView downArrowImgView;
    CardView cardViewLc;
    boolean isShown = true;

    TextView memberTv;
    TextView memberTvCircle;
    ImageView downArrowMember;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_liability_fragment, container, false);

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

        liabilityModels = new ArrayList<>();

        for (int i = 0; i < LCTitle.length; i++) {
            LiabilityModel liabilityModel = new LiabilityModel();
            liabilityModel.setLiabilityTitle(LCTitle[i]);
            liabilityModel.setLiabilitySubTitle(LCSubTitle1[i]);
            liabilityModel.setLiabilitySubTitle2(LCSubTitle2[i]);
            liabilityModel.setAchievedTitle(achievedTv[i]);
            liabilityModel.setAchievedPercentageTv(achievedPercentageTv[i]);
            liabilityModel.setPendingTv(pendingTv[i]);
            liabilityModel.setPendingPercentageTv(pendingPercentageTv[i]);
            liabilityModel.setImageSrc(dashboardDrawable[i]);
            liabilityModels.add(liabilityModel);
        }

        recyclerViewLC = view.findViewById(R.id.recyclerViewLC);
        downArrowImgView = view.findViewById(R.id.downArrowImgView);
        cardViewLc = view.findViewById(R.id.cardViewLc);

        LiabilityAdapter adapter = new LiabilityAdapter(liabilityModels, getActivity());
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

        recyclerViewLC.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
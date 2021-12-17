package com.wibmothon.fbank.ui.fragment;

import static com.sound.waves.Common.DEFAULT_CODE_BOOK;
import static com.wibmothon.fbank.ui.Dashboard.bottomNavigationView;
import static com.wibmothon.fbank.ui.Dashboard.fab;
import static com.wibmothon.fbank.ui.Dashboard.headerCircleImage;
import static com.wibmothon.fbank.ui.Dashboard.headerImage;
import static com.wibmothon.fbank.ui.Dashboard.imgBack;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_RECG_END;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_RECG_START;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_SET_RECG_TEXT;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sound.waves.SinVoicePlayer;
import com.sound.waves.SinVoiceRecognition;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.DashboardRecyclerViewAdapter;
import com.wibmothon.fbank.adapter.LiquidCashAdapter;
import com.wibmothon.fbank.model.DashboardModel;
import com.wibmothon.fbank.model.LCashModel;
import com.wibmothon.fbank.model.UserData;
import com.wibmothon.fbank.ui.ReceiveActivity;
import com.wibmothon.fbank.ui.SearchActivity;
import com.wibmothon.fbank.ui.SummaryActivity;
import com.wibmothon.fbank.util.CircleTextView;
import com.wibmothon.fbank.util.Util;

import java.util.ArrayList;
import java.util.List;

public class LiquidCashFragment extends Fragment implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {

    private final static String TAG = "LiquidCashFragment";

    RecyclerView recyclerViewLC;
    boolean isRegStart;

    /*LCTitle[0] = "Family Mint wallet";
        LCSubTitle1[0] = "Wallet balance: ₹ " + UserData.vBal;
        LCSubTitle2[0] = "XXXX-9785";
        payTitle[0] = "View All >";
        dashboardDrawable[0] = R.drawable.ic_lc3;*/

    String[] LCTitle = {"Family Mint wallet", "Bank Accounts",
            "Spend Analayzer", "Split Pay", "Bill Payments"};


    String[] payTitle = {"View All >",
            "View All >",
            "View Spends >",
            "Pay >",
            "Pay >"};

    int[] dashboardDrawable = new int[]{
            R.drawable.ic_lc0,
            R.drawable.ic_lc3,
            R.drawable.ic_lc4,
            R.drawable.ic_lc5,
            R.drawable.ic_lc6,
    };

    List<LCashModel> lCashModels;
    ImageView downArrowImgView;
    CardView cardViewLc;
    boolean isShown = true;
    private TextView txtSubtitle, sendMoneyTxtTv, recMoneyTxtTv;
    private SinVoicePlayer mSinVoicePlayer;
    private SinVoiceRecognition mRecognition;
    private Switch switchVisibility;
    private StringBuilder mTextBuilder = new StringBuilder();
    private Handler mHanlder;

    TextView memberTv;
    TextView memberTvCircle;
    ImageView downArrowMember;

    ImageView switchVisibilityImg;

    boolean imageViewClicked = true;
    String extra;

    public LiquidCashFragment(String hasExtra) {
        this.extra = hasExtra;
    }

    public LiquidCashFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cash_management, container, false);

        mSinVoicePlayer = new SinVoicePlayer(DEFAULT_CODE_BOOK);
        mSinVoicePlayer.setListener(this);
        mRecognition = new SinVoiceRecognition(DEFAULT_CODE_BOOK);
        mRecognition.setListener(this);
        mHanlder = new Util.RegHandler(new TextView(getActivity()));

        switchVisibility = view.findViewById(R.id.switchVisibility);
        memberTv = view.findViewById(R.id.memberTv);
        memberTvCircle = view.findViewById(R.id.memberTvCircle);

        downArrowMember = view.findViewById(R.id.downArrowMember);
        switchVisibilityImg = view.findViewById(R.id.switchVisibilityImg);

       switchVisibility.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                mSinVoicePlayer.play("1", true, 1000);
            else {
                mSinVoicePlayer.stop();
                if (!isRegStart) {
                    isRegStart = true;
                    mRecognition.start();
                }
            }
        });

        switchVisibilityImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewClicked) {
                    switchVisibilityImg.setImageResource(R.drawable.ic_lc7);
                    imageViewClicked = false;
                    mSinVoicePlayer.play("1", true, 1000);
                } else {
                    switchVisibilityImg.setImageResource(R.drawable.ic_lc8);
                    imageViewClicked = true;
                    mSinVoicePlayer.stop();
                    if (!isRegStart) {
                        isRegStart = true;
                        mRecognition.start();
                    }
                }

            }
        });

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

        lCashModels = new ArrayList<>();

        if(extra!=null && !extra.isEmpty() && extra.startsWith("Vijay")){
            memberTv.setText(UserData.name);
            memberTvCircle.setText(UserData.name.toCharArray()[0]+""+ UserData.name.toCharArray()[UserData.name.length()-1]);

        }else {
            memberTv.setText(UserData.sName);
            memberTvCircle.setText(UserData.sName.toCharArray()[0] + "" + UserData.sName.toCharArray()[UserData.sName.length() - 1]);
        }

        String[] LCSubTitle1 = {" ₹ " + ((extra!=null  && !extra.isEmpty() && extra.startsWith("Vijay"))?UserData.vBal:UserData.sBal), "3 Savings A/c & 1 Current A/c",
                "Last Month - Rs 30 K",
                "Total Balance - Rs 10 L",
                "Electricity Bill - Rs 2 K"};

        String[] LCSubTitle2 = {"XXXX-9785", "Total Balance - Rs 10 L",
                "Current Month - Rs 15 K",
                "Outstanding Amount - Rs 5 K",
                "Mobile Recharge - Rs 500"};

        for (int i = 0; i < LCTitle.length; i++) {
            LCashModel lCashModel = new LCashModel();
            lCashModel.setLCTitle(LCTitle[i]);
            lCashModel.setLCSubTitle1(LCSubTitle1[i]);
            lCashModel.setLCSubTitle2(LCSubTitle2[i]);
            lCashModel.setPayTitle(payTitle[i]);
            lCashModel.setImageSrc(dashboardDrawable[i]);
            lCashModels.add(lCashModel);
        }

        recyclerViewLC = view.findViewById(R.id.recyclerViewLC);
        downArrowImgView = view.findViewById(R.id.downArrowImgView);
        cardViewLc = view.findViewById(R.id.cardViewLc);
        sendMoneyTxtTv = view.findViewById(R.id.sendMoneyTxtTv);
        recMoneyTxtTv = view.findViewById(R.id.requestMoneyTv);


        LiquidCashAdapter adapter = new LiquidCashAdapter(lCashModels, getActivity());
        recyclerViewLC.setHasFixedSize(true);
        recyclerViewLC.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLC.setAdapter(adapter);

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

        sendMoneyTxtTv.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        });
        // mSinVoicePlayer.play("1",true, 1000);

        recMoneyTxtTv.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReceiveActivity.class));
        });

        downArrowMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Util.getDataFromFirebase();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecognition != null)
            mRecognition.stop();

        if (mSinVoicePlayer != null)
            mSinVoicePlayer.stop();
    }

    @Override
    public void onPlayStart() {

    }

    @Override
    public void onPlayEnd() {

    }

    @Override
    public void onRecognitionStart() {
        mHanlder.sendEmptyMessage(MSG_RECG_START);
        mTextBuilder.delete(0, mTextBuilder.length());

    }

    @Override
    public void onRecognition(char ch) {
        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
        mTextBuilder.append(ch);

        if (mTextBuilder.toString().startsWith("4")) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    switchVisibility.setChecked(false);
                    mSinVoicePlayer.stop();
                    if (!isRegStart) {
                        isRegStart = true;
                        mRecognition.start();
                    }
                    mTextBuilder.delete(0, mTextBuilder.length());
                }
            });
        }
    }

    @Override
    public void onRecognitionEnd() {
        mHanlder.sendEmptyMessage(MSG_RECG_END);
        Log.i(TAG, "onRecognitionEnd " + mTextBuilder.toString());

        if (mTextBuilder.toString().startsWith("2")) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    String amount = mTextBuilder.toString().replace("3", "0").substring(1);
                    Log.i(TAG, "Amount Received" + amount);
                    Intent intent = new Intent(getActivity(), SummaryActivity.class);
                    intent.putExtra("EXTRA_STATUS_MSG", "Received " + "₹ " + amount + " Successfully!");
                    intent.putExtra("EXTRA_AMT", amount);
                    startActivity(intent);
                }
            });
        }

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

                Log.d("userDataValues", "UserData " + UserData.vBal);

                lCashModels.clear();

                String[] LCSubTitle1 = {" ₹ " + UserData.vBal, "3 Savings A/c & 1 Current A/c",
                        "Last Month - Rs 30 K",
                        "Total Balance - Rs 10 L",
                        "Electricity Bill - Rs 2 K"};

                String[] LCSubTitle2 = {"XXXX-9785", "Total Balance - Rs 10 L",
                        "Current Month - Rs 15 K",
                        "Outstanding Amount - Rs 5 K",
                        "Mobile Recharge - Rs 500"};

                for (int i = 0; i < LCTitle.length; i++) {
                    LCashModel lCashModel = new LCashModel();
                    lCashModel.setLCTitle(LCTitle[i]);
                    lCashModel.setLCSubTitle1(LCSubTitle1[i]);
                    lCashModel.setLCSubTitle2(LCSubTitle2[i]);
                    lCashModel.setPayTitle(payTitle[i]);
                    lCashModel.setImageSrc(dashboardDrawable[i]);
                    lCashModels.add(lCashModel);
                }

                LiquidCashAdapter adapter = new LiquidCashAdapter(lCashModels, getActivity());
                recyclerViewLC.setHasFixedSize(true);
                recyclerViewLC.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewLC.setAdapter(adapter);
                dialog.dismiss();
            }
        });

        downArrow2ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText(UserData.sName);
                memberTvCircle.setText(UserData.sName.toCharArray()[0]+""+ UserData.sName.toCharArray()[UserData.sName.length()-1]);

                Log.d("userDataValues", "UserData " + UserData.sBal);

                lCashModels.clear();

                String[] LCSubTitle1 = {" ₹ " + UserData.sBal, "3 Savings A/c & 1 Current A/c",
                        "Last Month - Rs 30 K",
                        "Total Balance - Rs 10 L",
                        "Electricity Bill - Rs 2 K"};

                String[] LCSubTitle2 = {"XXXX-6226", "Total Balance - Rs 10 L",
                        "Current Month - Rs 15 K",
                        "Outstanding Amount - Rs 5 K",
                        "Mobile Recharge - Rs 500"};

                for (int i = 0; i < LCTitle.length; i++) {
                    LCashModel lCashModel = new LCashModel();
                    lCashModel.setLCTitle(LCTitle[i]);
                    lCashModel.setLCSubTitle1(LCSubTitle1[i]);
                    lCashModel.setLCSubTitle2(LCSubTitle2[i]);
                    lCashModel.setPayTitle(payTitle[i]);
                    lCashModel.setImageSrc(dashboardDrawable[i]);
                    lCashModels.add(lCashModel);
                }

                LiquidCashAdapter adapter = new LiquidCashAdapter(lCashModels, getActivity());
                recyclerViewLC.setHasFixedSize(true);
                recyclerViewLC.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewLC.setAdapter(adapter);
                dialog.dismiss();
            }
        });

        downArrow3ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberTv.setText(UserData.rName);
                memberTvCircle.setText(UserData.rName.toCharArray()[0]+""+ UserData.rName.toCharArray()[UserData.rName.length()-1]);

                Log.d("userDataValues", "UserData " + UserData.rBal);

                lCashModels.clear();

                String[] LCSubTitle1 = {" ₹ " + UserData.rBal, "3 Savings A/c & 1 Current A/c",
                        "Last Month - Rs 30 K",
                        "Total Balance - Rs 10 L",
                        "Electricity Bill - Rs 2 K"};

                String[] LCSubTitle2 = {"XXXX-8000", "Total Balance - Rs 10 L",
                        "Current Month - Rs 15 K",
                        "Outstanding Amount - Rs 5 K",
                        "Mobile Recharge - Rs 500"};

                for (int i = 0; i < LCTitle.length; i++) {
                    LCashModel lCashModel = new LCashModel();
                    lCashModel.setLCTitle(LCTitle[i]);
                    lCashModel.setLCSubTitle1(LCSubTitle1[i]);
                    lCashModel.setLCSubTitle2(LCSubTitle2[i]);
                    lCashModel.setPayTitle(payTitle[i]);
                    lCashModel.setImageSrc(dashboardDrawable[i]);
                    lCashModels.add(lCashModel);
                }

                LiquidCashAdapter adapter = new LiquidCashAdapter(lCashModels, getActivity());
                recyclerViewLC.setHasFixedSize(true);
                recyclerViewLC.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewLC.setAdapter(adapter);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
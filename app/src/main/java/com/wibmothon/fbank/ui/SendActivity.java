package com.wibmothon.fbank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sound.waves.LogHelper;
import com.sound.waves.SinVoicePlayer;
import com.sound.waves.SinVoiceRecognition;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.model.UserData;
import com.wibmothon.fbank.util.Util;

import androidx.appcompat.app.AppCompatActivity;

import static com.sound.waves.Common.DEFAULT_CODE_BOOK;
import static java.lang.Integer.*;

public class SendActivity extends AppCompatActivity implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {

    private final static String TAG = "SendActivity";
    private final static int MAX_NUMBER = 5;
    private final static int MSG_SET_RECG_TEXT = 1;
    private final static int MSG_RECG_START = 2;
    private final static int MSG_RECG_END = 3;


    private Handler mHanlder;
    private SinVoicePlayer mSinVoicePlayer;
    private SinVoiceRecognition mRecognition;
    private ImageView imgSend, imgBack;
    private EditText amount;
    private TextView tvName, tvRecName, tvNumber, tvRecNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        imgSend = findViewById(R.id.imgSend);
        imgBack = findViewById(R.id.imageViewBack);
        amount = findViewById(R.id.editTextAmt);
        tvName = findViewById(R.id.tvName);
        tvRecName = findViewById(R.id.tvRecName);
        tvNumber = findViewById(R.id.tvNumber);
        tvRecNumber = findViewById(R.id.tvRecNumber);


        tvName.setText(UserData.name);
        tvRecName.setText(UserData.sName);
        tvNumber.setText("Balance: ₹"+UserData.vBal);
        //tvRecNumber.setText(UserData.sBal);


        mSinVoicePlayer = new SinVoicePlayer(DEFAULT_CODE_BOOK);
        mSinVoicePlayer.setListener(this);

        mRecognition = new SinVoiceRecognition(DEFAULT_CODE_BOOK);
        mRecognition.setListener(this);

        mHanlder = new Util.RegHandler(new TextView(this));

        mSinVoicePlayer.play("4", false, 1000);

        Glide.with(this)
                .asGif()
                .load(R.drawable.sending)
                .into(imgSend);

        imgSend.setOnClickListener(view -> {

            String amountStr = amount.getText().toString();
            UpdateBal();
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra("EXTRA_STATUS_MSG", "Sent Successfully!");
            intent.putExtra("EXTRA_AMT", amountStr);
            startActivity(intent);
        });

        imgBack.setOnClickListener(view -> {
            finish();
        });


    }

    private void UpdateBal() {
        String amountStr = amount.getText().toString();
        String amts = amountStr;
        int vBals = parseInt(UserData.vBal) -  parseInt(amts.trim());
        UserData.vBal =  vBals+"";
        Util.setDataFromFirebase(this, "Vbalance", UserData.vBal);

        if(tvRecName.getText().toString().equalsIgnoreCase(UserData.rName)){
            int rBals = parseInt(UserData.rBal) +  parseInt(amts);
            UserData.rBal =  rBals+"";
            Util.setDataFromFirebase(this, "Rbalance", UserData.rBal);

        }else if(tvRecName.getText().toString().equalsIgnoreCase(UserData.sName)){
            int sBals = parseInt(UserData.sBal) +  parseInt(amts);
            UserData.sBal =  sBals+"";
            Util.setDataFromFirebase(this, "Sbalance", UserData.sBal);

        }
            int balance = parseInt(UserData.vBal) + parseInt(UserData.rBal)  + parseInt(UserData.sBal);

        UserData.balance  = balance+"";
        Util.setDataFromFirebase(this, "balance", UserData.balance);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecognition.start();
        // playTextView.setText(text);
        //mSinVoicePlayer.play("111", true, 1000);

        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(new Intent(ReceiveActivity.this, SummaryActivity.class));

            }
        }, 5000);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRecognition != null)
            mRecognition.stop();

        if (mSinVoicePlayer != null)
            mSinVoicePlayer.stop();
    }

    @Override
    public void onRecognitionStart() {
        mHanlder.sendEmptyMessage(MSG_RECG_START);
    }

    @Override
    public void onRecognition(char ch) {
        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
    }

    @Override
    public void onRecognitionEnd() {
        mHanlder.sendEmptyMessage(MSG_RECG_END);
    }

    @Override
    public void onPlayStart() {
        LogHelper.d(TAG, "start play");
    }

    @Override
    public void onPlayEnd() {
        LogHelper.d(TAG, "stop play");
    }
}
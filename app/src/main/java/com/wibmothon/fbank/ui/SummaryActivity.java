package com.wibmothon.fbank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sound.waves.LogHelper;
import com.sound.waves.SinVoicePlayer;
import com.sound.waves.SinVoiceRecognition;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.model.UserData;
import com.wibmothon.fbank.ui.fragment.LiquidCashFragment;
import com.wibmothon.fbank.util.Util;

import static com.sound.waves.Common.DEFAULT_CODE_BOOK;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_RECG_END;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_RECG_START;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_SET_RECG_TEXT;
import static java.lang.Integer.parseInt;


public class SummaryActivity extends AppCompatActivity implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {
    private final static String TAG = "SummaryActivity";

    private ImageView imgSuccess;
    private TextView txtMsg;
    private SinVoicePlayer mSinVoicePlayer;
    private SinVoiceRecognition mRecognition;
    private Handler mHanlder;
    String msg ;
    String amt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        imgSuccess = findViewById(R.id.imageViewSuccess);
        txtMsg = findViewById(R.id.tvFullTitle);

        mSinVoicePlayer = new SinVoicePlayer(DEFAULT_CODE_BOOK);
        mSinVoicePlayer.setListener(this);

        mRecognition = new SinVoiceRecognition(DEFAULT_CODE_BOOK);
        mRecognition.setListener(this);

        mHanlder = new Util.RegHandler(new TextView(this));

         msg = getIntent().getStringExtra("EXTRA_STATUS_MSG");
         amt = getIntent().getStringExtra("EXTRA_AMT");


        txtMsg.setText(msg);

        //calculateBal();
        Glide.with(this)
                .asGif()
                .load(R.drawable.sent_small)
                .into(imgSuccess);

        if (msg.startsWith("Sent")) {
            String amts = "2" + amt.replace("0", "3");
            Log.i(TAG, "AmountSending "+amts);

            mSinVoicePlayer.play(amts, true, 1000);
        }else if(amt!=null && amt.isEmpty()){
            calculateBal();
        }


        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("EXTRA_CASH", msg.startsWith("Sent")?"Vijay":"Shilpa");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }, 5000);
    }

    private void calculateBal() {
        try {
            String amts = amt;
            int vBals = parseInt(UserData.vBal) - parseInt(amts);
            UserData.vBal = vBals + "";
            Util.setDataFromFirebase(this, "Vbalance", UserData.vBal);

            // if(tvRecName.getText().toString().equalsIgnoreCase(UserData.rName)){
            int rBals = parseInt(UserData.rBal) + parseInt(amts);
            UserData.rBal = rBals + "";
            Util.setDataFromFirebase(this, "Rbalance", UserData.rBal);

            /*}else if(tvRecName.getText().toString().equalsIgnoreCase(UserData.sName)){
                int sBals = parseInt(UserData.sBal) +  parseInt(amts);
                UserData.sBal =  sBals+"";
                Util.setDataFromFirebase(this, "Sbalance", UserData.sBal);

            }*/
            int balance = parseInt(UserData.vBal) + parseInt(UserData.rBal) + parseInt(UserData.sBal);

            UserData.balance = balance + "";
            Util.setDataFromFirebase(this, "balance", UserData.balance);
        }catch(Exception ex){
            Log.e(TAG,"Error "+ex);
        }
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
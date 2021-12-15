package com.wibmothon.fbank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sound.waves.LogHelper;
import com.sound.waves.SinVoicePlayer;
import com.sound.waves.SinVoiceRecognition;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.util.Util;

import androidx.appcompat.app.AppCompatActivity;

import static com.sound.waves.Common.DEFAULT_CODE_BOOK;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_SET_RECG_TEXT;

public class ReceiveActivity extends AppCompatActivity implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {

    private final static String TAG = "ReceiveActivity";
    private final static int MAX_NUMBER = 5;
    private final static int MSG_SET_RECG_TEXT = 1;
    private final static int MSG_RECG_START = 2;
    private final static int MSG_RECG_END = 3;

    private boolean isWaiting;
    private Handler mHanlder;
    private SinVoicePlayer mSinVoicePlayer;
    private SinVoiceRecognition mRecognition;
    private ImageView imageViewScan, imageViewPerson;
    private LinearLayout linearPerson;
    private ImageView imgSuccess;
    private TextView txtStatus;
    private StringBuilder mTextBuilder = new StringBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mSinVoicePlayer = new SinVoicePlayer(DEFAULT_CODE_BOOK);
        mSinVoicePlayer.setListener(this);

        mRecognition = new SinVoiceRecognition(DEFAULT_CODE_BOOK);
        mRecognition.setListener(this);

        //final TextView playTextView = (TextView) findViewById(R.id.playtext);
        //TextView recognisedTextView = (TextView) findViewById(R.id.regtext);
        txtStatus =  findViewById(R.id.tvFullTitle);

        txtStatus.setText("Waiting to receive...");
        mHanlder = new Util.RegHandler(new TextView(this));

        imgSuccess = findViewById(R.id.imageViewSuccess);

        Glide.with(this)
                .asGif()
                .load(R.drawable.waiting)
                .into(imgSuccess);



       // Button playStart = (Button) this.findViewById(R.id.start_play);
//        playStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {

//            }
//        });

       // Button playStop = (Button) this.findViewById(R.id.stop_play);
        /*playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mSinVoicePlayer.stop();
            }
        });*/

        /*Button recognitionStart = (Button) this.findViewById(R.id.start_reg);
        recognitionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mRecognition.start();
            }
        });

        Button recognitionStop = (Button) this.findViewById(R.id.stop_reg);
        recognitionStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mRecognition.stop();
            }
        });*/
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
                startActivity(new Intent(ReceiveActivity.this, SummaryActivity.class));

            }
        }, 500);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRecognition!=null)
            mRecognition.stop();

        if(mSinVoicePlayer!=null)
            mSinVoicePlayer.stop();
    }

    @Override
    public void onRecognitionStart() {
        mHanlder.sendEmptyMessage(MSG_RECG_START);
    }

    @Override
    public void onRecognition(char ch) {
        mTextBuilder.append(ch);
        isWaiting = true;
        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
    }

    @Override
    public void onRecognitionEnd() {
        mHanlder.sendEmptyMessage(MSG_RECG_END);
            Log.i(TAG, "Amount "+mTextBuilder.toString().replace("3","0"));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    String amount = mTextBuilder.toString().replace("3","0").substring(1);
                    Intent intent = new Intent(ReceiveActivity.this, SummaryActivity.class);
                    intent.putExtra("EXTRA_STATUS_MSG", "Received "+"₹ "+amount+" Successfully!");
                    startActivity(intent);
                }
            });
    //    }
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
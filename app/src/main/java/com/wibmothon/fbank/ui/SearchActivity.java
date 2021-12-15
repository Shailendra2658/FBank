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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;
import static com.sound.waves.Common.DEFAULT_CODE_BOOK;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_RECG_END;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_RECG_START;
import static com.wibmothon.fbank.util.Util.RegHandler.MSG_SET_RECG_TEXT;

public class SearchActivity extends AppCompatActivity implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {

    private final static int MAX_NUMBER = 5;
    private final static String TAG = "SearchActivity";

    private Handler mHanlder;
    private SinVoicePlayer mSinVoicePlayer;
    private SinVoiceRecognition mRecognition;
    private ImageView imageViewScan, imageViewPerson;
    private LinearLayout linearPerson, linearNonPerson, linearFamily, linearNonFamily;
    private StringBuilder mTextBuilder = new StringBuilder();
    private boolean user1, user2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSinVoicePlayer = new SinVoicePlayer(DEFAULT_CODE_BOOK);
        mSinVoicePlayer.setListener(this);

        mRecognition = new SinVoiceRecognition(DEFAULT_CODE_BOOK);
        mRecognition.setListener(this);

        //final TextView playTextView = (TextView) findViewById(R.id.playtext);
        TextView recognisedTextView = (TextView) findViewById(R.id.regtext);
        imageViewScan = findViewById(R.id.imageViewScan);
        imageViewPerson = findViewById(R.id.imageViewFirstMem);
        linearPerson = findViewById(R.id.linearPerson);
        linearNonPerson = findViewById(R.id.linearNonPerson);

        linearFamily = findViewById(R.id.linearFamily);
        linearNonFamily = findViewById(R.id.linearNonFamily);


        mHanlder = new Util.RegHandler(recognisedTextView);

        Glide.with(this)
                .asGif()
                .load(R.drawable.scan)
                .into(imageViewScan);

        linearPerson.setOnClickListener(view -> {
            startActivity(new Intent(this, SendActivity.class));
        });

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
        String text = genText(7);
        // playTextView.setText(text);
        // mSinVoicePlayer.play(text, true, 1000);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                linearPerson.setVisibility(View.VISIBLE);
                linearFamily.setVisibility(View.VISIBLE);
            }
        }, 3000);

        final Handler handlerNon = new Handler();
        handlerNon.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                linearNonPerson.setVisibility(View.VISIBLE);
                linearNonFamily.setVisibility(View.VISIBLE);
            }
        }, 5000);

       /* try {

            ScheduledExecutorService executor =
                    Executors.newSingleThreadScheduledExecutor();

            Runnable periodicTask = new Runnable() {
                public void run() {
                    Log.e(TAG, "Interval call " + user1 + " User2 " + user2);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            setViewVisiblity(linearPerson, linearFamily, user1 ? View.VISIBLE : View.GONE);
                            setViewVisiblity(linearNonPerson, linearNonFamily, user2 ? View.VISIBLE : View.GONE);
                        }
                    });
                    user1 = false;
                    user2 = false;
                }
            };

            executor.scheduleAtFixedRate(periodicTask, 0, 2, TimeUnit.SECONDS);
        } catch (Exception ex) {
            Log.e(TAG, "Error " + ex);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRecognition!=null)
            mRecognition.stop();

        if(mSinVoicePlayer!=null)
            mSinVoicePlayer.stop();
    }

    private String genText(int count) {
        StringBuilder sb = new StringBuilder();
        int pre = 0;
        while (count > 0) {
            int x = (int) (Math.random() * MAX_NUMBER + 1);
            if (Math.abs(x - pre) > 0) {
                sb.append(x);
                --count;
                pre = x;
            }
        }

        return sb.toString();
    }


    @Override
    public void onRecognitionStart() {
        mTextBuilder.delete(0, mTextBuilder.length());//Clear Builder on start
        mHanlder.sendEmptyMessage(MSG_RECG_START);
    }

    @Override
    public void onRecognition(char ch) {
        mTextBuilder.append(ch);
        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
        if (mTextBuilder.toString().equalsIgnoreCase("111")) {
            mTextBuilder.delete(0, mTextBuilder.length());//Clear Builder on finding
            user1 = true;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    setViewVisiblity(linearPerson, linearFamily, View.VISIBLE);
                }
            });

        } else if (mTextBuilder.toString().equalsIgnoreCase("333")) {
            mTextBuilder.delete(0, mTextBuilder.length());//Clear Builder on finding
            user2 = true;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    setViewVisiblity(linearNonPerson, linearNonFamily, View.VISIBLE);
                }
            });
        }
    }

    private void setViewVisiblity(LinearLayout linearPerson, LinearLayout linearFamily, int visibility) {
        linearPerson.setVisibility(visibility);
        linearFamily.setVisibility(visibility);
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
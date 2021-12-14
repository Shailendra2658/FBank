package com.wibmothon.fbank.ui;

import static android.text.Html.fromHtml;
import static com.sound.waves.Common.DEFAULT_CODE_BOOK;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sound.waves.SinVoicePlayer;
import com.sound.waves.SinVoiceRecognition;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.util.TokenGeneration;

public class CashManagement extends AppCompatActivity  implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {


    private TextView txtSubtitle;
    private SinVoicePlayer mSinVoicePlayer;
    private LinearLayout linearLayout2,linearLayout3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_management);

        mSinVoicePlayer = new SinVoicePlayer(DEFAULT_CODE_BOOK);
        mSinVoicePlayer.setListener(this);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        txtSubtitle = findViewById(R.id.txt_subtitle);
        linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout3 = findViewById(R.id.linearLayout3);

        linearLayout2.setOnClickListener(view -> {
            mSinVoicePlayer.play("1",true, 1000);

        });

        linearLayout3.setOnClickListener(view -> {
            mSinVoicePlayer.play("2",true, 1000);

        });

        txtSubtitle.setText(fromHtml("<small>" + "Switch to 0% commission direct MF " +
                "and get higher returns <font color=#03A9F4> <u>View recommendations.</u>"
                + "</small>."));

        //mSinVoicePlayer.play(TokenGeneration.getToken("androidId")+"", true, 1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
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

    }

    @Override
    public void onRecognition(char ch) {

    }

    @Override
    public void onRecognitionEnd() {

    }
}
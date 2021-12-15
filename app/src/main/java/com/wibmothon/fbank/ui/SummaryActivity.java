package com.wibmothon.fbank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wibmothon.fbank.R;

public class SummaryActivity extends AppCompatActivity {

    private ImageView imgSuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        imgSuccess = findViewById(R.id.imageViewSuccess);

        Glide.with(this)
                .asGif()
                .load(R.drawable.sent_small)
                .into(imgSuccess);
        Intent intent= new Intent(this, Dashboard.class);
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
}
package com.wibmothon.fbank.ui;

import static android.text.Html.fromHtml;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.wibmothon.fbank.R;

public class CashManagement extends AppCompatActivity {

    TextView txtSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_management);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        txtSubtitle = findViewById(R.id.txt_subtitle);

        txtSubtitle.setText(fromHtml("<small>" + "Switch to 0% commission direct MF " +
                "and get higher returns <font color=#03A9F4> <u>View recommendations.</u>"
                + "</small>."));
    }
}
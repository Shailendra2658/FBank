package com.wibmothon.fbank.ui;

import static android.text.Html.fromHtml;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.DashboardRecyclerViewAdapter;
import com.wibmothon.fbank.adapter.InvestmentRecyclerViewAdapter;
import com.wibmothon.fbank.model.DashboardModel;
import com.wibmothon.fbank.model.InvestmentModel;

import java.util.ArrayList;
import java.util.List;

public class Investment extends AppCompatActivity {

    RecyclerView investmentRecyclerView;
    String[] titleStr = {"Mutual Funds", "Debt",
            "In Stocks", "Gold",
            "Real Estate", "PPF",
            "FD", "NPS",
            "Govt.Bonds", "PPF",
            "Crypto"};
    int[] imageId = {R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24,
            R.drawable.ic_baseline_money_24};

    List<InvestmentModel> investmentModel;

    FloatingActionButton fab;
    TextView txtSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        investmentModel = new ArrayList<>();

        for (int i = 0; i < titleStr.length; i++) {
            InvestmentModel investmentModels = new InvestmentModel();
            investmentModels.setInvestmentTitle(titleStr[i]);
            investmentModels.setImageSrc(imageId[i]);
            investmentModel.add(investmentModels);
        }

        investmentRecyclerView = findViewById(R.id.investmentRecyclerView);
        txtSubtitle = findViewById(R.id.txt_subtitle);
        fab = findViewById(R.id.add_fab);

        InvestmentRecyclerViewAdapter adapter = new InvestmentRecyclerViewAdapter(investmentModel, this);
        investmentRecyclerView.setHasFixedSize(false);
        investmentRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        investmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        investmentRecyclerView.setAdapter(adapter);

        txtSubtitle.setText(fromHtml("<small>" + "Switch to 0% commission direct MF " +
                "and get higher returns <font color=#03A9F4> <u>View recommendations.</u>"
                + "</small>."));

        investmentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }
}
package com.wibmothon.fbank.ui;

import static android.text.Html.fromHtml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wibmothon.fbank.MainActivity;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.DashboardRecyclerViewAdapter;
import com.wibmothon.fbank.model.DashboardModel;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] titleStr = {"Investments", "Insurance", "Cash Management", "Liability"};
    String[] subTitleStr = {"View your total investments & grow your money",
            "Build a safety net for you & your loved ones",
            "Manage your bills and other expenses",
            "Manage your loan & EMIs"};

    List<DashboardModel> dashboardModelList;

    TextView txtSubtitle;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        dashboardModelList = new ArrayList<>();

        for (int i = 0; i < titleStr.length; i++) {
            DashboardModel dashboardModel = new DashboardModel();
            dashboardModel.setTitleText(titleStr[i]);
            dashboardModel.setSubTitle(subTitleStr[i]);
            dashboardModelList.add(dashboardModel);
        }

        recyclerView = findViewById(R.id.recyclerView);
        txtSubtitle = findViewById(R.id.txt_subtitle);
        fab = findViewById(R.id.add_fab);

        DashboardRecyclerViewAdapter adapter = new DashboardRecyclerViewAdapter(dashboardModelList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        txtSubtitle.setText(fromHtml("<small>" + "Switch to 0% commission direct MF " +
                "and get higher returns <font color=#03A9F4> <u>View recommendations.</u>"
                + "</small>."));

        fab.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, MainActivity.class)));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        recyclerView.addOnItemTouchListener(new DashboardRecyclerViewAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new DashboardRecyclerViewAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 0) {
                    startActivity(new Intent(Dashboard.this, Investment.class));
                } else if (position == 2) {
                    startActivity(new Intent(Dashboard.this, CashManagement.class));
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
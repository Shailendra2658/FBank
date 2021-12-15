package com.wibmothon.fbank.ui;

import static android.text.Html.fromHtml;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wibmothon.fbank.R;
import com.wibmothon.fbank.ui.fragment.AccountsFragments;
import com.wibmothon.fbank.ui.fragment.AdvisoryFragment;
import com.wibmothon.fbank.ui.fragment.GoalsFragment;
import com.wibmothon.fbank.ui.fragment.HomeFragment;
import com.wibmothon.fbank.util.Util;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static BottomNavigationView bottomNavigationView;

    private TextView txtSubtitle;
    public static ImageView imgBack;
    public static ImageView headerImage;
    public static ImageView headerCircleImage;
    private ImageView downArrowImgView;
    private FragmentManager fm = getSupportFragmentManager();

    private final Fragment fragment1 = new HomeFragment();
    private Fragment active = fragment1;

    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        headerImage = findViewById(R.id.headerImage);
        headerCircleImage = findViewById(R.id.headerCircleImage);
        downArrowImgView = findViewById(R.id.downArrowImgView);
        txtSubtitle = findViewById(R.id.txt_subtitle);
        fab = findViewById(R.id.fab);
        imgBack = findViewById(R.id.img_back);
        imgBack.setVisibility(View.INVISIBLE);
        headerCircleImage.setVisibility(View.INVISIBLE);
        headerImage.setVisibility(View.VISIBLE);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.bottom_dashboard);

        txtSubtitle.setText(fromHtml("<small>" + "Switch to 0% commission direct MF " +
                "and get higher returns <font color=#03A9F4> <u>View recommendations.</u>"
                + "</small>."));

//        fab.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, MainActivity.class)));

        /*HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();*/

        downArrowImgView.setOnClickListener(v -> showDialog(Dashboard.this));

        Util.getDataFromFirebase();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_dashboard:
                Fragment fragment1 = new HomeFragment();
                setFragment(fragment1, "1", 0);
                imgBack.setVisibility(View.INVISIBLE);
                headerCircleImage.setVisibility(View.INVISIBLE);
                headerImage.setVisibility(View.VISIBLE);
                return true;
            case R.id.bottom_goals:
                Fragment fragment2 = new GoalsFragment();
                setFragment(fragment2, "2", 1);
                return true;
            case R.id.bottom_advisory:
                Fragment fragment3 = new AdvisoryFragment();
                setFragment(fragment3, "3", 2);
                return true;
            case R.id.bottom_accounts:
                Fragment fragment4 = new AccountsFragments();
                setFragment(fragment4, "4", 3);
                return true;
        }
        return false;
    }

    public void setFragment(Fragment fragment, String tag, int position) {
        if (fragment.isAdded()) {
            fm.beginTransaction().remove(active).show(fragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.flFragment, fragment, tag).commit();
        }
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        active = fragment;
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

        downArrow1ImgView.setOnClickListener(v -> dialog.dismiss());
        downArrow2ImgView.setOnClickListener(v -> dialog.dismiss());
        downArrow3ImgView.setOnClickListener(v -> dialog.dismiss());

        dialog.show();


    }

    @Override
    public void onBackPressed() {
        if (active == fragment1) {
            super.onBackPressed();
        } else {
            imgBack.setVisibility(View.INVISIBLE);
            headerCircleImage.setVisibility(View.INVISIBLE);
            headerImage.setVisibility(View.VISIBLE);
            setFragment(fragment1, "1", 0);
        }
    }
}
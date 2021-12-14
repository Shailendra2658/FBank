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
import com.wibmothon.fbank.ui.fragment.GoalsFragment;
import com.wibmothon.fbank.ui.fragment.HomeFragment;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static BottomNavigationView bottomNavigationView;

    private TextView txtSubtitle;
    public static ImageView imgBack;
    private ImageView downArrowImgView;
    private FragmentManager fm = getSupportFragmentManager();

    private final Fragment fragment1 = new HomeFragment();
    private final Fragment fragment2 = new GoalsFragment();
    private Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        downArrowImgView = findViewById(R.id.downArrowImgView);
        txtSubtitle = findViewById(R.id.txt_subtitle);
        imgBack = findViewById(R.id.img_back);
        imgBack.setVisibility(View.INVISIBLE);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.bottom_dashboard);

        txtSubtitle.setText(fromHtml("<small>" + "Switch to 0% commission direct MF " +
                "and get higher returns <font color=#03A9F4> <u>View recommendations.</u>"
                + "</small>."));

//        fab.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, MainActivity.class)));

        /*HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();*/

        downArrowImgView.setOnClickListener(v -> showDialog(Dashboard.this));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_dashboard:
                setFragment(fragment1, "1", 0);
                imgBack.setVisibility(View.INVISIBLE);
                return true;
            case R.id.bottom_goals:
                setFragment(fragment2, "2", 1);
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
            setFragment(fragment1, "1", 0);
        }
    }
}
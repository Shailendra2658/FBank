package com.wibmothon.fbank.ui.fragment.goalstab;

import static com.wibmothon.fbank.ui.Dashboard.fab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.InProgressGoalAdapter;
import com.wibmothon.fbank.model.InProgressGoalsModel;

import java.util.ArrayList;
import java.util.List;

public class InProgressGoalsTab extends Fragment {

    public static InProgressGoalsTab newInstance() {
        return new InProgressGoalsTab();
    }

    String[] goalsYear = {"Goal • Due by 2030", "Goal • Due by 2050"};
    String[] goalsTitle = {"My Son's Marriage",
            "Home Loan"};
    String[] goalsSubTitle = {"Savings for expenses towards\nSon's marriage",
            "Save for Home EMIs, furnitures &\nBuilding plan"};
    String[] achievedPercentage = {"- 55%", "- 38%"};
    String[] pendingPercentage = {"- 45%", "- 62%"};

    int[] dashboardDrawable = new int[]{
            R.drawable.ic_goalsmain,
            R.drawable.ic_goalsmain,
    };

    List<InProgressGoalsModel> inProgressGoalsModels;
    RecyclerView recyclerViewInProgressGoals;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_in_progress_goals_tab, container, false);

        inProgressGoalsModels = new ArrayList<>();

        for (int i = 0; i < goalsYear.length; i++) {
            InProgressGoalsModel inProgressGoalsModel = new InProgressGoalsModel();
            inProgressGoalsModel.setGoalsYear(goalsYear[i]);
            inProgressGoalsModel.setGoalsTitle(goalsTitle[i]);
            inProgressGoalsModel.setGoalsSubTitle(goalsSubTitle[i]);
            inProgressGoalsModel.setAchievedPercentage(achievedPercentage[i]);
            inProgressGoalsModel.setPendingPercentage(pendingPercentage[i]);
            inProgressGoalsModel.setImageViewFromDrawable(dashboardDrawable[i]);
            inProgressGoalsModels.add(inProgressGoalsModel);
        }

        recyclerViewInProgressGoals = view.findViewById(R.id.recyclerViewInProgressGoals);

        InProgressGoalAdapter adapter = new InProgressGoalAdapter(inProgressGoalsModels, getActivity());
        recyclerViewInProgressGoals.setHasFixedSize(true);
        recyclerViewInProgressGoals.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewInProgressGoals.setAdapter(adapter);

        recyclerViewInProgressGoals.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }
}
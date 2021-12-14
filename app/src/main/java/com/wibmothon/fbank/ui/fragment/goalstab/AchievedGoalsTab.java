package com.wibmothon.fbank.ui.fragment.goalstab;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.adapter.AchievedGoalAdapter;
import com.wibmothon.fbank.adapter.InProgressGoalAdapter;
import com.wibmothon.fbank.model.InProgressGoalsModel;

import java.util.ArrayList;
import java.util.List;

public class AchievedGoalsTab extends Fragment {

    public static AchievedGoalsTab newInstance() {
        return new AchievedGoalsTab();
    }

    String[] goalsYear = {"Goal • Due by 2020", "Goal • Due by 2021"};
    String[] goalsTitle = {"Buy a Car",
            "Home Loan"};
    String[] goalsSubTitle = {"Save for the purchase of new car",
            "Save for the purchase of new\nTelevision for my office"};

    List<InProgressGoalsModel> inProgressGoalsModels;
    RecyclerView recyclerViewInProgressGoals;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_achieved_goals_tab, container, false);

        inProgressGoalsModels = new ArrayList<>();

        for (int i = 0; i < goalsYear.length; i++) {
            InProgressGoalsModel inProgressGoalsModel = new InProgressGoalsModel();
            inProgressGoalsModel.setGoalsYear(goalsYear[i]);
            inProgressGoalsModel.setGoalsTitle(goalsTitle[i]);
            inProgressGoalsModel.setGoalsSubTitle(goalsSubTitle[i]);
            inProgressGoalsModels.add(inProgressGoalsModel);
        }

        recyclerViewInProgressGoals = view.findViewById(R.id.recyclerViewInProgressGoals);

        AchievedGoalAdapter adapter = new AchievedGoalAdapter(inProgressGoalsModels, getActivity());
        recyclerViewInProgressGoals.setHasFixedSize(true);
        recyclerViewInProgressGoals.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewInProgressGoals.setAdapter(adapter);

        return view;
    }
}
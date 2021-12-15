package com.wibmothon.fbank.model;

public class AdvisoryModel {

    String advisoryHeader;
    String goalYrTv2;
    String advisoryTitle;
    String advisorySubTitle;
    int advisoryDrawable;

    public String getGoalYrTv2() {
        return goalYrTv2;
    }

    public void setGoalYrTv2(String goalYrTv2) {
        this.goalYrTv2 = goalYrTv2;
    }

    public String getAdvisoryHeader() {
        return advisoryHeader;
    }

    public void setAdvisoryHeader(String advisoryHeader) {
        this.advisoryHeader = advisoryHeader;
    }

    public String getAdvisoryTitle() {
        return advisoryTitle;
    }

    public void setAdvisoryTitle(String advisoryTitle) {
        this.advisoryTitle = advisoryTitle;
    }

    public String getAdvisorySubTitle() {
        return advisorySubTitle;
    }

    public void setAdvisorySubTitle(String advisorySubTitle) {
        this.advisorySubTitle = advisorySubTitle;
    }

    public int getAdvisoryDrawable() {
        return advisoryDrawable;
    }

    public void setAdvisoryDrawable(int advisoryDrawable) {
        this.advisoryDrawable = advisoryDrawable;
    }
}

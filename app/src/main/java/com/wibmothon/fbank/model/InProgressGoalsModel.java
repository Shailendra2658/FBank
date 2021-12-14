package com.wibmothon.fbank.model;

public class InProgressGoalsModel {

    String goalsYear;
    String goalsTitle;
    String goalsSubTitle;
    String achievedPercentage;
    String pendingPercentage;
    int imageViewFromDrawable;

    public int getImageViewFromDrawable() {
        return imageViewFromDrawable;
    }

    public void setImageViewFromDrawable(int imageViewFromDrawable) {
        this.imageViewFromDrawable = imageViewFromDrawable;
    }

    public String getGoalsYear() {
        return goalsYear;
    }

    public void setGoalsYear(String goalsYear) {
        this.goalsYear = goalsYear;
    }

    public String getGoalsTitle() {
        return goalsTitle;
    }

    public void setGoalsTitle(String goalsTitle) {
        this.goalsTitle = goalsTitle;
    }

    public String getGoalsSubTitle() {
        return goalsSubTitle;
    }

    public void setGoalsSubTitle(String goalsSubTitle) {
        this.goalsSubTitle = goalsSubTitle;
    }

    public String getAchievedPercentage() {
        return achievedPercentage;
    }

    public void setAchievedPercentage(String achievedPercentage) {
        this.achievedPercentage = achievedPercentage;
    }

    public String getPendingPercentage() {
        return pendingPercentage;
    }

    public void setPendingPercentage(String pendingPercentage) {
        this.pendingPercentage = pendingPercentage;
    }

    ;

}

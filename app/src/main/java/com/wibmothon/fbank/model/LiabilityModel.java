package com.wibmothon.fbank.model;

import android.graphics.drawable.Drawable;

public class LiabilityModel {

    String liabilityTitle;
    String liabilitySubTitle;
    String liabilitySubTitle2;
    String achievedTitle;
    String achievedPercentageTv;
    String pendingTv;
    String pendingPercentageTv;
    int imageSrc;

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getLiabilityTitle() {
        return liabilityTitle;
    }

    public void setLiabilityTitle(String liabilityTitle) {
        this.liabilityTitle = liabilityTitle;
    }

    public String getLiabilitySubTitle() {
        return liabilitySubTitle;
    }

    public void setLiabilitySubTitle(String liabilitySubTitle) {
        this.liabilitySubTitle = liabilitySubTitle;
    }

    public String getLiabilitySubTitle2() {
        return liabilitySubTitle2;
    }

    public void setLiabilitySubTitle2(String liabilitySubTitle2) {
        this.liabilitySubTitle2 = liabilitySubTitle2;
    }

    public String getAchievedTitle() {
        return achievedTitle;
    }

    public void setAchievedTitle(String achievedTitle) {
        this.achievedTitle = achievedTitle;
    }

    public String getAchievedPercentageTv() {
        return achievedPercentageTv;
    }

    public void setAchievedPercentageTv(String achievedPercentageTv) {
        this.achievedPercentageTv = achievedPercentageTv;
    }

    public String getPendingTv() {
        return pendingTv;
    }

    public void setPendingTv(String pendingTv) {
        this.pendingTv = pendingTv;
    }

    public String getPendingPercentageTv() {
        return pendingPercentageTv;
    }

    public void setPendingPercentageTv(String pendingPercentageTv) {
        this.pendingPercentageTv = pendingPercentageTv;
    }
}

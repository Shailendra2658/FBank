package com.wibmothon.fbank.model;

import android.graphics.drawable.Drawable;

public class LCashModel {

    String LCTitle;
    String LCSubTitle1;
    String LCSubTitle2;
    String payTitle;
    Drawable imageSrc;


    public String getLCTitle() {
        return LCTitle;
    }

    public void setLCTitle(String LCTitle) {
        this.LCTitle = LCTitle;
    }

    public String getLCSubTitle1() {
        return LCSubTitle1;
    }

    public void setLCSubTitle1(String LCSubTitle1) {
        this.LCSubTitle1 = LCSubTitle1;
    }

    public String getLCSubTitle2() {
        return LCSubTitle2;
    }

    public void setLCSubTitle2(String LCSubTitle2) {
        this.LCSubTitle2 = LCSubTitle2;
    }

    public String getPayTitle() {
        return payTitle;
    }

    public void setPayTitle(String payTitle) {
        this.payTitle = payTitle;
    }

    public Drawable getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(Drawable imageSrc) {
        this.imageSrc = imageSrc;
    }
}

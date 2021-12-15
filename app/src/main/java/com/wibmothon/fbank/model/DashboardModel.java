package com.wibmothon.fbank.model;

import java.io.Serializable;

public class DashboardModel implements Serializable {

    private String titleText;
    private String subTitle;
    private String titleSubText1;
    private String titleSubText2;
    private int imageSrc;

    public String getTitleSubText1() {
        return titleSubText1;
    }

    public void setTitleSubText1(String titleSubText1) {
        this.titleSubText1 = titleSubText1;
    }

    public String getTitleSubText2() {
        return titleSubText2;
    }

    public void setTitleSubText2(String titleSubText2) {
        this.titleSubText2 = titleSubText2;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }


}

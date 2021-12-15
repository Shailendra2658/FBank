package com.wibmothon.fbank.model;

public class InvestmentModel {

    private String InvestmentTitle;
    private String investmentSubTv;
    private int imageSrc;

    public String getInvestmentSubTv() {
        return investmentSubTv;
    }

    public void setInvestmentSubTv(String investmentSubTv) {
        this.investmentSubTv = investmentSubTv;
    }

    public String getInvestmentTitle() {
        return InvestmentTitle;
    }

    public void setInvestmentTitle(String investmentTitle) {
        InvestmentTitle = investmentTitle;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }


}

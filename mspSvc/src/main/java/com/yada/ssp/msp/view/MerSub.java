package com.yada.ssp.msp.view;

public class MerSub {

    private String merNo;
    private String merName;

    public MerSub(String merNo, String merName) {
        this.merNo = merNo;
        this.merName = merName;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }
}

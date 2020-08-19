package com.yada.ssp.msp.model;

import java.io.Serializable;

public class SettlePK implements Serializable {

    private String merNo;
    private String settleDate;

    public SettlePK() {
    }

    public SettlePK(String merNo, String settleDate) {
        this.merNo = merNo;
        this.settleDate = settleDate;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }
}

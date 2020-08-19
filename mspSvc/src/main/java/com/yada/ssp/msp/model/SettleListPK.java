package com.yada.ssp.msp.model;

import java.io.Serializable;

public class SettleListPK implements Serializable {

    private String merNo;
    private String settleDate;
    private String channel;

    public SettleListPK() {
    }

    public SettleListPK(String merNo, String settleDate, String channel) {
        this.merNo = merNo;
        this.settleDate = settleDate;
        this.channel = channel;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

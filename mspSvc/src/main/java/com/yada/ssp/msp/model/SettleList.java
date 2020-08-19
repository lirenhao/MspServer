package com.yada.ssp.msp.model;

import javax.persistence.*;

@Entity
@Table(name = "V_WEB_MER_SETTLE_LIST")
@IdClass(SettleListPK.class)
public class SettleList {

    @Id
    @Column(name = "SETTLE_DATE")
    private String settleDate;

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    @Id
    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "TRAN_AMT")
    private String tranAmt; // 总交易金额

    @Column(name = "FEE")
    private String fee; // 手续费

    @Column(name = "SETTLE_AMT")
    private String settleAmt; // 清算金额

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }
}

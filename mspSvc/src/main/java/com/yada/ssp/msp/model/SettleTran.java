package com.yada.ssp.msp.model;

import com.yada.ssp.common.util.StringUtil;

import javax.persistence.*;

@Entity
@Table(name = "V_WEB_MER_SETTLE_TRAN_LIST")
@IdClass(SettleTranPK.class)
public class SettleTran {

    @Column(name = "SETTLE_DATE")
    private String settleDate;

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    @Id
    @Column(name = "TERMINAL_ID")
    private String termNo;

    @Id
    @Column(name = "CARD_NO")
    private String cardNo;

    @Column(name = "TRAN_NAME")
    private String tranName;

    @Id
    @Column(name = "TRAN_AMT")
    private String tranAmt; // 总交易金额

    @Column(name = "FEE")
    private String fee; // 手续费

    @Column(name = "SETTLE_AMT")
    private String settleAmt; // 清算金额

    @Id
    @Column(name = "TRAN_DATE")
    private String tranDate;

    @Id
    @Column(name = "TRAN_TIME")
    private String tranTime;

    @Column(name = "BATCH_NO")
    private String batchNo;

    @Column(name = "TRACE_NO")
    private String traceNo;

    @Column(name = "AUTH_NO")
    private String authNo;

    @Column(name = "RRN")
    private String rrn;

    @Column(name = "CHANNEL")
    private String channel;

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

    public String getTermNo() {
        return termNo;
    }

    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    public String getCardNo() {
        return StringUtil.formatCardNo(cardNo);
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
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

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

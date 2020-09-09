package com.yada.ssp.msp.model;

import com.yada.ssp.common.util.StringUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_WEB_LOCAL_TRANS_LIST")
public class Trans {

    @Id
    @Column(name = "LS_ID")
    private String lsId;

    @Column(name = "MER_NO")
    private String merNo;

    @Column(name = "TERM_NO")
    private String termNo;

    @Column(name = "CARD_NO")
    private String cardNo; // 卡号隐藏中间

    @Column(name = "TRAN_AMT")
    private String tranAmt;

    @Column(name = "TRAN_TYPE")
    private String tranType;

    @Column(name = "RESP_CODE")
    private String respCode; // 交易状态

    @Column(name = "TRAN_STATUS")
    private String tranStatus; // 交易状态

    @Column(name = "TRAN_DATE")
    private String tranDate;

    @Column(name = "TRAN_TIME")
    private String tranTime;

    @Column(name = "BATCH_NO")
    private String batchNo;

    @Column(name = "TRACE_NO")
    private String traceNo;// 跟踪号

    @Column(name = "AUTH_NO")
    private String authNo; // 授权号

    @Column(name = "RRN")
    private String rrn; // 参考号

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
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
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getTranStatus() {
        return tranStatus;
    }

    public void setTranStatus(String tranStatus) {
        this.tranStatus = tranStatus;
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
}

package com.yada.ssp.msp.model;

import javax.persistence.*;

@Entity
@Table(name = "V_R_MERCHANT_TRANCOUNT_CUR")
@IdClass(SalesPK.class)
public class SalesCur {

    @Id
    @Column(name = "ORG_ID")
    private String orgId;

    @Id
    @Column(name = "YEARMON")
    private String yearMon;

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    @Column(name = "MER_NAME_ENG")
    private String merName;

    @Column(name = "TRAN_AMT")
    private String tranAmt;

    @Column(name = "FEE")
    private String fee;

    @Column(name = "TRAN_COUNT")
    private Integer tranCount;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getYearMon() {
        return yearMon;
    }

    public void setYearMon(String yearMon) {
        this.yearMon = yearMon;
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

    public Integer getTranCount() {
        return tranCount;
    }

    public void setTranCount(Integer tranCount) {
        this.tranCount = tranCount;
    }
}

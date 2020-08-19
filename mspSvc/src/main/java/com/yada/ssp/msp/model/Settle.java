package com.yada.ssp.msp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "V_WEB_MER_SETTLE")
@IdClass(SettlePK.class)
public class Settle {

    @Id
    @Column(name = "SETTLE_DATE")
    private String settleDate;

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    @Column(name = "TRAN_AMT")
    private String tranAmt; // 总交易金额

    @Column(name = "FEE")
    private String fee; // 手续费

    @Column(name = "SETTLE_AMT")
    private String settleAmt; // 清算金额

    @OneToMany
    @JoinColumns({
            @JoinColumn(name="SETTLE_DATE", referencedColumnName="SETTLE_DATE"),
            @JoinColumn(name="MERCHANT_ID", referencedColumnName="MERCHANT_ID")
    })
    @OrderBy("channel ASC")
    private Set<SettleList> subs;

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

    public Set<SettleList> getSubs() {
        return subs;
    }

    public void setSubs(Set<SettleList> subs) {
        this.subs = subs;
    }
}

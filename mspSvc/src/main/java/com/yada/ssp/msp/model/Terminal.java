package com.yada.ssp.msp.model;

import javax.persistence.*;

@Entity
@Table(name = "V_R_TERMINAL_COUNT_CUR")
@IdClass(TerminalPK.class)
public class Terminal {

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    @Id
    @Column(name = "TERMINAL_ID")
    private String termNo;

    @Column(name = "INSTALL_ADDRESS")
    private String termAddress; // 装机地址

    @Column(name = "SN_NO")
    private String serialNumber;

    //终端状态 0-正常1-未签到2-注销3-注销待回執4-待发工单5-审核通过待回执6-待生成工单
    @Column(name = "TERM_STATUS")
    private String status;

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

    public String getTermAddress() {
        return termAddress;
    }

    public void setTermAddress(String termAddress) {
        this.termAddress = termAddress;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.yada.ssp.msp.model;

import java.io.Serializable;

public class TerminalPK implements Serializable {

    private String merNo;
    private String termNo;

    public TerminalPK() {
    }

    public TerminalPK(String merNo, String termNo) {
        this.merNo = merNo;
        this.termNo = termNo;
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
}

package com.yada.ssp.msp.model;

import java.io.Serializable;

public class SalesPK implements Serializable {

    private String orgId;
    private String yearMon;
    private String merNo;

    public SalesPK() {
    }

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
}

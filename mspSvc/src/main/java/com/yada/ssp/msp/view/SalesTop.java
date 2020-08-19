package com.yada.ssp.msp.view;

import java.math.BigDecimal;

public class SalesTop {

    private String merNo;
    private String merName;
    private BigDecimal sales;

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

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }
}

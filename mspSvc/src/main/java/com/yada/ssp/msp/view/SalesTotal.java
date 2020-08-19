package com.yada.ssp.msp.view;

import java.math.BigDecimal;

public class SalesTotal {

    private String year;
    private int count;
    private BigDecimal trans;
    private BigDecimal settle;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTrans() {
        return trans;
    }

    public void setTrans(BigDecimal trans) {
        this.trans = trans;
    }

    public BigDecimal getSettle() {
        return settle;
    }

    public void setSettle(BigDecimal settle) {
        this.settle = settle;
    }
}

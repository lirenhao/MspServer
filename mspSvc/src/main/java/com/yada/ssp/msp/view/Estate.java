package com.yada.ssp.msp.view;

import com.yada.ssp.msp.model.SettleList;

import java.util.List;

public class Estate {

    private String merchantId;
    private List<String> settleDate;
    private String merchantName;
    private String emailAddress;
    private String postalCode;
    private String contactPerson;
    private List<SettleList> settles;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public List<String> getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(List<String> settleDate) {
        this.settleDate = settleDate;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<SettleList> getSettles() {
        return settles;
    }

    public void setSettles(List<SettleList> settles) {
        this.settles = settles;
    }
}

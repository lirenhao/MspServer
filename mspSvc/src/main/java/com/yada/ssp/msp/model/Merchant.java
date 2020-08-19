package com.yada.ssp.msp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_B_MERCHANT_BASE_GAS")
public class Merchant {

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    @Column(name = "MER_NAME_ENG")
    private String merName;

    @Column(name = "MER_NAME_ENG_ABBR")
    private String merNameAbbr;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "BUSINESS_ADDRESS_ENG")
    private String merAddress;

    @Column(name = "CONTACT_PERSON_NAME")
    private String contactName;

    @Column(name = "TELEPHONE")
    private String contactPhone;

    @Column(name = "TAX")
    private String contactTax;

    @Column(name = "EMAIL")
    private String contactEmail;

    @Column(name = "POSTCODE")
    private String postcode;

    @OneToMany
    @JoinColumn(name = "MERCHANT_ID", referencedColumnName = "MERCHANT_ID")
    private Set<Terminal> terms;

    @OneToMany
    @OrderBy("merNo ASC")
    @JoinTable(name = "T_WEB_MER_SUBS", joinColumns = @JoinColumn(name = "MER_NO"), inverseJoinColumns = @JoinColumn(name = "MER_SUB_NO"))
    private Set<Merchant> subs = new HashSet<>();;

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

    public String getMerNameAbbr() {
        return merNameAbbr;
    }

    public void setMerNameAbbr(String merNameAbbr) {
        this.merNameAbbr = merNameAbbr;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getMerAddress() {
        return merAddress;
    }

    public void setMerAddress(String merAddress) {
        this.merAddress = merAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactTax() {
        return contactTax;
    }

    public void setContactTax(String contactTax) {
        this.contactTax = contactTax;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Set<Terminal> getTerms() {
        return terms;
    }

    public void setTerms(Set<Terminal> terms) {
        this.terms = terms;
    }

    public Set<Merchant> getSubs() {
        return subs;
    }

    public void setSubs(Set<Merchant> subs) {
        this.subs = subs;
    }
}

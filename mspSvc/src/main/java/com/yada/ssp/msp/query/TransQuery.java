package com.yada.ssp.msp.query;

import com.yada.ssp.msp.model.Trans;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

public class TransQuery implements Specification<Trans> {

    private String merNo; // 商户号
    private String termNo; // 终端号
    private String tranType; // 交易类型
    private String tranDate; // 交易时间
    private String tranStatus;

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

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranStatus() {
        return tranStatus;
    }

    public void setTranStatus(String tranStatus) {
        this.tranStatus = tranStatus;
    }

    @Override
    public Predicate toPredicate(Root<Trans> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> list = new LinkedList<>();

        if (null != merNo && !"".equals(merNo)) {
            list.add(cb.equal(root.get("merNo").as(String.class), merNo));
        }
        if (null != termNo && !"".equals(termNo)) {
            list.add(cb.equal(root.get("termNo").as(String.class), termNo));
        }
        if (null != tranType && !"".equals(tranType)) {
            list.add(cb.equal(root.get("tranType").as(String.class), tranType));
        }
        if (null != tranDate && !"".equals(tranDate)) {
            list.add(cb.equal(root.get("tranDate").as(String.class), tranDate));
        }
        if (null != tranStatus && !"".equals(tranStatus)) {
            list.add(cb.equal(root.get("tranStatus").as(String.class), tranStatus));
        }
        if (list.size() > 0) {
            query.where(list.toArray(new Predicate[0]));
        }

        return query.getRestriction();
    }
}


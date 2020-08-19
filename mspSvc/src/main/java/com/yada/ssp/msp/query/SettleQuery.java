package com.yada.ssp.msp.query;

import com.yada.ssp.msp.model.Settle;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class SettleQuery implements Specification<Settle> {

    private String settleDate; // 清算日期
    private String merNo; // 商户号

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

    @Override
    public Predicate toPredicate(Root<Settle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> list = new LinkedList<>();

        if (null != merNo && !"".equals(merNo)) {
            list.add(cb.equal(root.get("merNo").as(String.class), merNo));
        }
        if (null != settleDate && !"".equals(settleDate)) {
            list.add(cb.equal(root.get("settleDate").as(String.class), settleDate));
        }
        if (list.size() > 0) {
            query.where(list.toArray(new Predicate[0]));
        }

        return query.getRestriction();
    }
}


package com.yada.ssp.msp.service;

import com.yada.ssp.msp.dao.SettleDao;
import com.yada.ssp.msp.dao.SettleTranDao;
import com.yada.ssp.msp.model.Settle;
import com.yada.ssp.msp.model.SettleList;
import com.yada.ssp.msp.model.SettleTran;
import com.yada.ssp.msp.query.SettleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettleService {

    private final SettleDao settleDao;
    private final SettleTranDao settleTranDao;

    @Autowired
    public SettleService(SettleDao settleDao, SettleTranDao settleTranDao) {
        this.settleDao = settleDao;
        this.settleTranDao = settleTranDao;
    }

    public Page<Settle> findAll(SettleQuery query, Pageable pageable) {
        return settleDao.findAll(query, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "settleDate")));
    }

    public List<Settle> findAll(SettleQuery query) {
        return settleDao.findAll(query, Sort.by(Sort.Direction.ASC, "settleDate"));
    }

    public List<SettleTran> findTrans(SettleList sub) {
        return settleTranDao.findByMerNoAndSettleDateAndChannel(sub.getMerNo(), sub.getSettleDate(), sub.getChannel(),
                Sort.by(Sort.Direction.ASC, "settleDate", "channel"));
    }
}

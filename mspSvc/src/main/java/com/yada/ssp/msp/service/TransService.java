package com.yada.ssp.msp.service;

import com.yada.ssp.msp.dao.TransDao;
import com.yada.ssp.msp.model.Trans;
import com.yada.ssp.msp.query.TransQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransService {

    private final TransDao transDao;

    @Autowired
    public TransService(TransDao transDao) {
        this.transDao = transDao;
    }

    public Page<Trans> findAll(TransQuery query, Pageable pageable) {
        return transDao.findAll(query, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "tranDate", "tranTime")));
    }

    public List<Trans> findAll(TransQuery query) {
        return transDao.findAll(query, Sort.by(Sort.Direction.ASC, "tranDate", "tranTime"));
    }
}

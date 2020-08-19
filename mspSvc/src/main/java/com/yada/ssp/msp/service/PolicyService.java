package com.yada.ssp.msp.service;

import com.yada.ssp.msp.dao.PolicyDao;
import com.yada.ssp.msp.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private final PolicyDao policyDao;

    @Autowired
    public PolicyService(PolicyDao policyDao) {
        this.policyDao = policyDao;
    }

    public Policy findById(String id) {
        return policyDao.findById(id).orElse(new Policy());
    }
}

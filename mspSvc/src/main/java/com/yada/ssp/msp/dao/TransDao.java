package com.yada.ssp.msp.dao;

import com.yada.ssp.msp.model.Trans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransDao extends JpaRepository<Trans, String>, JpaSpecificationExecutor<Trans> {
    
}
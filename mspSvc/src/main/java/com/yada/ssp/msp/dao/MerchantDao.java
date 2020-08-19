package com.yada.ssp.msp.dao;

import com.yada.ssp.msp.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MerchantDao extends JpaRepository<Merchant, String>, JpaSpecificationExecutor<Merchant> {
    
}
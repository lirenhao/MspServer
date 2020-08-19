package com.yada.ssp.msp.dao;

import com.yada.ssp.msp.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyDao extends JpaRepository<Policy, String> {

}
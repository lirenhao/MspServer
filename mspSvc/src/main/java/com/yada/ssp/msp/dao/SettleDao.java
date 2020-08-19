package com.yada.ssp.msp.dao;

import com.yada.ssp.msp.model.Settle;
import com.yada.ssp.msp.model.SettlePK;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettleDao extends JpaRepository<Settle, SettlePK>, JpaSpecificationExecutor<Settle> {

    List<Settle> findByMerNoAndSettleDateIsBetween(String merNo, String startDate, String endDate, Sort sort);
}
package com.yada.ssp.msp.dao;

import com.yada.ssp.msp.model.Settle;
import com.yada.ssp.msp.model.SettleTran;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettleTranDao extends JpaRepository<SettleTran, String>, JpaSpecificationExecutor<Settle> {

    List<SettleTran> findByMerNoAndSettleDateAndChannel(String merNo, String settleDate, String channel, Sort sort);
}
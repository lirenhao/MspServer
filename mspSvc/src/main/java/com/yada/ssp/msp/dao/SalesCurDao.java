package com.yada.ssp.msp.dao;

import com.yada.ssp.msp.model.SalesCur;
import com.yada.ssp.msp.model.SalesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesCurDao extends JpaRepository<SalesCur, SalesPK>, JpaSpecificationExecutor<SalesCur> {

    List<SalesCur> findByMerNoInAndYearMonLike(List<String> merNos, String year);
}
package com.yada.ssp.msp.dao;

import com.yada.ssp.msp.model.SalesHis;
import com.yada.ssp.msp.model.SalesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesHisDao extends JpaRepository<SalesHis, SalesPK>, JpaSpecificationExecutor<SalesHis> {

    List<SalesHis> findByMerNoInAndYearMonLike(List<String> merNos, String year);
}
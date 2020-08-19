package com.yada.ssp.msp.service;

import com.yada.ssp.msp.dao.MerchantDao;
import com.yada.ssp.msp.dao.SettleDao;
import com.yada.ssp.msp.model.Merchant;
import com.yada.ssp.msp.model.Settle;
import com.yada.ssp.msp.view.Estate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class EstateService {

    private final MerchantDao merchantDao;
    private final SettleDao settleDao;

    @Autowired
    public EstateService(MerchantDao merchantDao, SettleDao settleDao) {
        this.merchantDao = merchantDao;
        this.settleDao = settleDao;
    }

    public Estate getEstate(String merNo, String startDate, String endDate ) {
        Estate eState = new Estate();
        eState.setMerchantId(merNo);
        eState.setSettleDate(Arrays.asList(startDate, endDate));
        Merchant merchant = merchantDao.findById(merNo).orElse(new Merchant());
        eState.setMerchantName(merchant.getMerName());
        eState.setPostalCode(merchant.getPostcode());
        eState.setContactPerson(merchant.getContactName());
        eState.setEmailAddress(merchant.getContactEmail());
        eState.setSettles(
                settleDao.findByMerNoAndSettleDateIsBetween(merNo, startDate, endDate, Sort.by(Sort.Direction.ASC, "settleDate"))
                .stream().map(Settle::getSubs).map(ArrayList::new)
                .reduce((a, b) -> {a.addAll(b); return a;}).orElseGet(ArrayList::new)
        );
        return eState;
    }
}

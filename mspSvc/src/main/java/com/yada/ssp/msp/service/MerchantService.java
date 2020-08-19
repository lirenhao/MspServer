package com.yada.ssp.msp.service;

import com.yada.ssp.msp.dao.MerchantDao;
import com.yada.ssp.msp.model.Merchant;
import com.yada.ssp.msp.model.Terminal;
import com.yada.ssp.msp.view.MerSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantService {

    private final MerchantDao merchantDao;

    @Autowired
    public MerchantService(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    public Merchant findById(String merNo) {
        return merchantDao.findById(merNo).orElse(new Merchant());
    }

    /**
     * 获取下级商户
     *
     * @param merNo 集团商户号
     * @return 下级商户
     */
    public List<MerSub> getSubs(String merNo) {
        List<MerSub> subMer = new ArrayList<>();
        Merchant merchant = merchantDao.findById(merNo).orElse(new Merchant());
        if (merchant.getMerNo() != null) {
            subMer.add(new MerSub(merchant.getMerNo(), merchant.getMerName()));
        }
        if (merchant.getSubs().size() > 0) {
            for (Merchant mer : merchant.getSubs()) {
                subMer.add(new MerSub(mer.getMerNo(), mer.getMerName()));
            }
        }
        return subMer;
    }

    public boolean isSubMer(String merNo, String subMerNo) {
        if (merNo.equals(subMerNo)) {
            return true;
        } else {
            return getSubs(merNo).stream().map(MerSub::getMerNo)
                    .collect(Collectors.toList()).contains(subMerNo);
        }
    }

    /**
     * 获取商户下的终端号
     *
     * @param merNo 商户号
     * @return 终端号
     */
    public List<String> getTermNos(String merNo) {
        Merchant merchant = merchantDao.findById(merNo).orElse(new Merchant());
        return merchant.getTerms().stream()
                .map(Terminal::getTermNo)
                .collect(Collectors.toList());
    }
}

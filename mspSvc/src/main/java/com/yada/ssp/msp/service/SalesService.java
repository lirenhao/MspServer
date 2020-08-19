package com.yada.ssp.msp.service;

import com.yada.ssp.msp.dao.SalesCurDao;
import com.yada.ssp.msp.dao.SalesHisDao;
import com.yada.ssp.msp.model.SalesCur;
import com.yada.ssp.msp.model.SalesHis;
import com.yada.ssp.msp.view.MerSub;
import com.yada.ssp.msp.view.SalesMonth;
import com.yada.ssp.msp.view.SalesTop;
import com.yada.ssp.msp.view.SalesTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SalesService {

    private final SalesCurDao curDao;
    private final SalesHisDao hisDao;
    private final MerchantService merchantService;

    @Autowired
    public SalesService(SalesCurDao curDao, SalesHisDao hisDao, MerchantService merchantService) {
        this.curDao = curDao;
        this.hisDao = hisDao;
        this.merchantService = merchantService;
    }

    public SalesTotal getTotal(String merNo, String year) {
        List<String> merNos = merchantService.getSubs(merNo).stream().map(MerSub::getMerNo).collect(Collectors.toList());
        List<SalesCur> curList = curDao.findByMerNoInAndYearMonLike(merNos, year + "%");
        List<SalesHis> hisList = hisDao.findByMerNoInAndYearMonLike(merNos, year + "%");

        SalesTotal total = createTotal(curList, hisList);
        total.setYear(year);
        return total;
    }

    public List<SalesMonth> getMonths(String merNo, String year) {
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        List<String> merNos = merchantService.getSubs(merNo).stream().map(MerSub::getMerNo).collect(Collectors.toList());
        List<SalesCur> curList = curDao.findByMerNoInAndYearMonLike(merNos, year + "%");
        List<SalesHis> hisList = hisDao.findByMerNoInAndYearMonLike(merNos, year + "%");

        return Arrays.stream(months)
                .map(month -> {
                    SalesMonth sm = new SalesMonth();
                    sm.setMonth(Integer.parseInt(month));
                    SalesTotal total = createTotal(
                            curList.stream().filter(item -> (year + month).equals(item.getYearMon())).collect(Collectors.toList()),
                            hisList.stream().filter(item -> (year + month).equals(item.getYearMon())).collect(Collectors.toList())
                    );
                    sm.setSales(total.getTrans());
                    return sm;
                })
                .collect(Collectors.toList());
    }

    public List<SalesTop> getTops(String merNo, String year) {
        List<MerSub> merSubs = merchantService.getSubs(merNo);
        List<SalesCur> curList = curDao.findByMerNoInAndYearMonLike(
                merSubs.stream().map(MerSub::getMerNo).collect(Collectors.toList()), year + "%");
        List<SalesHis> hisList = hisDao.findByMerNoInAndYearMonLike(
                merSubs.stream().map(MerSub::getMerNo).collect(Collectors.toList()), year + "%");

        return merSubs.stream()
                .map(sub -> {
                    SalesTop top = new SalesTop();
                    top.setMerNo(sub.getMerNo());
                    top.setMerName(sub.getMerName());
                    SalesTotal total = createTotal(
                            curList.stream().filter(item -> sub.getMerNo().equals(item.getMerNo())).collect(Collectors.toList()),
                            hisList.stream().filter(item -> sub.getMerNo().equals(item.getMerNo())).collect(Collectors.toList())
                    );
                    top.setSales(total.getTrans());
                    return top;
                })
                .sorted(Comparator.comparing(SalesTop::getSales).reversed())
                .collect(Collectors.toList());
    }

    private SalesTotal createTotal(List<SalesCur> curList, List<SalesHis> hisList) {
        SalesTotal total = new SalesTotal();

        BigDecimal curTrans = curList.stream().map(item -> new BigDecimal(item.getTranAmt())).reduce(new BigDecimal(0), BigDecimal::add);
        BigDecimal hisTrans = hisList.stream().map(item -> new BigDecimal(item.getTranAmt())).reduce(new BigDecimal(0), BigDecimal::add);
        total.setTrans(curTrans.add(hisTrans));

        BigDecimal curFee = curList.stream().map(item -> new BigDecimal(item.getFee())).reduce(new BigDecimal(0), BigDecimal::add);
        BigDecimal hisFee = hisList.stream().map(item -> new BigDecimal(item.getFee())).reduce(new BigDecimal(0), BigDecimal::add);
        total.setSettle(total.getTrans().subtract(curFee).subtract(hisFee));

        int curCount = curList.stream().map(SalesCur::getTranCount).filter(Objects::nonNull).reduce(0, Integer::sum);
        int hisCount = hisList.stream().map(SalesHis::getTranCount).filter(Objects::nonNull).reduce(0, Integer::sum);
        total.setCount(Integer.sum(curCount, hisCount));
        return total;
    }
}

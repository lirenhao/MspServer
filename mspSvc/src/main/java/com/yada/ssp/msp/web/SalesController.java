package com.yada.ssp.msp.web;

import com.yada.ssp.common.util.DateUtil;
import com.yada.ssp.msp.auth.model.Auth;
import com.yada.ssp.msp.service.SalesService;
import com.yada.ssp.msp.view.SalesMonth;
import com.yada.ssp.msp.view.SalesTop;
import com.yada.ssp.msp.view.SalesTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/total")
    public SalesTotal total(@RequestAttribute("auth") Auth auth) {
        return salesService.getTotal(auth.getMerId(), DateUtil.getCurDateFormat("YYYY"));
    }

    @GetMapping("/months")
    public List<SalesMonth> months(@RequestAttribute("auth") Auth auth) {
        return salesService.getMonths(auth.getMerId(), DateUtil.getCurDateFormat("YYYY"));
    }

    @GetMapping("/tops")
    public List<SalesTop> tops(@RequestAttribute("auth") Auth auth) {
        return salesService.getTops(auth.getMerId(), DateUtil.getCurDateFormat("YYYY"));
    }
}

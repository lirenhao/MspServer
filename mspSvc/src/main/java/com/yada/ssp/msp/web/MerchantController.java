package com.yada.ssp.msp.web;

import com.yada.ssp.msp.auth.model.Auth;
import com.yada.ssp.msp.model.Merchant;
import com.yada.ssp.msp.service.MerchantService;
import com.yada.ssp.msp.view.MerSub;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mer")
public class MerchantController {

    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public Merchant index(@RequestAttribute("auth") Auth auth) {
        return merchantService.findById(auth.getMerId());
    }

    @GetMapping("/subs")
    public List<MerSub> subs(@RequestAttribute("auth") Auth auth) {
        return merchantService.getSubs(auth.getMerId());
    }

    @GetMapping("/termNos")
    public List<String> termNos(@RequestAttribute("auth") Auth auth, String merNo) {
        return merchantService.isSubMer(auth.getMerId(), merNo)
                ? merchantService.getTermNos(merNo) : Lists.newArrayList();
    }
}

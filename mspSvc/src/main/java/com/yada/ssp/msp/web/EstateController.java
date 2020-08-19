package com.yada.ssp.msp.web;

import com.yada.ssp.msp.auth.model.Auth;
import com.yada.ssp.msp.service.EstateService;
import com.yada.ssp.msp.service.MerchantService;
import com.yada.ssp.msp.view.Estate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eState")
public class EstateController {

    private final EstateService eStateService;
    private final MerchantService merchantService;

    @Autowired
    public EstateController(EstateService eStateService, MerchantService merchantService) {
        this.eStateService = eStateService;
        this.merchantService = merchantService;
    }

    @GetMapping
    public Estate list(@RequestAttribute("auth") Auth auth, String merNo, String[] settleDate) {
        return merchantService.isSubMer(auth.getMerId(), merNo)
                ? eStateService.getEstate(merNo, settleDate[0], settleDate[1]) : null;
    }
}

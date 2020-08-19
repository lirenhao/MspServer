package com.yada.ssp.msp.web;

import com.yada.ssp.common.util.DateUtil;
import com.yada.ssp.msp.auth.model.Auth;
import com.yada.ssp.msp.model.Trans;
import com.yada.ssp.msp.query.TransQuery;
import com.yada.ssp.msp.service.MerchantService;
import com.yada.ssp.msp.service.TransService;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/trans")
public class TransController {

    private final TransService transService;
    private final MerchantService merchantService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public TransController(TransService transService, MerchantService merchantService, ResourceLoader resourceLoader) {
        this.transService = transService;
        this.merchantService = merchantService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public Page<Trans> list(@RequestAttribute("auth") Auth auth,
                            @ModelAttribute TransQuery query, @PageableDefault Pageable pageable) {
        return merchantService.isSubMer(auth.getMerId(), query.getMerNo())
                ? transService.findAll(query, pageable) : null;
    }

    @GetMapping("/download")
    public void download(@RequestAttribute("auth") Auth auth,
                         @ModelAttribute TransQuery query, HttpServletResponse resp) throws IOException {
        if (merchantService.isSubMer(auth.getMerId(), query.getMerNo())) {
            List<Trans> page = transService.findAll(query);
            Context context = new Context();
            context.putVar("page", page);
            context.putVar("query", query);

            String fileName = "MER_TRANS_DETAIL" + DateUtil.getCurDate() + ".xls";
            resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
            resp.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
            resp.setHeader("X-Suggested-Filename", fileName);
            InputStream in = resourceLoader.getResource("classpath:templates/merTransDetail.xls").getInputStream();
            JxlsHelper.getInstance().processTemplate(in, resp.getOutputStream(), context);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

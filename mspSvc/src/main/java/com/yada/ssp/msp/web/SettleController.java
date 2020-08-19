package com.yada.ssp.msp.web;

import com.yada.ssp.common.util.DateUtil;
import com.yada.ssp.msp.auth.model.Auth;
import com.yada.ssp.msp.jxls.GroupCollapsedCommand;
import com.yada.ssp.msp.model.Settle;
import com.yada.ssp.msp.model.SettleList;
import com.yada.ssp.msp.model.SettleTran;
import com.yada.ssp.msp.query.SettleQuery;
import com.yada.ssp.msp.service.MerchantService;
import com.yada.ssp.msp.service.SettleService;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
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
@RequestMapping("/settle")
public class SettleController {

    private final SettleService settleService;
    private final MerchantService merchantService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public SettleController(SettleService settleService, MerchantService merchantService, ResourceLoader resourceLoader) {
        this.settleService = settleService;
        this.merchantService = merchantService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public Page<Settle> list(@RequestAttribute("auth") Auth auth,
                             @ModelAttribute SettleQuery query, @PageableDefault Pageable pageable) {
        return merchantService.isSubMer(auth.getMerId(), query.getMerNo())
                ? settleService.findAll(query, pageable) : null;
    }

    @GetMapping("/download")
    public void download(@RequestAttribute("auth") Auth auth,
                         @ModelAttribute SettleQuery query, HttpServletResponse resp) {
        if (merchantService.isSubMer(auth.getMerId(), query.getMerNo())) {
            List<Settle> page = settleService.findAll(query);
            Context context = new Context();
            context.putVar("page", page);
            context.putVar("query", query);

            try {
                String fileName = "MER_SETTLE_DETAIL" + DateUtil.getCurDate() + ".xls";
                resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
                resp.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
                resp.setHeader("X-Suggested-Filename", fileName);
                InputStream in = resourceLoader.getResource("classpath:templates/merSettleDetail.xls").getInputStream();
                XlsCommentAreaBuilder.addCommandMapping("groupCollapsed", GroupCollapsedCommand.class);
                JxlsHelper.getInstance().processTemplate(in, resp.getOutputStream(), context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @GetMapping("/trans")
    public List<SettleTran> trans(@RequestAttribute("auth") Auth auth, @ModelAttribute SettleList sub) {
        return merchantService.isSubMer(auth.getMerId(), sub.getMerNo())
                ? settleService.findTrans(sub) : null;
    }
}

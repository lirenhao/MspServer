package com.yada.ssp.msp.auth.config;

import com.yada.ssp.msp.auth.model.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthFilter implements Filter {

    @Value("#{'${auth.exclude.paths:/res_list,/error}'.split(',')}")
    private List<String> exPaths;
    @Value("${auth.mock.open:false}")
    private boolean mockOpen;
    @Value("${auth.mock.orgId:}")
    private String mockOrgId;
    @Value("${auth.mock.merId:}")
    private String mockMerId;
    @Value("${auth.mock.userId:}")
    private String mockUserId;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI().substring(request.getContextPath().length());
        if(exPaths.stream().anyMatch(path -> new PathPatternParser().parse(path).matches(PathContainer.parsePath(uri)))) {
            chain.doFilter(request, response);
        } else {
            if(mockOpen) {
                Auth auth = new Auth();
                auth.setOrgId(mockOrgId);
                auth.setMerId(mockMerId);
                auth.setUserId(mockUserId);
                request.setAttribute("auth", auth);
                chain.doFilter(request, response);
            } else {
                String orgId = request.getHeader("X-YADA-ORG-ID");
                String userId = request.getHeader("X-YADA-USER-ID");

                if(orgId != null && userId != null) {
                    String[] info = userId.split("@");
                    Auth auth = new Auth();
                    auth.setOrgId(orgId);
                    auth.setMerId(info[0]);
                    auth.setUserId(info[1]);
                    request.setAttribute("auth", auth);
                    chain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
    }

    @Override
    public void destroy() {
    }
}

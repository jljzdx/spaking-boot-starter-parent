package com.spaking.boot.starter.cas.filter;


import com.spaking.boot.starter.cas.model.SsoUser;
import com.spaking.boot.starter.cas.utils.AntPathMatcher;
import com.spaking.boot.starter.cas.utils.BalanceUtil;
import com.spaking.boot.starter.cas.utils.SsoConstant;
import com.spaking.boot.starter.cas.utils.SsoWebLoginHelper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class SsoWebFilter extends HttpServlet implements Filter {

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String ssoServers;
    private String logoutPath;
    private String excludedPaths;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("sso web filter init start ...............");
        ssoServers = filterConfig.getInitParameter(SsoConstant.SSO_SERVERS);
        logoutPath = filterConfig.getInitParameter(SsoConstant.SSO_LOGOUT_PATH);
        excludedPaths = filterConfig.getInitParameter(SsoConstant.SSO_EXCLUDED_PATHS);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("sso web filter doFilter start ...............["+ssoServers+"]");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String [] ips = ssoServers.split(",");
        String ssoServer = BalanceUtil.getRoundRobinIp(ips);

        // make url
        String servletPath = req.getServletPath();

        if (excludedPaths!=null && excludedPaths.trim().length()>0) {
            for (String excludedPath:excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    chain.doFilter(request, response);
                    return;
                }

            }
        }

        if (logoutPath!=null && logoutPath.trim().length()>0 && logoutPath.equals(servletPath)) {
            // remove cookie
            SsoWebLoginHelper.removeSessionIdByCookie(req, res);
            // redirect logout
            String logoutPageUrl = ssoServer.concat(SsoConstant.SSO_LOGOUT).concat("?").concat(req.getQueryString());
            res.sendRedirect(logoutPageUrl);
            return;
        }

        // valid login user, cookie + redirect
        SsoUser user = SsoWebLoginHelper.loginCheck(req, res);

        // valid login fail
        if (user == null) {

            String header = req.getHeader("content-type");
            boolean isJson=  header!=null && header.contains("json");
            if (isJson) {

                // json msg
                res.setContentType("application/json;charset=utf-8");
                res.getWriter().println("{\"code\":"+SsoConstant.SSO_LOGIN_FAIL_RESULT.getCode()+", \"msg\":\""+ SsoConstant.SSO_LOGIN_FAIL_RESULT.getMsg() +"\"}");
                return;
            } else {

                // total link
                String link = req.getRequestURL().toString();

                // redirect logout
                String loginPageUrl = ssoServer.concat(SsoConstant.SSO_LOGIN)
                        + "?" + SsoConstant.REDIRECT_URL + "=" + link;

                res.sendRedirect(loginPageUrl);
                return;
            }

        }

        // ser sso user
        request.setAttribute(SsoConstant.SSO_USER, user);


        // already login, allow
        chain.doFilter(request, response);
        return;
    }

}

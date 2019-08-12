package com.spaking.boot.starter.cas.utils;


import com.spaking.boot.starter.cas.model.SsoUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuxueli 2018-04-03
 */
public class SsoWebLoginHelper {

    /**
     * client login
     *
     * @param response
     * @param sessionId
     * @param ifRemember    true: cookie not expire, false: expire when browser close （server cookie）
     * @param ssoUser
     */
    public static void login(HttpServletResponse response,
                             String sessionId,
                             SsoUser ssoUser,
                             boolean ifRemember) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }

        SsoLoginStore.put(storeKey, ssoUser);
        CookieUtil.set(response, SsoConstant.SSO_SESSIONID, sessionId, ifRemember);
    }

    /**
     * client logout
     *
     * @param request
     * @param response
     */
    public static void logout(HttpServletRequest request,
                              HttpServletResponse response) {

        String cookieSessionId = CookieUtil.getValue(request, SsoConstant.SSO_SESSIONID);
        if (cookieSessionId==null) {
            return;
        }

        String storeKey = SsoSessionIdHelper.parseStoreKey(cookieSessionId);
        if (storeKey != null) {
            SsoLoginStore.remove(storeKey);
        }

        CookieUtil.remove(request, response, SsoConstant.SSO_SESSIONID);
    }



    /**
     * login check
     *
     * @param request
     * @param response
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request, HttpServletResponse response){

        String cookieSessionId = CookieUtil.getValue(request, SsoConstant.SSO_SESSIONID);

        // cookie user
        SsoUser ssoUser = loginCheck(cookieSessionId);
        if (ssoUser != null) {
            return ssoUser;
        }

        // redirect user

        // remove old cookie
        SsoWebLoginHelper.removeSessionIdByCookie(request, response);

        // set new cookie
        String paramSessionId = request.getParameter(SsoConstant.SSO_SESSIONID);
        ssoUser = loginCheck(paramSessionId);
        if (ssoUser != null) {
            CookieUtil.set(response, SsoConstant.SSO_SESSIONID, paramSessionId, false);    // expire when browser close （client cookie）
            return ssoUser;
        }

        return null;
    }

    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public static SsoUser loginCheck(String  sessionId){

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return null;
        }

        SsoUser ssoUser = SsoLoginStore.get(storeKey);
        if (ssoUser != null) {
            String version = SsoSessionIdHelper.parseVersion(sessionId);
            if (ssoUser.getVersion().equals(version)) {

                // After the expiration time has passed half, Auto refresh
                if ((System.currentTimeMillis() - ssoUser.getExpireFreshTime()) > ssoUser.getExpireMinite()/2) {
                    ssoUser.setExpireFreshTime(System.currentTimeMillis());
                    SsoLoginStore.put(storeKey, ssoUser);
                }

                return ssoUser;
            }
        }
        return null;
    }

    /**
     * login check
     *
     * @param request
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request){
        String headerSessionId = request.getHeader(SsoConstant.SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }

    /**
     * client logout, cookie only
     *
     * @param request
     * @param response
     */
    public static void removeSessionIdByCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, SsoConstant.SSO_SESSIONID);
    }

    /**
     * get sessionid by cookie
     *
     * @param request
     * @return
     */
    public static String getSessionIdByCookie(HttpServletRequest request) {
        String cookieSessionId = CookieUtil.getValue(request, SsoConstant.SSO_SESSIONID);
        return cookieSessionId;
    }


}

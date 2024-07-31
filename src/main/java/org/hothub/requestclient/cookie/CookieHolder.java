package org.hothub.requestclient.cookie;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CookieHolder implements CookieJar {

    public static final Logger logger = LoggerFactory.getLogger(CookieHolder.class);

    //本次请求的Cookie
    private List<Cookie> requestCookieList = new ArrayList<>();

    private List<Cookie> responseCookieList;


    public CookieHolder() {
    }


    public void setRequestCookies(List<Cookie> list) {
        if (list != null && !list.isEmpty()) {
            this.requestCookieList = list;
        }
    }


    public List<Cookie> getRequestCookies() {
        return this.requestCookieList;
    }

    public List<Cookie> getResponseCookies() {
        return responseCookieList;
    }



    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        return this.requestCookieList;
    }



    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        if (list != null && !list.isEmpty()) {
            this.responseCookieList = list;
        }
    }

}

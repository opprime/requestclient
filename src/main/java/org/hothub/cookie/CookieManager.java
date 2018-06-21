package org.hothub.cookie;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.hothub.manager.ContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class CookieManager implements CookieJar {

    public static final Logger logger = LoggerFactory.getLogger(CookieManager.class);

    private LinkedHashSet<Cookie> mCookieList;

    public CookieManager(List<Cookie> cookieList) {
        this.mCookieList = cookieList == null ? new LinkedHashSet<>() : new LinkedHashSet<>(cookieList);
    }



    @SuppressWarnings("unchecked")
    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        LinkedHashSet<Cookie> cookiesList = (LinkedHashSet<Cookie>) ContextManager.get(httpUrl.host());
        //注：这里不能返回null，否则会报NULLException的错误。
        //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
        if (cookiesList == null) {
            cookiesList = new LinkedHashSet<>();
        }

        if (!this.mCookieList.isEmpty()) {
            cookiesList.addAll(this.mCookieList);
        }

        return new ArrayList<>(cookiesList);
    }



    @SuppressWarnings("unchecked")
    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        //移除相同的url的Cookie
        String host = httpUrl.host();
        LinkedHashSet<Cookie> newList = new LinkedHashSet<>(list);

        LinkedHashSet<Cookie> cookiesList = (LinkedHashSet<Cookie>) ContextManager.get(host);
        if (cookiesList != null){
            newList.addAll(cookiesList);
        }

        //再重新添加
        ContextManager.set(host, newList);
    }

}

package org.hothub.cookie;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.hothub.manager.ContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CookieManager implements CookieJar {

    public static final Logger logger = LoggerFactory.getLogger(CookieManager.class);

    private List<Cookie> mCookieList;

    public CookieManager(List<Cookie> cookieList) {
        this.mCookieList = cookieList == null ? new ArrayList<>() : cookieList;
    }



    @SuppressWarnings("unchecked")
    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        List<Cookie> cookiesList = (List<Cookie>) ContextManager.get(httpUrl.host());
        //注：这里不能返回null，否则会报NULLException的错误。
        //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
        cookiesList = cookiesList == null ? new ArrayList<>() : cookiesList;

        if (!this.mCookieList.isEmpty()) {
            cookiesList.addAll(this.mCookieList);
        }

        return cookiesList;
    }



    @SuppressWarnings("unchecked")
    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        //移除相同的url的Cookie
        String host = httpUrl.host();

        List<Cookie> cookiesList = (List<Cookie>) ContextManager.get(host);
        if (cookiesList != null){
            ContextManager.remove(host);
        }

        //再重新添加
        ContextManager.set(host, list);
    }

}

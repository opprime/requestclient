package org.hothub.cookie;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieHandler implements CookieJar {

    public static final Logger logger = LoggerFactory.getLogger(CookieHandler.class);


    //Cookie缓存
    private final Map<String, List<Cookie>> cookiesMap = new HashMap<>();


    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        //移除相同的url的Cookie
        String host = httpUrl.host();

        System.out.println(httpUrl.toString());
        System.out.println(list);

        System.out.println(list.get(0).expiresAt());

        List<Cookie> cookiesList = cookiesMap.get(host);
        if (cookiesList != null){
            cookiesMap.remove(host);
        }

        //再重新添加
        cookiesMap.put(host, list);
    }



    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        List<Cookie> cookiesList = cookiesMap.get(httpUrl.host());
        //注：这里不能返回null，否则会报NULLException的错误。
        //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
        return cookiesList != null ? cookiesList : new ArrayList<>();
    }



}

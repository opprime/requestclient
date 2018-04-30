package org.hothub.core;

import okhttp3.Cookie;
import org.hothub.utils.RequestClientUtils;

import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractBuilderChain<T> extends AbstractAttribute {


    public T init() {
        this.url = null;
        this.params = null;
        this.headers = null;
        this.cookies = null;
        this.bodyString = null;
        this.bodyFile = null;

        return (T) this;
    }

    public T url(String url) {
        this.url = url;

        return (T) this;
    }

    public T header(String key, String value) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<>();
        }

        if (!RequestClientUtils.isEmpty(key)) {
            this.headers.put(key, value);
        }

        return (T) this;
    }

    public T cookie(String key, String value) {
        if (this.cookies == null) {
            this.cookies = new LinkedHashMap<>();
        }

        if (!RequestClientUtils.isEmpty(key)) {
            this.cookies.put(key, value);
        }

        return (T) this;
    }

    public T cookie(List<Cookie> cookieList) {
        if (this.cookies == null) {
            this.cookies = new LinkedHashMap<>();
        }

        if (cookieList != null && !cookieList.isEmpty()) {
            for (Cookie item : cookieList) {
                if (RequestClientUtils.isEmpty(item.name())) {
                    continue;
                }

                this.cookies.put(item.name(), item.value());
            }
        }

        return (T) this;
    }

    public T withCookie(boolean useCookie) {
        this.useCookie = useCookie;

        return (T) this;
    }

    public T readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;

        return (T) this;
    }

    public T writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;

        return (T) this;
    }

    public T connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;

        return (T) this;
    }


}

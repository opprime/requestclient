package org.hothub.requestclient.core;

import okhttp3.*;
import org.hothub.requestclient.base.RequestMethod;
import org.hothub.requestclient.base.OnRequestListener;
import org.hothub.requestclient.pojo.FileBody;
import org.hothub.requestclient.response.ResultBody;
import org.hothub.requestclient.utils.RequestClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class AbstractBuilder<T> extends AbstractAttribute {


    private static final Logger logger = LoggerFactory.getLogger(AbstractBuilder.class);


    public AbstractBuilder() {}



    /**
     * 同步，带返回值
     * */
    public ResultBody execute() {

        logger.debug("org.hothub:requestclient, core.AbstractBuilder.execute sync");

        RequestBuild requestBuild = new RequestBuild(this);

        Request request = requestBuild.buildRequest();
        OkHttpClient okHttpClient = requestBuild.getOkHttpClient();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return new ResultBody(request, response, requestBuild.getCookieHolder());
        } catch (IOException e) {
            logger.error("org.hothub:requestclient, builder.GetBuilder.execute sync error", e);
        }

        return new ResultBody(request, null, null);
    }



    /**
     * 异步，无返回值
     * */
    public void execute(OnRequestListener onRequestListener) {

        logger.debug("org.hothub:requestclient, core.AbstractBuilder.execute async");

        RequestBuild requestBuild = new RequestBuild(this);

        Request request = requestBuild.buildRequest();
        OkHttpClient okHttpClient = requestBuild.getOkHttpClient();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            private ResultBody getResultBody(Response response) {
                return new ResultBody(request, response, requestBuild.getCookieHolder());
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (onRequestListener != null) {
                    onRequestListener.onFailure(getResultBody(null));
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (onRequestListener != null) {
                    onRequestListener.onSuccess(getResultBody(response));
                }
            }
        });
    }





    public abstract RequestMethod getRequestMethod();

    protected abstract T context();



    public T init() {
        this.url = null;
        this.params = null;
        this.headers = null;
        this.cookies = null;
        this.bodyString = null;
        this.bodyFile = null;
        this.bodyJSON = null;
        this.bodyCustom = null;
        this.contentType = null;

        return context();
    }


    public T url(String url) {
        this.url = url;

        return context();
    }

    public T header(String key, String value) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<>();
        }

        if (!RequestClientUtils.isEmpty(key)) {
            this.headers.put(key, value);
        }

        return context();
    }

    public T cookie(String key, String value) {
        if (this.cookies == null) {
            this.cookies = new LinkedHashMap<>();
        }

        if (!RequestClientUtils.isEmpty(key)) {
            this.cookies.put(key, value);
        }

        return context();
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

        return context();
    }

    public T withCookie(boolean useCookie) {
        this.useCookie = useCookie;

        return context();
    }

    public T readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;

        return context();
    }

    public T writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;

        return context();
    }

    public T connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;

        return context();
    }

    public T followRedirect(boolean http, boolean https) {
        this.followRedirects = http;
        this.followSslRedirects = https;

        return context();
    }

    public T proxy(String proxyHost, Integer proxyPort) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;

        return context();
    }

    public T certificate(FileBody certificate) {
        this.certificate = certificate;

        return context();
    }

}

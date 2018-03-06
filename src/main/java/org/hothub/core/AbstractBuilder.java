package org.hothub.core;

import okhttp3.*;
import org.hothub.base.AppConstants;
import org.hothub.base.RequestMethod;
import org.hothub.cookie.CookieHandler;
import org.hothub.http.OnRequestListener;
import org.hothub.http.SSLManager;
import org.hothub.response.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public abstract class AbstractBuilder<T extends AbstractBuilder> extends AbstractBuilderChain<T> {


    private static final Logger logger = LoggerFactory.getLogger(AbstractBuilder.class);


    public AbstractBuilder() {}




    /**
     * 同步，带返回值
     * */
    public ResultBody execute() {
        RequestHandler abstractRequest = new RequestHandler(this);


        Response response = null;
        Request request = abstractRequest.buildRequest();
        OkHttpClient okHttpClient = getOkHttpClient(abstractRequest);


        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new ResultBody(request, response);
    }



    /**
     * 异步，无返回值
     * */
    public void execute(OnRequestListener onRequestListener) {
        logger.info("entry - execute");

        RequestHandler abstractRequest = new RequestHandler(this);


        Call call;
        Request request = abstractRequest.buildRequest();
        OkHttpClient okHttpClient = getOkHttpClient(abstractRequest);


        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onRequestListener != null) {
                    onRequestListener.onFailure(null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (onRequestListener != null) {
                    onRequestListener.onSuccess(null);
                }
            }
        });
    }




    private OkHttpClient getOkHttpClient(RequestHandler abstractRequest) {
        readTimeOut = readTimeOut > 0 ? readTimeOut : AppConstants.DEFAULT_MILLISECONDS;
        writeTimeOut = writeTimeOut > 0 ? writeTimeOut : AppConstants.DEFAULT_MILLISECONDS;
        connTimeOut = connTimeOut > 0 ? connTimeOut : AppConstants.DEFAULT_MILLISECONDS;

        OkHttpClient.Builder okHttpClientBuild = new OkHttpClient.Builder()
                .sslSocketFactory(SSLManager.createSSLSocketFactory(), new SSLManager.TrustAllCerts())
                .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)         //连接超时时间
                .hostnameVerifier(new SSLManager.TrustAllHostnameVerifier());


        if (abstractRequest.useCookie) {
            okHttpClientBuild.cookieJar(new CookieHandler());
        }

        if (abstractRequest.isUseSSL()) {
            return okHttpClientBuild.build();
        }

        return okHttpClientBuild.sslSocketFactory(SSLManager.createSSLSocketFactory(), new SSLManager.TrustAllCerts())
                .hostnameVerifier(new SSLManager.TrustAllHostnameVerifier()).build();
    }



    public abstract RequestMethod getRequestMethod();

}

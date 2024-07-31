package org.hothub.requestclient.core;

import okhttp3.OkHttpClient;
import org.hothub.requestclient.base.AppConstants;

import java.util.concurrent.TimeUnit;

public class HttpClientInstance {


    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(AppConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .writeTimeout(AppConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .connectTimeout(AppConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .build();


    //OkhttpClient单例
    public static OkHttpClient getInstance() {
        return HttpClientInstance.okHttpClient;
    }


}

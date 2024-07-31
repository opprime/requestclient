package org.hothub.requestclient.core;

import okhttp3.*;
import org.hothub.requestclient.base.AppConstants;
import org.hothub.requestclient.base.ContentType;
import org.hothub.requestclient.cookie.CookieHolder;
import org.hothub.requestclient.pojo.FileBody;
import org.hothub.requestclient.utils.RequestClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RequestBuild extends AbstractAttribute {

    private final static Logger logger = LoggerFactory.getLogger(RequestBuild.class);

    private final AbstractBuilder<?> abstractBuilder;

    private final Request.Builder builder = new Request.Builder();

    private final CookieHolder cookieHolder = new CookieHolder();


    public RequestBuild(AbstractBuilder<?> abstractBuilder) {
        if (abstractBuilder == null) {
            throw new IllegalArgumentException("builder is must not null");
        }

        if (RequestClientUtils.isEmpty(abstractBuilder.url)) {
            throw new IllegalArgumentException("request url is must not null");
        }


        this.abstractBuilder = abstractBuilder;

        this.url = abstractBuilder.url;
        this.params = abstractBuilder.params;
        this.headers = abstractBuilder.headers;
        this.cookies = abstractBuilder.cookies;
        this.bodyString = abstractBuilder.bodyString;
        this.bodyFile = abstractBuilder.bodyFile;
        this.bodyJSON = abstractBuilder.bodyJSON;
        this.bodyCustom= abstractBuilder.bodyCustom;
        this.useCookie = abstractBuilder.useCookie;

        this.contentType = abstractBuilder.contentType;
        this.readTimeOut = abstractBuilder.readTimeOut;
        this.writeTimeOut = abstractBuilder.writeTimeOut;
        this.connTimeOut = abstractBuilder.connTimeOut;

        this.followRedirects = abstractBuilder.followRedirects;
        this.followSslRedirects = abstractBuilder.followSslRedirects;

        this.proxyHost = abstractBuilder.proxyHost;
        this.proxyPort = abstractBuilder.proxyPort;

        this.certificate = abstractBuilder.certificate;

        builder.url(buildRequestParams());
    }



    public OkHttpClient getOkHttpClient() {
        return configOkHttpClient(HttpClientInstance.getInstance());
    }



    public Request buildRequest() {
        Request.Builder methodBuild = null;

        switch (abstractBuilder.getRequestMethod()) {
            case GET:
                methodBuild = builder.get();
                break;
            case POST:
                methodBuild = builder.post(buildRequestBody());
                break;
            case PUT:
                methodBuild = builder.put(buildRequestBody());
                break;
            case DELETE:
                methodBuild = builder.delete(buildRequestBody());
                break;
            default:
                break;
        }


        buildRequestHeader(methodBuild);
        buildRequestCookie();

        return methodBuild == null ? null : methodBuild.build();
    }



    private RequestBody buildRequestBody() {
        if (contentType == ContentType.JSON) {
            //JSON参数
            if (this.bodyJSON == null) {
                throw new IllegalArgumentException("request must be not null, when contentType is json");
            }

            return RequestBody.create(bodyJSON, AppConstants.MEDIA_TYPE_APP_JSON);
        } else if (contentType == ContentType.APPLICATION_XML) {
            //XML参数
            if (this.bodyCustom == null) {
                throw new IllegalArgumentException("request must be not null, when contentType is application xml");
            }

            return RequestBody.create(bodyCustom, AppConstants.MEDIA_TYPE_APP_XML);
        } else if (contentType == ContentType.TEXT_XML) {
            //XML参数
            if (this.bodyCustom == null) {
                throw new IllegalArgumentException("request must be not null, when contentType is text xml");
            }

            return RequestBody.create(bodyCustom, AppConstants.MEDIA_TYPE_TEXT_XML);
        } else if (bodyFile == null || bodyFile.isEmpty()) {
            //纯参数
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (bodyString != null && !bodyString.isEmpty()) {
                for (Map.Entry<String, String> stringStringEntry : bodyString.entrySet()) {
                    formBodyBuilder.add(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
            }

            return formBodyBuilder.build();
        } else {
            //混合参数
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            //添加参数
            if (bodyString != null && !bodyString.isEmpty()) {
                for (Map.Entry<String, String> stringStringEntry : bodyString.entrySet()) {
                    builder.addPart(
                            Headers.of("Content-Disposition", "form-data; name=\"" + stringStringEntry.getKey() + "\""),
                            RequestBody.create(stringStringEntry.getValue(), ContentType.get(contentType))
                    );
                }
            }


            for (Map.Entry<String, FileBody> stringFileBodyEntry : bodyFile.entrySet()) {
                FileBody fileBody = stringFileBodyEntry.getValue();
                if (fileBody == null) {
                    continue;
                }

                if (fileBody.getFile() != null) {
                    builder.addFormDataPart(
                            fileBody.getKey(),
                            fileBody.getFileName(),
                            RequestBody.create(fileBody.getFile(), MediaType.parse(RequestClientUtils.guessMimeType(fileBody.getFileName())))
                    );

                    continue;
                }

                if (fileBody.getFileByte() != null) {
                    builder.addFormDataPart(
                            fileBody.getKey(),
                            fileBody.getFileName(),
                            RequestBody.create(fileBody.getFileByte(), MediaType.parse(RequestClientUtils.guessMimeType(fileBody.getFileName())))
                    );
                }
            }


            return builder.build();
        }
    }



    private String buildRequestParams() {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }

        try {
            StringBuilder stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                stringBuilder.append(String.format("&%s=%s", stringStringEntry.getKey(), URLEncoder.encode(stringStringEntry.getValue(), "utf-8")));
            }

            return url + (RequestClientUtils.isEmpty(stringBuilder) ? "" : url.lastIndexOf("?") > 0 ? stringBuilder : "?" + stringBuilder.substring(1));
        } catch (UnsupportedEncodingException e) {
            logger.error("org.hothub:requestclient, core.RequestBuild.buildRequestParams error", e);
        }

        return url;
    }



    private void buildRequestHeader(Request.Builder builder) {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }



    private void buildRequestCookie() {
        if (this.cookies != null && !this.cookies.isEmpty()) {
            HttpUrl httpUrl = HttpUrl.parse(this.url);
            if (httpUrl == null) {
                return;
            }

            List<Cookie> cookies = new ArrayList<>();

            for (Map.Entry<String, String> entry : this.cookies.entrySet()) {
                cookies.add(new Cookie.Builder().name(entry.getKey()).value(entry.getValue()).domain(httpUrl.host()).build());
            }

            cookieHolder.setRequestCookies(cookies);
        }
    }



    private OkHttpClient configOkHttpClient(OkHttpClient okHttpClient) {
        //有自定义配置 newBuilder
        OkHttpClient.Builder okhttpClientBuilder = okHttpClient.newBuilder();

        okhttpClientBuilder.setFollowRedirects$okhttp(followRedirects);
        okhttpClientBuilder.setFollowSslRedirects$okhttp(followSslRedirects);

        okhttpClientBuilder
                .readTimeout(this.readTimeOut > 0 ? this.readTimeOut : AppConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(this.writeTimeOut > 0 ? this.writeTimeOut : AppConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .connectTimeout(this.connTimeOut > 0 ? this.connTimeOut : AppConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        //设置代理IP
        if (!RequestClientUtils.isEmpty(proxyHost) && proxyPort != null && proxyPort > 0) {
            okhttpClientBuilder.setProxy$okhttp(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
        }

        //Cookie
        okhttpClientBuilder.setCookieJar$okhttp(cookieHolder);

        //启用https
        //if (isUseSSL()) {
            ////有设置证书文件
            //if (this.certificate == null) {
            //    okhttpClientBuilder.setSslSocketFactoryOrNull$okhttp();
            //    okhttpClientBuilder
            //            .sslSocketFactory(SSLManager.createSSLSocketFactory(), new SSLManager.TrustAllCerts())
            //            .hostnameVerifier(new SSLManager.TrustAllHostnameVerifier())
            //            .build();
            //} else {
            //    okhttpClientBuilder
            //            .sslSocketFactory(SSLManager.createSSLSocketFactory(this.certificate))
            //            .hostnameVerifier(new SSLManager.TrustAllHostnameVerifier())
            //            .build();
            //}
        //}

        return okhttpClientBuilder.build();
    }



    private boolean isUseSSL() {
        return !RequestClientUtils.isEmpty(this.url) && this.url.toLowerCase().startsWith("https");
    }



    public CookieHolder getCookieHolder() {
        return cookieHolder;
    }

}

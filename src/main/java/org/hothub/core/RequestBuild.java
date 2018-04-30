package org.hothub.core;

import okhttp3.*;
import org.hothub.base.AppConstants;
import org.hothub.base.ContentType;
import org.hothub.cookie.CookieManager;
import org.hothub.core.ssl.SSLManager;
import org.hothub.manager.ContextManager;
import org.hothub.pojo.FileBody;
import org.hothub.utils.RequestClientUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RequestBuild extends AbstractAttribute {


    private AbstractBuilder mAbstractBuilder;

    private Request.Builder builder = new Request.Builder();


    public RequestBuild(AbstractBuilder abstractBuilder) {
        if (abstractBuilder == null) {
            throw new IllegalArgumentException("builder is must not null");
        }

        if (RequestClientUtils.isEmpty(abstractBuilder.url)) {
            throw new IllegalArgumentException("request url is must not null");
        }


        this.mAbstractBuilder = abstractBuilder;

        this.url = abstractBuilder.url;
        this.params = abstractBuilder.params;
        this.headers = abstractBuilder.headers;
        this.cookies = abstractBuilder.cookies;
        this.bodyString = abstractBuilder.bodyString;
        this.bodyFile = abstractBuilder.bodyFile;
        this.useCookie = abstractBuilder.useCookie;


        builder.url(buildRequestParam());
    }






    public Request buildRequest() {
        Request.Builder methodBuild = null;

        switch (mAbstractBuilder.getRequestMethod()) {
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


        methodBuild = buildRequestHeader(methodBuild);
        buildRequestCookie();

        return methodBuild == null ? null : methodBuild.build();
    }



    private RequestBody buildRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        //添加参数
        if (bodyString != null && !bodyString.isEmpty()) {
            for (Map.Entry<String, String> stringStringEntry : bodyString.entrySet()) {
                builder.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"" + stringStringEntry.getKey() + "\""),
                        RequestBody.create(ContentType.get(contentType), stringStringEntry.getValue()));
            }
        }

        if (bodyFile != null && !bodyFile.isEmpty()) {
            for (Map.Entry<String, FileBody> stringFileBodyEntry : bodyFile.entrySet()) {
                FileBody fileBody = stringFileBodyEntry.getValue();
                if (fileBody == null) {
                    continue;
                }

                if (fileBody.getFile() != null) {
                    builder.addFormDataPart(
                            fileBody.getKey(),
                            fileBody.getFileName(),
                            RequestBody.create(MediaType.parse(RequestClientUtils.guessMimeType(fileBody.getFileName())), fileBody.getFile())
                    );

                    continue;
                }

                if (fileBody.getFileByte() != null) {
                    builder.addFormDataPart(
                            fileBody.getKey(),
                            fileBody.getFileName(),
                            RequestBody.create(MediaType.parse(RequestClientUtils.guessMimeType(fileBody.getFileName())), fileBody.getFileByte())
                    );
                }
            }
        }

        return builder.build();
    }



    private String buildRequestParam() {
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
            e.printStackTrace();
        }

        return url;
    }





    private Request.Builder buildRequestHeader(Request.Builder builder) {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return builder;
    }





    @SuppressWarnings("unchecked")
    private void buildRequestCookie() {
        if (this.cookies != null && !this.cookies.isEmpty()) {
            HttpUrl httpUrl = HttpUrl.parse(this.url);
            if (httpUrl == null) {
                return;
            }
            List<Cookie> originCookie = ContextManager.get(httpUrl.host(), List.class);
            if (originCookie == null) {
                originCookie = new ArrayList<>();
            }

            for (Map.Entry<String, String> entry : this.cookies.entrySet()) {
                originCookie.add(new Cookie.Builder().name(entry.getKey()).value(entry.getValue()).domain(httpUrl.host()).build());
            }

            ContextManager.set(httpUrl.host(), originCookie);
        }
    }




    public OkHttpClient getOkHttpClient() {
        return getOkHttpBuild().build();
    }

    private List<Cookie> getRequestCookie() {
        HttpUrl httpUrl = HttpUrl.parse(url);

        return httpUrl != null ? ContextManager.get(httpUrl.host(), List.class) : null;
    }

    public List<Cookie> getResponseCookie() {
        HttpUrl httpUrl = HttpUrl.parse(url);

        return httpUrl != null ? ContextManager.get(httpUrl.host(), List.class) : null;
    }

    private OkHttpClient.Builder getOkHttpBuild() {
        readTimeOut = readTimeOut > 0 ? readTimeOut : AppConstants.DEFAULT_MILLISECONDS;
        writeTimeOut = writeTimeOut > 0 ? writeTimeOut : AppConstants.DEFAULT_MILLISECONDS;
        connTimeOut = connTimeOut > 0 ? connTimeOut : AppConstants.DEFAULT_MILLISECONDS;

        OkHttpClient.Builder okHttpClientBuild = new OkHttpClient.Builder()
                .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS);         //连接超时时间

        if (this.useCookie) {
            okHttpClientBuild.cookieJar(new CookieManager(getRequestCookie()));
        }

        if (!isUseSSL()) {
            return okHttpClientBuild;
        }

        return okHttpClientBuild
                .sslSocketFactory(SSLManager.createSSLSocketFactory(), new SSLManager.TrustAllCerts())
                .hostnameVerifier(new SSLManager.TrustAllHostnameVerifier());
    }

    private boolean isUseSSL() {
        return !RequestClientUtils.isEmpty(this.url) && this.url.toLowerCase().startsWith("https");
    }
}

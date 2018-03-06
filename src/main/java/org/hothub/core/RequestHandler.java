package org.hothub.core;

import okhttp3.*;
import org.hothub.pojo.FileBody;
import org.hothub.utils.CommonUtils;

import java.util.Map;

public class RequestHandler extends AbstractAttribute {


    private AbstractBuilder mAbstractBuilder;

    private Request.Builder builder = new Request.Builder();


    public RequestHandler(AbstractBuilder abstractBuilder) {
        if (abstractBuilder == null) {
            throw new IllegalArgumentException("builder is must not null");
        }

        if (CommonUtils.isEmpty(abstractBuilder.url)) {
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


        builder.url(this.url);
    }


    public boolean isUseSSL() {
        return !CommonUtils.isEmpty(this.url) && this.url.toLowerCase().startsWith("https");
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

        return methodBuild == null ? null : methodBuild.build();
    }






    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");




    private RequestBody buildRequestBody() {
        switch (mAbstractBuilder.getRequestMethod()) {
            case GET:
                return buildGet();
            case POST:
            case PUT:
            case DELETE:
                return buildPost();
            default:
                break;
        }

        return null;
    }







    private RequestBody buildGet() {
        FormBody.Builder builder = new FormBody.Builder();

        //添加参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                builder.add(stringStringEntry.getKey(), params.get(stringStringEntry.getValue()));
            }
        }

        return builder.build();
    }



    private RequestBody buildPost() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        //添加参数
        if (bodyString != null && !bodyString.isEmpty()) {
            for (Map.Entry<String, String> stringStringEntry : bodyString.entrySet()) {
                builder.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"" + stringStringEntry.getKey() + "\""),
                        RequestBody.create(JSON, stringStringEntry.getValue()));
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
                            RequestBody.create(MediaType.parse(CommonUtils.guessMimeType(fileBody.getFileName())), fileBody.getFile())
                    );

                    continue;
                }

                if (fileBody.getFileByte() != null) {
                    builder.addFormDataPart(
                            fileBody.getKey(),
                            fileBody.getFileName(),
                            RequestBody.create(MediaType.parse(CommonUtils.guessMimeType(fileBody.getFileName())), fileBody.getFileByte())
                    );
                }
            }
        }

        return builder.build();
    }




}

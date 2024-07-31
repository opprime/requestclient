package org.hothub.requestclient.base;

import okhttp3.MediaType;
import org.hothub.requestclient.utils.RequestClientUtils;

import java.nio.charset.Charset;

public enum  ContentType {


    URL_ENCODE("application/x-www-form-urlencoded"),

    JSON("application/json"),

    APPLICATION_XML("application/xml"),

    TEXT_XML("text/xml"),

    FORM_DATA("multipart/form-data");


    private final String type;

    ContentType(String type) {
        this.type = type;
    }





    public static MediaType get() {
        return get(null, "utf-8");
    }



    public static MediaType get(ContentType contentType) {
        String mediaType =
                (contentType == null ? ContentType.URL_ENCODE.type : contentType.type)
                        + "; charset=utf-8";

        return MediaType.parse(mediaType.toLowerCase());
    }



    public static MediaType get(ContentType contentType, String charset) {
        String mediaType =
                (contentType == null ? ContentType.URL_ENCODE.type : contentType.type)
                        + "; charset="
                        + Charset.forName(RequestClientUtils.isEmpty(charset) ? "utf-8" : charset);

        return MediaType.parse(mediaType.toLowerCase());
    }

}

package org.hothub.requestclient.base;

import okhttp3.MediaType;

public class AppConstants {

    public static final long DEFAULT_MILLISECONDS = 3_000L;

    public static final MediaType MEDIA_TYPE_APP_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_APP_XML = MediaType.parse("application/xml;charset=utf-8");

    public static final MediaType MEDIA_TYPE_TEXT_XML = MediaType.parse("text/xml;charset=utf-8");


}

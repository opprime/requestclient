package org.hothub.core;

import org.hothub.base.ContentType;
import org.hothub.pojo.FileBody;

import java.util.Map;

public abstract class AbstractAttribute {


    protected String url;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected Map<String, String> cookies;
    protected Map<String, String> bodyString;
    protected Map<String, FileBody> bodyFile;
    protected boolean useCookie = true;

    protected ContentType contentType;
    protected long readTimeOut;
    protected long writeTimeOut;
    protected long connTimeOut;

    protected boolean followRedirects = true;
    protected boolean followSslRedirects = true;

    protected String proxyHost;
    protected Integer proxyPort;


}

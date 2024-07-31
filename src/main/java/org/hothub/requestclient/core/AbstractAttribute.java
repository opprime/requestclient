package org.hothub.requestclient.core;

import org.hothub.requestclient.base.ContentType;
import org.hothub.requestclient.pojo.FileBody;

import java.util.Map;

public abstract class AbstractAttribute {


    protected String url;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected Map<String, String> cookies;
    protected Map<String, String> bodyString;
    protected Map<String, FileBody> bodyFile;
    protected String bodyJSON;
    protected String bodyCustom;
    protected boolean useCookie = false;

    protected ContentType contentType;
    protected long readTimeOut;
    protected long writeTimeOut;
    protected long connTimeOut;

    protected boolean followRedirects = true;
    protected boolean followSslRedirects = true;

    protected String proxyHost;
    protected Integer proxyPort;

    protected FileBody certificate;


}

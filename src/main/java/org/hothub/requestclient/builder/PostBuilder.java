package org.hothub.requestclient.builder;

import org.hothub.requestclient.base.ContentType;
import org.hothub.requestclient.base.RequestMethod;
import org.hothub.requestclient.core.AbstractBuilder;
import org.hothub.requestclient.pojo.FileBody;
import org.hothub.requestclient.utils.RequestClientUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PostBuilder extends AbstractBuilder<PostBuilder> {


    public PostBuilder body(String key, String value) {
        if (this.bodyString == null) {
            this.bodyString = new LinkedHashMap<>();
        }

        if (!RequestClientUtils.isEmpty(key)) {
            this.bodyString.put(key, value);
        }

        return this;
    }

    public PostBuilder body(String key, FileBody fileBody) {
        if (this.bodyFile == null) {
            this.bodyFile = new LinkedHashMap<>();
        }

        if (!RequestClientUtils.isEmpty(key)) {
            this.bodyFile.put(key, fileBody);
        }

        return this;
    }

    public PostBuilder body(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                this.body(entry.getKey(), entry.getValue());
            }
        }

        return this;
    }

    public PostBuilder body(String json) {
        this.bodyJSON = json;

        return this;
    }

    public PostBuilder body(String body, ContentType contentType) {
        this.bodyCustom = body;
        this.contentType = contentType;

        return this;
    }

    public PostBuilder contentType(ContentType contentType) {
        this.contentType = contentType;

        return this;
    }



    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    protected PostBuilder context() {
        return PostBuilder.this;
    }


}

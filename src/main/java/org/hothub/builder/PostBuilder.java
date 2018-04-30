package org.hothub.builder;

import org.hothub.base.ContentType;
import org.hothub.base.RequestMethod;
import org.hothub.core.AbstractBuilder;
import org.hothub.pojo.FileBody;
import org.hothub.utils.RequestClientUtils;

import java.util.LinkedHashMap;

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

    public PostBuilder contentType(ContentType contentType) {
        this.contentType = contentType;

        return this;
    }



    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}

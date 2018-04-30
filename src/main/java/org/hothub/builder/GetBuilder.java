package org.hothub.builder;

import org.hothub.base.RequestMethod;
import org.hothub.core.AbstractBuilder;
import org.hothub.utils.RequestClientUtils;

import java.util.LinkedHashMap;

public class GetBuilder extends AbstractBuilder<GetBuilder> {


    public GetBuilder param(String key, String value) {
        if (this.params == null) {
            this.params = new LinkedHashMap<>();
        }

        if (!RequestClientUtils.isEmpty(key)) {
            this.params.put(key, value);
        }

        return this;
    }



    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}

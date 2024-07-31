package org.hothub.requestclient.builder;

import org.hothub.requestclient.base.RequestMethod;
import org.hothub.requestclient.core.AbstractBuilder;

public class PutBuilder extends AbstractBuilder<PutBuilder> {
    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.PUT;
    }

    @Override
    protected PutBuilder context() {
        return PutBuilder.this;
    }
}

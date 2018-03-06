package org.hothub.builder;

import org.hothub.base.RequestMethod;
import org.hothub.core.AbstractBuilder;

public class PutBuilder extends AbstractBuilder<PutBuilder> {
    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.PUT;
    }
}

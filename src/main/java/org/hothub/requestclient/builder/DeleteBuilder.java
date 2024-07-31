package org.hothub.requestclient.builder;

import org.hothub.requestclient.base.RequestMethod;
import org.hothub.requestclient.core.AbstractBuilder;

public class DeleteBuilder extends AbstractBuilder<DeleteBuilder> {
    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.DELETE;
    }
}

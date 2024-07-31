package org.hothub.requestclient;

import org.hothub.requestclient.builder.DeleteBuilder;
import org.hothub.requestclient.builder.GetBuilder;
import org.hothub.requestclient.builder.PostBuilder;
import org.hothub.requestclient.builder.PutBuilder;

public class RequestClient {


    public static GetBuilder get() {
        return new GetBuilder();
    }


    public static PostBuilder post() {
        return new PostBuilder();
    }


    public static PutBuilder put() {
        return new PutBuilder();
    }


    public static DeleteBuilder delete() {
        return new DeleteBuilder();
    }


}

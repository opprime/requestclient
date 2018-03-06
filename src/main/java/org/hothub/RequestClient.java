package org.hothub;

import org.hothub.builder.DeleteBuilder;
import org.hothub.builder.GetBuilder;
import org.hothub.builder.PostBuilder;
import org.hothub.builder.PutBuilder;


public class RequestClient {


    public RequestClient() {
        super();
    }



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

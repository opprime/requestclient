package org.hothub.requestclient.test;

import org.hothub.requestclient.RequestClient;
import org.hothub.requestclient.response.ResultBody;

import java.io.IOException;

public class RequestTest {

    public static void main(String[] args) throws IOException {

        ResultBody resultBody = RequestClient.get()
                .url("https://www.baidu.com")
                .execute();

        System.out.println(resultBody.toString());

        //System.out.println(resultBody.getResponseCookie());
        //System.out.println(resultBody.toByte().length);
    }

}

package org.hothub.requestclient.test;

import org.hothub.requestclient.RequestClient;
import org.hothub.requestclient.response.ResultBody;

import java.io.IOException;
import java.util.Arrays;

public class RequestTest {

    public static void main(String[] args) throws IOException {

        ResultBody getBody = RequestClient.get()
                .url("https://www.baidu.com")
                .execute();

        System.out.println(getBody.toString());
        System.out.println(getBody.toString());
        System.out.println(Arrays.toString(getBody.toByte()));

        //System.out.println(getBody.getResponseCookie());
        //System.out.println(getBody.toByte().length);


        ResultBody postBody = RequestClient.post()
                .url("https://www.baidu.com")
                .body("from", "abc")
                .execute();

        //System.out.println(postBody.toString());

    }

}

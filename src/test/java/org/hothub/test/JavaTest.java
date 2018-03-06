package org.hothub.test;

import okhttp3.*;
import org.hothub.RequestClient;
import org.hothub.base.AppConstants;
import org.hothub.http.OnRequestListener;
import org.hothub.pojo.FileBody;
import org.hothub.response.ResultBody;
import org.hothub.utils.CommonUtils;

import java.io.File;

public class JavaTest {

    public static void main(String[] args) {

        ResultBody resultBody = RequestClient.get()
                .url("https://www.baidu.com")
                .param("from", "sss")
                .withCookie(true)
                .readTimeOut(10000)
                .execute();


        Response response = resultBody.getResponse();
        Request request = resultBody.getRequest();


//        Cookie.parse(request.url())
        System.out.println(resultBody.toString());

        ResultBody resultBody1 = RequestClient.post()
                .url("https://vip.baasia.com/filetest/upload")
                .body("moduleCode", "cm")
                .body("file", new FileBody("hahaha", new File("C:\\Users\\opprime\\Desktop\\hahaha.jpg")))
                .execute();


//        System.out.println(resultBody1.toString());


        ResultBody resultBody2 = RequestClient.get()
                .url("https://vip.baasia.com/filetest/view/ed46ac35dbde4d90a623204bf9108153qmcm")
                .execute();


        File file = resultBody2.toFile("C:\\Users\\opprime\\Desktop\\", "heiheihei.jpg");

        System.out.println(file.getName());
        System.out.println(AppConstants.DEFAULT_MILLISECONDS);
    }
}

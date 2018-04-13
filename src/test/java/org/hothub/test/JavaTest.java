package org.hothub.test;

import okhttp3.*;
import org.hothub.RequestClient;
import org.hothub.base.AppConstants;
import org.hothub.base.ContentType;
import org.hothub.pojo.FileBody;
import org.hothub.response.ResultBody;
import sun.misc.BASE64Encoder;
import sun.misc.Request;

import java.io.File;
import java.nio.charset.Charset;

public class JavaTest {

    public static void main(String[] args) {

        System.out.println(ContentType.get(ContentType.URL_ENCODE, "gb2312"));


        System.out.println(RequestClient.post()
                .url("https://www.baidu.com/")
                .body("haha", "dd")
                .contentType(ContentType.URL_ENCODE)
                .execute());

//        System.out.println(Charset.forName("UTF-8") + "; adfad");

//        System.out.println(RequestClient.post()
//                .url("http://localhost:8080/oauth/token")
//                .withCookie(true)
//                .header("Authorization", "Basic " + new BASE64Encoder().encode("fooClientIdPassword:secret".getBytes()))
//                .body("username", "username")
//                .body("password", "password")
//                .body("grant_type", "password")
//                .body("redirect_uri", "http://localhost:8080/redirectUrl")
//                .execute());
//
//
//        System.out.println(RequestClient.get()
//                .url("http://localhost:8080/oauth/check_token")
//                .withCookie(true)
//                .header("Authorization", "Basic " + new BASE64Encoder().encode("fooClientIdPassword:secret".getBytes()))
//                .param("token", "59da78e4-c73b-45e1-a44d-ccded1130e94")
//                .execute());






        //        System.out.println(new BASE64Encoder().encode("fooClientIdPassword:secret".getBytes()));

//        System.out.println(RequestClient.get()
//                .withCookie(true)
//                .url("http://localhost:8080/bar")
//                .execute());

//        System.out.println(RequestClient.post()
//                .url("http://localhost:8080/login")
//                .body("username", "user1")
//                .body("password", "pass")
//                .execute());

//        http://localhost:8080/oauth2/oauth/token?grant_type=authorization_code&code=t7ol7D&redirect_uri=http://localhost:8080/web&client_id=easylocate&client_secret=secret

//        System.out.println(RequestClient.post()
//                .url("http://localhost:8080/oauth/token")
//                .withCookie(true)
////                .header("Authorization", "Basic " + new BASE64Encoder().encode("fooClientIdPassword:secret".getBytes()))
//                .body("client_id", "fooClientIdPassword")
//                .body("client_secret", "secret")
//                .body("redirect_uri", "http://localhost:8080/redirectUrl")
//                .body("grant_type", "password")
//                .body("code", "Swnh89")
//                .execute());

// -d ‘grant_type=authorization_code&code=G0C20Z&redirect_uri=http://www.baidu.com‘ "http://client:secret@localhost:8080/uaa/oauth/token"






//        {"access_token":"6382c79b-5e3e-4be8-bc43-70937f061306","token_type":"bearer","refresh_token":"14973ec7-d8c4-4967-8e1b-c0923e408526","expires_in":3599,"scope":"trust"}
//        {"access_token":"6382c79b-5e3e-4be8-bc43-70937f061306","token_type":"bearer","refresh_token":"14973ec7-d8c4-4967-8e1b-c0923e408526","expires_in":3432,"scope":"trust"}

//        ResultBody resultBody = RequestClient.get()
//                .url("https://www.baidu.com")
//                .param("from", "sss")
//                .withCookie(true)
//                .readTimeOut(10000)
//                .execute();
//
//
//        Response response = resultBody.getResponse();
//        Request request = resultBody.getRequest();
//
//
////        Cookie.parse(request.url())
//        System.out.println(resultBody.toString());
//
//        ResultBody resultBody1 = RequestClient.post()
//                .url("https://vip.baasia.com/filetest/upload")
//                .body("moduleCode", "cm")
//                .body("file", new FileBody("hahaha", new File("C:\\Users\\opprime\\Desktop\\hahaha.jpg")))
//                .execute();
//
//
////        System.out.println(resultBody1.toString());
//
//
//        ResultBody resultBody2 = RequestClient.get()
//                .url("https://vip.baasia.com/filetest/view/ed46ac35dbde4d90a623204bf9108153qmcm")
//                .execute();
//
//
//        File file = resultBody2.toFile("C:\\Users\\opprime\\Desktop\\", "heiheihei.jpg");
//
//        System.out.println(file.getName());
//        System.out.println(AppConstants.DEFAULT_MILLISECONDS);
    }
}

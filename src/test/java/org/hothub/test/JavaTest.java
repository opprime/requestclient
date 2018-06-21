package org.hothub.test;


import okhttp3.Cookie;
import okhttp3.HttpUrl;
import org.hothub.RequestClient;
import org.hothub.base.ContentType;
import org.hothub.response.ResultBody;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaTest {

    public static void main(String[] args) {


        String username = "cybbysyey";
        String password = "147852369";           //147852369

        String passwordMd5 = JavaTest.toMd5("r4QD4vG9" + password);
        String cookieToken = "";

        System.out.println(passwordMd5);
//        ResultBody getResult = RequestClient.get()
//                .url("http://youeryuan.yinglets.com/eagles-ibaby/kindergarten/login.do")
////                .withCookie(false)
//                .execute();
//
//
//        System.out.println(getResult.getRequestCookie());
//        System.out.println(getResult.getResponseCookie());
//        List<Cookie> cookieListGet = Cookie.parseAll(HttpUrl.parse("http://youeryuan.yinglets.com/eagles-ibaby"), getResult.getResponse().headers());
//        System.out.println(cookieListGet);
//        cookieToken = cookieListGet.get(0).value();
//        System.out.println("cookieToken = " + cookieToken);
//        List<Cookie> cookieList = getResult.getCookie();

//                System.out.println(cookieList);
//
//                Cookie cookie = cookieList.get(0);
//        System.out.println(cookie.value());

        System.out.println("------------ post result ----------------");

        ResultBody postResult = RequestClient.post()
                .url("http://youeryuan.yinglets.com/eagles-ibaby/kindergarten/login.do")//?username=" + username + "&password=" + passwordMd5)
                .body("username", username)
                .body("password", passwordMd5)
                .body("captcha", "")
                .followRedirect(false, false)
                .proxy("192.168.10.107", 8888)
//                .withCookie(false)
//                .cookie("SHAREJSESSIONID", cookieToken)
//                        .cookie("ibabyuser", "cybbysyey@@" + CommonUtils.toMd5("r4QD4vG9" + "147852369"))
//                .header("X-Requested-With", "XMLHttpRequest")
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
//                .cookie("UM_distinctid", "161699d156a77-09f508fd41000a-3b60490d-19fa51-161699d156bce")
//                .header("Referer", "http://youeryuan.yinglets.com/eagles-ibaby/kindergarten/login.do")
//                .header("Referer", "http://youeryuan.yinglets.com/eagles-ibaby/kindergarten/login.do")//;JSESSIONID="+cookieToken)
//                .header("Origin", "http://youeryuan.yinglets.com")
//                .header("Host", "youeryuan.yinglets.com")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
//                .header("Cookie", "SHAREJSESSIONID=" + cookieToken)// + "; ibabyuser=cybbysyey@@" + passwordMd5)
//                .contentType(ContentType.URL_ENCODE)
                .execute();

        System.out.println("------------ post result - REQUEST - HEADERS ----------------");

        System.out.println(postResult.getRequest().headers());

        System.out.println("------------ post result - RESPONSE - HEADERS ----------------");

        System.out.println(postResult.getResponse().headers());

        System.out.println("------------ post result - RESPONSE - COOKIE----------------");

        System.out.println(postResult.getRequestCookie());
        System.out.println(postResult.getResponseCookie());
        List<Cookie> cookieList = Cookie.parseAll(HttpUrl.parse("http://youeryuan.yinglets.com"), postResult.getResponse().headers());
        System.out.println(cookieList);

        cookieToken = postResult.getResponseCookie().get(0).value();

//        cookieToken = cookieList.get(0).value();

        System.out.println("------------ post result - STRING----------------");

        System.out.println(postResult);

        System.out.println("------------ post result - STRING - END ----------------");

//cookieToken = postResult.getResponse().headers().get(0).value();

//        ResultBody adminResult = RequestClient.get()
//                .url("http://youeryuan.yinglets.com/eagles-ibaby/kindergarten/admin.do")
//                .cookie("SHAREJSESSIONID", cookieToken)
//                .cookie("ibabyuser", "cybbysyey@@" + passwordMd5)
////                .header("Referer", "http://youeryuan.yinglets.com/eagles-ibaby/kindergarten/login.do;JSESSIONID=" + cookie.value())
////                .header("Origin", "http://youeryuan.yinglets.com")
////                .header("Host", "youeryuan.yinglets.com")
//                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                .header("Accept-Encoding", "gzip, deflate")
//                .header("Accept-Language", "zh-CN,zh;q=0.9")
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
//                .header("Upgrade-Insecure-Requests", "1")
//                .withCookie(false)
//                .execute();
//
//        System.out.println(adminResult.getRequest().headers());
//        System.out.println(adminResult.getCookie());
//        System.out.println(adminResult.getResponse().headers());
//
//        try {
//            String gbk = new String(adminResult.toString().getBytes("UTF-8"), "GBK");
//            System.out.println(gbk);
//            String iso = new String(gbk.getBytes("UTF-8"), "ISO8859-1");
//            System.out.println(iso);
//            String utf8 = new String(gbk.getBytes("ISO8859-1"), "utf-8");
//            System.out.println(utf8);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(adminResult);


//        System.out.println(RequestClient.get()
//                .url("http://localhost:8088/v150/parent/getBindBabyStatus")
//                .param("parentId", "107c52e3445f4e629652ea5cd6466c59")
//                .execute());

        System.out.println("cookieToken = " + cookieToken);

        System.out.println(RequestClient.post()
                .url("http://appapi.baby.dev.yinglets.com/alisports/v150/oauth/token/assistant")
                .body("token", cookieToken)
//                .body("token", "f28a9c06-37de-4693-ab50-544dd6b0fb79")
//                .body("token", "b66e7510-9263-4d52-8db7-8a16c44ca586")
                .execute());


//        Map<String, String> map = new HashMap<>();
//        map.put("school_id", "aaa");
//        map.put("school_name", "bbb");
//
//        ResultBody resultBody = RequestClient.post()
//                .url("https://www.baidu.com")
//                .body(map)
//                .execute();

//        System.out.println(resultBody.toString());
//        System.out.println(resultBody.toByte().length);
//        System.out.println(resultBody.toString());

//        resultBody.toFile("D:", "html.html");




    }





    public static String toMd5(String text) {
        String result = null;
        if (text == null || text.isEmpty()) {
            return "";
        } else {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] btInput = text.getBytes();
                md.update(btInput);
                byte[] btResult = md.digest();
                StringBuffer sb = new StringBuffer();
                byte[] var6 = btResult;
                int var7 = btResult.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    byte b = var6[var8];
                    int bt = b & 255;
                    if (bt < 16) {
                        sb.append(0);
                    }

                    sb.append(Integer.toHexString(bt));
                }

                result = sb.toString();
            } catch (NoSuchAlgorithmException var11) {
                var11.printStackTrace();
            }

            return result;
        }
    }
}

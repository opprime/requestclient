package org.hothub.test;


import org.hothub.RequestClient;
import org.hothub.response.ResultBody;

import java.util.HashMap;
import java.util.Map;

public class JavaTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("school_id", "aaa");
        map.put("school_name", "bbb");

        ResultBody resultBody = RequestClient.post()
                .url("https://www.baidu.com")
                .body(map)
                .execute();

//        System.out.println(resultBody.toString());
//        System.out.println(resultBody.toByte().length);
//        System.out.println(resultBody.toString());

//        resultBody.toFile("D:", "html.html");




    }
}

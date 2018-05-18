package org.hothub.test;


import org.hothub.RequestClient;

import java.util.HashMap;
import java.util.Map;

public class JavaTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("school_id", "aaa");
        map.put("school_name", "bbb");

        System.out.println(RequestClient.post()
                .url("https://www.baidu.com")
                .body(map)
                .execute());

    }
}

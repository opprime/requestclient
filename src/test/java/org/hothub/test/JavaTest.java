package org.hothub.test;

import org.hothub.RequestClient;

public class JavaTest {

    public static void main(String[] args) {
        System.out.println(RequestClient.get()
                .url("https://www.baidu.com")
                .execute());
    }
}

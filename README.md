# 一个基于OkHttp3，用于JAVA服务端的HTTP网络请求框架。


![maven](https://img.shields.io/maven-central/v/org.hothub/requestclient.svg)
![license](https://img.shields.io/github/license/opprime/requestclient.svg)




## 使用方法
>pom.xml引用
```
    <dependency>
        <groupId>org.hothub</groupId>
        <artifactId>requestclient</artifactId>
        <version>2.0.1</version>
    </dependency>

    <!-- 如果项目中未引用OkHttp，则还需要添加下面的引用 -->
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
        <version>4.9.3</version>
    </dependency>
```

>gradle引用
```
    compile 'org.hothub:requestclient:2.0.1'
    
    <!-- 如果项目中未引用OkHttp，则还需要添加下面的引用 -->
    compile 'com.squareup.okhttp3:okhttp:4.9.3'
```



## 功能介绍
* GET请求（同步+异步）
* POST请求（同步+异步）
* DELETE请求（同步+异步）
* PATCH请求（同步+异步）
* PUT请求（同步+异步）
* 返回结果可转String、byte、Stream、Reader、File
* 支持Cookie的保持
* 支持自签名网站https的访问，提供方法设置下证书就行
* 支持设置代理


## 代码示例：

### GET请求（同步）：
```
    ResultBody resultBody = RequestClient.get()
            .url("http://www.hothub.org")
            .header("x-hader", "a header")
            .param("from", "github")
            .withCookie(true)
            .readTimeOut(10000)
            .writeTimeOut(10000)
            .connTimeOut(10000)
            .execute();
```



### GET请求（异步）：
```
    RequestClient.get()
            .url("http://www.hothub.org")
            .header("x-hader", "a header")
            .param("from", "github")
            .withCookie(true)
            .readTimeOut(10000)
            .writeTimeOut(10000)
            .connTimeOut(10000)
            .execute(new OnRequestListener() {
                @Override
                public void onSuccess(String response) {
                                             
                }
                     
                @Override
                public void onFailure(String response) {
                     
                }
            });
```



### POST请求（同步）：
```
    ResultBody resultBody1 = RequestClient.post()
            .url("http://www.hothub.org/upload")
            .header("x-hader", "a header")
            .contentType(ContentType.URL_ENCODE)
            .body("code", "cm")
            .body("file", new FileBody("filename", new File("D:\\file\\requestclient.jpg")))
            .execute();
```



### POST请求（异步）：
```
    RequestClient.post()
            .url("http://www.hothub.org/upload")
            .header("x-hader", "a header")
            .contentType(ContentType.JSON)
            .body("code", "cm")
            .body("file", new FileBody("filename", new File("D:\\file\\requestclient.jpg")))
            .execute(new OnRequestListener() {
                @Override
                public void onSuccess(String response) {
                                             
                }

                @Override
                public void onFailure(String response) {
                     
                }
            });
```



### PUT请求：



### DELETE请求：




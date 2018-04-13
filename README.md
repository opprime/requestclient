# requestClient
一个依赖于OkHttp3，用于JAVA服务端的网络请求框架。

## 使用方法
>pom.xml引用
```
    <dependency>
        <groupId>org.hothub</groupId>
        <artifactId>requestclient</artifactId>
        <version>1.0.0</version>
    </dependency>
```

>gradle引用
```
    compile 'org.hothub:requestclient:1.0.0'
```



## 功能介绍
* GET请求（同步+异步）
* POST请求（同步+异步）
* DELETE请求（同步+异步）
* PATCH请求（同步+异步）
* PUT请求（同步+异步）
* 返回结果可转String、byte、Stream、Reader、File
* 支持session的保持
* 支持自签名网站https的访问，提供方法设置下证书就行


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
            .body("code", "cm")
            .body("file", new FileBody("filename", new File("D:\\file\\hahaha.jpg")))
            .execute();
```



### POST请求（异步）：
```
    RequestClient.post()
            .url("http://www.hothub.org/upload")
            .header("x-hader", "a header")
            .body("code", "cm")
            .body("file", new FileBody("filename", new File("D:\\file\\hahaha.jpg")))
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




package org.hothub.response;

import okhttp3.*;
import org.hothub.manager.ContextManager;
import org.hothub.utils.RequestClientUtils;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class ResultBody {

    private HttpUrl httpUrl;
    private Request request;
    private Response response;
    private ResponseBody responseBody;
    private List<Cookie> requestCookieList;
    private List<Cookie> responseCookieList;

    private String string;
    private byte[] bytes;
    private File file;
    private InputStream stream;
    private Reader reader;



    public ResultBody(Request request, Response response, String requestUrl) {
        this.request = request;
        this.response = response;
        this.httpUrl = HttpUrl.parse(requestUrl);
        this.responseBody = response != null ? response.body() : null;
        this.requestCookieList = request != null ? Cookie.parseAll(this.httpUrl, request.headers()) : Collections.EMPTY_LIST;
        this.responseCookieList = response != null ? Cookie.parseAll(this.httpUrl, response.headers()) : Collections.EMPTY_LIST;

        string = null;
        bytes = null;
        file = null;
        stream = null;
        reader = null;

        //清除线程本地变量
        ContextManager.remove();
    }


    @Override
    public String toString() {
        String result = null;

        if (RequestClientUtils.isEmpty(string)) {
            try {
                result = responseBody != null ? responseBody.string() : null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            string = result;
        }

        return string;
    }



    public byte[] toByte() {
        byte[] result = null;

        if (bytes == null) {
            try {
                result = responseBody != null ? responseBody.bytes() : null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            bytes = result;
        }

        return bytes;
    }



    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File toFile(String fileDirectory, String fileName) {
        File result = null;

        if (file == null) {
            InputStream inputStream = null;
            byte[] buf = new byte[2048];
            int len;
            FileOutputStream fos = null;

            try {
                inputStream = toStream();
                if (inputStream == null) {
                    return null;
                }

                File dir = new File(fileDirectory);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(dir, fileName);
                fos = new FileOutputStream(file);
                while ((len = inputStream.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();

                result = file;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    responseBody.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            file = result;
        }

        return file;
    }



    public InputStream toStream() {
        InputStream result;

        if (stream == null) {
            result = responseBody != null ? responseBody.byteStream() : null;

            stream = result;
        }

        return stream;
    }



    public Reader toReader() {
        Reader result;

        if (reader == null) {
            result = responseBody != null ? responseBody.charStream() : null;

            reader = result;
        }

        return reader;
    }



    public Request getRequest() {
        return request;
    }



    public Response getResponse() {
        return response;
    }



    public List<Cookie> getRequestCookie() {
        return requestCookieList;
    }



    public List<Cookie> getResponseCookie() {
        return responseCookieList;
    }

}

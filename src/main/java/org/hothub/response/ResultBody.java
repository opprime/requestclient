package org.hothub.response;

import okhttp3.*;

import java.io.*;
import java.util.List;

public class ResultBody {

    private Request request;
    private Response response;
    private ResponseBody responseBody;
    private List<Cookie> cookieList;


    public ResultBody(Request request, Response response, List<Cookie> cookieList) {
        this.request = request;
        this.response = response;
        this.responseBody = response != null ? response.body() : null;
        this.cookieList = cookieList;
    }


    @Override
    public String toString() {
        try {
            return responseBody != null ? responseBody.string() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    public byte[] toByte() {
        try {
            return responseBody != null ? responseBody.bytes() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File toFile(String fileDirectory, String fileName) {
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

            return file;
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

        return null;
    }



    public InputStream toStream() {
        return responseBody != null ? responseBody.byteStream() : null;
    }



    public Reader toReader() {
        return responseBody != null ? responseBody.charStream() : null;
    }



    public Request getRequest() {
        return request;
    }



    public Response getResponse() {
        return response;
    }



    public List<Cookie> getCookie() {
        return cookieList;
    }

}

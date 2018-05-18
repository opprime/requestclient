package org.hothub.response;

import okhttp3.*;
import org.hothub.utils.RequestClientUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

public class ResultBody {

    private Request request;
    private Response response;
    private ResponseBody responseBody;
    private List<Cookie> cookieList;

    private ResultHolderData resultHolderData;



    public ResultBody(Request request, Response response, List<Cookie> cookieList) {
        this.request = request;
        this.response = response;
        this.responseBody = response != null ? response.body() : null;
        this.cookieList = cookieList;

        resultHolderData = new ResultHolderData();
    }


    @Override
    public String toString() {
        String result = null;

        if (RequestClientUtils.isEmpty(resultHolderData.getString())) {
            try {
                result = responseBody != null ? responseBody.string() : null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            resultHolderData.setString(result);
        }

        return resultHolderData.getString();
    }



    public byte[] toByte() {
        byte[] result = null;

        if (resultHolderData.getBytes() == null) {
            try {
                result = responseBody != null ? responseBody.bytes() : null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            resultHolderData.setBytes(result);
        }

        return resultHolderData.getBytes();
    }



    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File toFile(String fileDirectory, String fileName) {
        File result = null;

        if (resultHolderData.getFile() == null) {
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

            resultHolderData.setFile(result);
        }

        return resultHolderData.getFile();
    }



    public InputStream toStream() {
        InputStream result;

        if (resultHolderData.getStream() == null) {
            result = responseBody != null ? responseBody.byteStream() : null;

            resultHolderData.setStream(result);
        }

        return resultHolderData.getStream();
    }



    public Reader toReader() {
        Reader result;

        if (resultHolderData.getReader() == null) {
            result = responseBody != null ? responseBody.charStream() : null;

            resultHolderData.setReader(result);
        }

        return resultHolderData.getReader();
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

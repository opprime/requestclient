package org.hothub.requestclient.response;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.hothub.requestclient.cookie.CookieHolder;
import org.hothub.requestclient.utils.RequestClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ResultBody {

    private final static Logger logger = LoggerFactory.getLogger(ResultBody.class);

    private final boolean success;
    private final Request request;
    private List<Cookie> requestCookieList;
    private List<Cookie> responseCookieList;

    private Buffer buffer;
    private String string;


    public ResultBody(Request request, Response response, CookieHolder cookieHolder) {
        this.request = request;

        this.success = response != null && response.isSuccessful();

        if (response != null && response.body() != null) {
            try {
                BufferedSource source = response.body().source();
                source.request(Long.MAX_VALUE);
                this.buffer = source.getBuffer().clone();
            } catch (IOException e) {
                logger.error("org.hothub.requestclient, response.ResultBody error", e);
            }
        }

        if (cookieHolder != null) {
            this.requestCookieList = cookieHolder.getRequestCookies();
            this.responseCookieList = cookieHolder.getResponseCookies();
        }
    }



    @Override
    public String toString() {
        if (!RequestClientUtils.isEmpty(this.string)) {
            return this.string;
        }

        if (this.buffer == null) {
            return null;
        }

        this.string = this.buffer.clone().readString(StandardCharsets.UTF_8);

        return this.string;
    }



    public byte[] toByte() {
        if (this.buffer == null) {
            return null;
        }

        return this.buffer.readByteArray();
    }



    public File toFile(String directory, String fileName) {
        if (this.buffer == null) {
            return null;
        }

        if (RequestClientUtils.isEmpty(directory) || RequestClientUtils.isEmpty(fileName)) {
            return null;
        }

        Path path = Paths.get(directory, fileName);

        try (InputStream inputStream = this.buffer.inputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            return path.toFile();
        } catch (IOException e) {
            logger.error("org.hothub.requestclient, response.ResultBody.toFile error", e);
        }

        return null;
    }



    public InputStream toStream() {
        if (this.buffer == null) {
            return null;
        }

        return this.buffer.inputStream();
    }



    public Reader toReader() {
        if (this.buffer == null) {
            return null;
        }

        return new InputStreamReader(this.buffer.inputStream());
    }



    public Request getRequest() {
        return request;
    }

    public boolean isSuccess() {
        return success;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public List<Cookie> getRequestCookie() {
        return requestCookieList;
    }

    public List<Cookie> getResponseCookie() {
        return responseCookieList;
    }

}

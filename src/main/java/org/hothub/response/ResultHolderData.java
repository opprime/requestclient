package org.hothub.response;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.Arrays;

class ResultHolderData implements Serializable {

    private String string;
    private byte[] bytes;
    private File file;
    private InputStream stream;
    private Reader reader;


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }


    @Override
    public String toString() {
        return "ResultHolderData{" +
                "string='" + string + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                ", file=" + file +
                ", stream=" + stream +
                ", reader=" + reader +
                '}';
    }
}

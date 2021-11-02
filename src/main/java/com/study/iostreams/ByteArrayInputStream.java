package com.study.iostreams;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {
    private final String STREAM_CLOSED = "Stream is dead";
    private byte[] array;
    private int index;
    private boolean closed;

    public ByteArrayInputStream(byte[] array) {
        this.array = array;
    }

    @Override
    public int read() throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        return available() > 0 ? array[index++] : -1;
    }

    public int read(byte[] store) throws IOException {
        return read(store, 0, store.length);
    }

    public int read(byte[] store, int offset, int length) throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        int size = Math.min(length, array.length - index);
        if (available() > 0) {
            System.arraycopy(array, index, store, offset, size);
        }
        index += size;
        return size;
    }

    public int available() {
        return array.length - index;
    }

    public void close() {
        array = null;
        index = 0;
        closed = true;
    }
}

package com.study.iostreams;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream  {
    private final String STREAM_CLOSED = "Stream is dead";
    private final static int BUFFER_SIZE = 8192; // 8kb
    private byte[] buffer;
    private int index;
    private int bufferSize;
    private final InputStream inputStream;
    private boolean closed;

    public BufferedInputStream(InputStream inputStream) {
        this(inputStream, BUFFER_SIZE);
    }

    public BufferedInputStream(InputStream inputStream, int size) {
        buffer = new byte[size];
        this.inputStream = inputStream;
    }

    @Override
    public int read(byte[] store, int offset, int length) throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        checkBuffer();
        int remains = bufferSize - index;
        if (length > remains) {
            System.arraycopy(buffer, index, store, offset, remains);
            return remains + inputStream.read(store, offset + remains, length - remains);
        }
        System.arraycopy(buffer, index, store, offset, length);
        return length;
    }

    @Override
    public int read(byte[] store) throws IOException {
        return read(store, 0, store.length);
    }

    @Override
    public int read() throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        checkBuffer();
        return available() > 0 ? buffer[index++] : -1;
    }

    private void checkBuffer() throws IOException {
        if (bufferSize == 0 || index == bufferSize) {
            bufferSize = inputStream.read(buffer, 0, bufferSize);
            index = 0;
        }
    }

    @Override
    public int available() throws IOException {
        return inputStream.available() + bufferSize - index;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        buffer = null;
        closed = true;
    }
}

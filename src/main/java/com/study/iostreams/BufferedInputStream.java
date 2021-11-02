package com.study.iostreams;

import java.io.*;

public class BufferedInputStream extends InputStream  {
    private final static int BUFFER_SIZE = 8192; // 8kb
    private final byte[] buffer;
    private final InputStream inputStream;

    public BufferedInputStream(InputStream inputStream) {
        this(inputStream, BUFFER_SIZE);
    }

    public BufferedInputStream(InputStream inputStream, int size) {
        buffer = new byte[size];
        this.inputStream = inputStream;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return super.read(b);
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int available() throws IOException {
        return super.available();
    }
}

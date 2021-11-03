package com.study.iostreams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class BufferedOutputStream extends OutputStream {
    private final String STREAM_CLOSED = "Stream is dead";
    private final static int DEFAULT_BUFFER_SIZE = 10;
    private int customBufferSize;
    private byte[] buffer;
    private int index;
    private final OutputStream outputStream;
    private boolean closed;

    public BufferedOutputStream(OutputStream outputStream) {
        this(outputStream, DEFAULT_BUFFER_SIZE);
        this.customBufferSize = DEFAULT_BUFFER_SIZE;
        this.buffer = new byte[customBufferSize];
    }

    public BufferedOutputStream(OutputStream outputStream, int customBufferSize) {
        this.outputStream = outputStream;
        this.customBufferSize = customBufferSize;
        this.buffer = new byte[customBufferSize];
    }

    @Override
    public void write(int value) throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        if (index > buffer.length) {
            flush();
        }
        buffer[index++] = (byte) value;
    }

    @Override
    public void write(byte[] store) throws IOException {
        write(store, 0, store.length);
    }

    @Override
    public void write(byte[] store, int offset, int length) throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        int remains = buffer.length - index;
        if (length > remains) {
            flush();
            outputStream.write(store, offset, length);
        } else {
            System.arraycopy(store, offset, buffer, index, length);
            index += length;
        }
    }

    @Override
    public void flush() throws IOException {
        outputStream.write(Arrays.copyOf(buffer, index));
        index = 0;
        buffer = new byte[customBufferSize];
    }

    @Override
    public void close() throws IOException {
        flush();
        outputStream.flush();
        outputStream.close();
        buffer = null;
        closed = true;
    }
}

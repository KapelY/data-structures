package com.study.iostreams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {
    private static final int DEFAULT_BUFFER_SIZE = 10;
    private final String STREAM_CLOSED = "Stream is dead";
    private byte[] array;
    private int index;
    private boolean closed;

    public ByteArrayOutputStream() {
        this(DEFAULT_BUFFER_SIZE);
    }

    public ByteArrayOutputStream(int bufferSize) {
        this.array = new byte[bufferSize];
    }

    @Override
    public void write(int value) throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        checkCapacity(index + 1);
        array[index++] = (byte) value;
    }

    public void write(byte[] store) throws IOException {
        write(store, 0, store.length);
    }

    public void write(byte[] store, int offset, int length) throws IOException {
        if (closed) {
            throw new IOException(STREAM_CLOSED);
        }
        checkCapacity(index + length);
        System.arraycopy(store, offset, array, index, length);
        index += length;
    }

    public void flush() {
        index = 0;
    }

    public void close() {
        array = null;
        index = 0;
        closed = true;
    }

    private void checkCapacity(int size) {
        if (size > array.length) {
            byte[] increased = new byte[(array.length + size) * 2];
            System.arraycopy(array, 0, increased, 0, array.length);
            array = increased;
        }
    }

    public byte[] getArray() {
        return Arrays.copyOf(array, index);
    }
}

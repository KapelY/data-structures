package com.study.iostreams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BufferedInputStreamTest {
    private final String STREAM_CLOSED = "Stream is dead";
    private final ByteArrayInputStream emptyStream = new ByteArrayInputStream(new byte[0]);
    private final ByteArrayInputStream oneElemStream = new ByteArrayInputStream(new byte[]{1});
    private final ByteArrayInputStream twoElemStream = new ByteArrayInputStream(new byte[]{1, 2});
    private final ByteArrayInputStream threeElemStream = new ByteArrayInputStream(new byte[]{1, 2, 3});
    private final ByteArrayInputStream fiveElemStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5});

    @Test
    @DisplayName("When call read on closed stream IOException is thrown")
    void whenCallReadOnClosedStreamIoExceptionIsThrown() throws IOException {
        InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(new byte[]{}));
        inputStream.close();
        Throwable exception = assertThrows(IOException.class, inputStream::read);
        assertEquals(exception.getMessage(), STREAM_CLOSED);
    }

    @Test
    @DisplayName("When call available on empty stream, stream with 1 elem, 2 elem, 3 elem")
    void whenCallAvailableOnEmptyStreamStreamWith1Elem2Elem3Elem() {
        assertEquals(0, emptyStream.available());
        assertEquals(1, oneElemStream.available());
        assertEquals(2, twoElemStream.available());
        assertEquals(3, threeElemStream.available());
    }

    @Test
    @DisplayName("When call read correct data returned")
    void whenCallReadCorrectDataReturned() throws IOException {
        assertEquals(1, twoElemStream.read());
        assertEquals(2, twoElemStream.read());
    }

    @Test
    @DisplayName("When call read on empty array -1 returns")
    void whenCallReadOnEmptyArray1Returns() throws IOException {
        assertEquals(-1, emptyStream.read());
        assertEquals(1, twoElemStream.read());
        assertEquals(2, twoElemStream.read());
        assertEquals(-1, twoElemStream.read());
    }

    @Test
    @DisplayName("When read byte[] then all bytes returned")
    void whenReadByteThenAllBytesReturns() throws IOException {
        byte[] array = new byte[10];
        assertEquals(2, twoElemStream.read(array));
        assertEquals(3, threeElemStream.read(array));

        assertEquals(1, fiveElemStream.read());
        assertEquals(2, fiveElemStream.read());
        assertEquals(3, fiveElemStream.read());
        assertEquals(2, fiveElemStream.read(array));
        assertEquals(4, array[0]);
        assertEquals(5, array[1]);
    }

    @Test
    @DisplayName("When read byte[] with offset and length then correct bytes returned")
    void whenReadBytes() throws IOException {
        byte[] array = new byte[11];
        assertEquals(2, twoElemStream.read(array, 0, 2));
        assertEquals(3, threeElemStream.read(array, 2, 3));

        assertEquals(5, fiveElemStream.read(array, 5, 10));

        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);
        assertEquals(3, array[4]);
        assertEquals(1, array[5]);
        assertEquals(2, array[6]);
        assertEquals(3, array[7]);
        assertEquals(4, array[8]);
        assertEquals(5, array[9]);
        assertEquals(0, array[10]);
    }
}
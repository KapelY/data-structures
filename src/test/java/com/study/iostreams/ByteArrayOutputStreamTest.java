package com.study.iostreams;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayOutputStreamTest {
    private final String STREAM_CLOSED = "Stream is dead";
    private final byte[] emptyArray = new byte[]{};
    private final byte[] oneArray = new byte[]{1};
    private final byte[] twoArray = new byte[]{1, 2};
    private final byte[] threeArray = new byte[]{1, 2, 3};
    private byte[] fiveArray = new byte[]{1, 2, 3, 4, 5};


    @Test
    @DisplayName("When call write on closed stream IOException is thrown")
    void whenCallWriteOnClosedStreamIoExceptionIsThrown() throws IOException {
        OutputStream os = new ByteArrayOutputStream();
        os.close();
        Throwable exception = assertThrows(IOException.class, () -> os.write(0));
        assertEquals(exception.getMessage(), STREAM_CLOSED);
    }

    @Test
    @Disabled
    @DisplayName("When call write correct data returned")
    void whenWrite() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(1);
        assertEquals(1, os.getArray()[0]);
        assertEquals(0, os.getArray()[1]);
        os.write(2);
        assertEquals(2, os.getArray()[1]);
    }

    @Test
    @Disabled
    @DisplayName("When write byte[] then all bytes are in array")
    void whenWrite1() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(fiveArray);
        assertArrayEquals(fiveArray, os.getArray());
    }

    @Test
    @Disabled
    @DisplayName("When write byte[] with offset and length then correct bytes are in array")
    void whenReadBytes() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(fiveArray, 2, 3);
        fiveArray = new byte[]{3, 4, 5};
        assertArrayEquals(fiveArray, os.getArray());
    }
}
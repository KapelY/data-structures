package com.study.iostreams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class BufferedOutputStreamTest {
    private final String STREAM_CLOSED = "Stream is dead";
    private byte[] fiveArray = new byte[]{1, 2, 3, 4, 5};


    @Test
    @DisplayName("When call write on closed stream IOException is thrown")
    void whenCallWriteOnClosedStreamIoExceptionIsThrown() throws IOException {
        OutputStream os = new BufferedOutputStream(new ByteArrayOutputStream());
        os.close();
        Throwable exception = assertThrows(IOException.class, () -> os.write(0));
        assertEquals(exception.getMessage(), STREAM_CLOSED);
    }

    @Test
    @DisplayName("When call write correct data returned")
    void whenWrite() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStream os = new BufferedOutputStream(outputStream);
        os.write(fiveArray);
        os.flush();
        assertArrayEquals(fiveArray, outputStream.getArray());
        os.close();
    }

    @Test
    @DisplayName("When write byte[] with offset and length then correct bytes are in array")
    void whenReadBytes() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStream os = new BufferedOutputStream(outputStream);
        os.write(fiveArray, 2, 3);
        os.flush();
        fiveArray = new byte[]{3, 4, 5};
        assertArrayEquals(fiveArray, outputStream.getArray());
        os.close();
    }

}
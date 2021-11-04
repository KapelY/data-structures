package com.study.webserver;

import lombok.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Data
public class Server {
    private static final String EXCEPTION_IN_STREAM = "Something went wrong in 'readContent(String path)')!";
    static byte[] array = new byte[999];
    int port;
    String webappPath;

    static int readContent(String path) {
        try (var bufferedReader = new FileInputStream(path)) {
            return bufferedReader.read(array);
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_IN_STREAM, e);
        }
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(" === Server start to work === ");

            for (; ; ) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     OutputStream writer = socket.getOutputStream()) {

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while (!(line = reader.readLine()).isEmpty()) {
                        sb.append(line).append(System.getProperty("line.separator"));
                    }
                    String uri = sb.toString().split(" ")[1];
                    String path = webappPath + uri;
                    System.out.println("Ready to write response " + path);

                    if (!new File(path).exists()) {
                        writeHeader(writer);
                        writer.write("Error 404 file not found".getBytes(StandardCharsets.UTF_8));
                        writer.flush();
                    } else {
                        int count = readContent(path);

                        writeHeader(writer);
                        writer.write(array, 0, count);
                    }
                    writer.flush();
                    System.out.println("written");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHeader(OutputStream writer) throws IOException {
        writer.write("HTTP/1.1 200 OK".getBytes(StandardCharsets.UTF_8));
        writer.write(System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8));
        writer.write(System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8));
    }
}

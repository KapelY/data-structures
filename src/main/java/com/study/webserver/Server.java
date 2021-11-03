package com.study.webserver;

import lombok.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Data
public class Server {
    private static final String EXCEPTION_IN_STREAM = "Something went wrong in 'readContent(String path)')!";
    int port;
    String webappPath;
    static char[] array = new char[999];

    static int readContent(String path) {
        try (var bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            return bufferedReader.read(array);
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_IN_STREAM, e);
        }
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(" === Server start to work === ");

                for (;;) {
                    try (Socket socket = serverSocket.accept();
                         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                        String uri;

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while (!(line = reader.readLine()).isEmpty()) {
                        sb.append(line).append(System.getProperty("line.separator"));
                    }
                    System.out.println(sb);
                    uri = sb.toString().split(" ")[1];
                    String path = webappPath + uri;
                    System.out.println("Ready to write response " + path);

                    int count = readContent(path);
                    System.out.println(array);

                    writer.write("HTTP/1.1 200 OK");
                    writer.newLine();
                    writer.newLine();
                    writer.write(array, 0, count);
                    writer.flush();
                    System.out.println("written");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

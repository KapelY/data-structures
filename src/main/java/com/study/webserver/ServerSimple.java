package com.study.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerSimple {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3000)) {
            System.out.println(" === Server start to work === ");
            try (Socket socket = serverSocket.accept();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                String line;
                while (!(line = reader.readLine()).isEmpty()) {
                    System.out.println(line);
                }

                System.out.println("Ready to write response");

                writer.write("HTTP/1.1 200 OK");
                writer.newLine();
                writer.newLine();
                writer.write("Hello browser");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

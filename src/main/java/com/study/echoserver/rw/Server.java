package com.study.echoserver.rw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3000)) {
            System.out.println("Server started");
            Socket socket = serverSocket.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            int count;
            char[] chars ;
            do {
                chars = new char[1028];
                count = reader.read(chars);
                writer.write("Echo: " + new String(chars, 0, count));
                writer.flush();
            } while (!Arrays.toString(chars).contains("q"));
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

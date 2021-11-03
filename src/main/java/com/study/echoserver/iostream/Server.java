package com.study.echoserver.iostream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3000)) {
            System.out.println("Server started");
            Socket socket = serverSocket.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String echo;
            while (!((echo = reader.readLine()).isEmpty())) {
                writer.write("Echo: " + echo);
                writer.newLine();
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

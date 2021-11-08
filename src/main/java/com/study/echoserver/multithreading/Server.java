package com.study.echoserver.multithreading;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3000)) {
            System.out.println("Server started main Thread name: " + Thread.currentThread().getName());
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                        System.out.println("Server accepted connection in Thread name: " + Thread.currentThread().getName());
                        String echo;
                        do {
                            echo = reader.readLine();
                            writer.write("Echo: " + echo);
                            writer.newLine();
                            writer.flush();
                        } while (!echo.contains("bye"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

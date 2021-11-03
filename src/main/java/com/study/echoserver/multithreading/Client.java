package com.study.echoserver.multithreading;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 3000)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Print message: ");
            String message;
            do {
                message = keyboard.readLine();
                writer.write(message);
                writer.newLine();
                writer.flush();
                System.out.println(reader.readLine());
            } while (!message.contains("bye"));
            reader.close();
            writer.close();
            keyboard.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

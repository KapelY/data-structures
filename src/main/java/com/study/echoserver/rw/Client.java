package com.study.echoserver.rw;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 3000)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) {
            };
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Print 8 chars: ");
            char[] chars;
            int countInput;
            int countFromServer;
            do {
                chars = new char[1028];
                countInput = keyboard.read(chars);
                writer.write(chars,0, countInput);
                writer.flush();
                countFromServer = reader.read(chars);
                System.out.print(new String(chars,0, countFromServer));
            } while (!Arrays.toString(chars).contains("q"));
            writer.close();
            reader.close();
            keyboard.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

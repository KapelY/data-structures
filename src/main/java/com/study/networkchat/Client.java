package com.study.networkchat;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public Client(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            close();
        }
    }

    public void start() {
        messageListener();
        massageSender();
    }

    private void massageSender() {
        try {
            writeMessage(userName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (socket.isConnected()) {
                String messageToSend = reader.readLine();
                bufferedWriter.write(userName + ": " + messageToSend);
                bufferedWriter.write("\r\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    private void messageListener() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    System.out.println(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                }
            }
        }).start();
    }

    private void writeMessage(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();
    }

    private void close() {
        try {
            bufferedWriter.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name;
        System.out.println("You nick please: ");
        while ((name = reader.readLine()).isBlank() || name.isEmpty()) {
            System.out.println("Invalid name, try again!!! ");
        }
        Client client = new Client(new Socket("127.0.0.1", 3000), name);
        client.start();
    }

}

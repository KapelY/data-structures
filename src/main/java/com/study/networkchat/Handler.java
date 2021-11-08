package com.study.networkchat;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Handler extends Thread {
    static Set<Handler> clients = new HashSet<>();
    Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            userName = bufferedReader.readLine();
            clients.add(this);
            sendMessageToAll("Server: " + userName + " joined the room!");

            while (socket.isConnected()) {
                sendMessageToAll(bufferedReader.readLine());
            }
        } catch (IOException e) {
            clients.remove(this);
            sendMessageToAll("Server: " + userName + " has left this chat!");
            close();
        }
    }

    public void sendMessageToAll(String message) {
        for (Handler client : clients) {
            if (!client.userName.equals(userName)) {
                try {
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.write(System.lineSeparator());
                    client.bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                }

            }
        }
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
}

package com.study.networkchat;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class Client {
    static final String EXIT = "exit";
    private static final String ENTER_NAME = "Your name please: ";
    private static final String INVALID_NAME = "Invalid name, try again!!! ";
    private final Socket socket;
    private final String userName;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            log.error("Error in constructor Client.", e);
            close();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name;
        System.out.println(ENTER_NAME);
        while ((name = reader.readLine()).isBlank() || name.isEmpty()) {
            System.out.println(INVALID_NAME);
        }
        Client client = new Client(new Socket("127.0.0.1", 3000), name);
        client.start();
    }

    public void start() {
        new Thread(messageListener()).start();
        massageSender();
    }

    private void massageSender() {
        try {
            sendMessage(userName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String messageToSend;
            while (!(messageToSend = reader.readLine()).contains(EXIT)) {
                sendMessage(messageToSend);
            }
        } catch (IOException e) {
            log.error("Error in send message Client.", e);
        } finally {
            try {
                sendMessage(EXIT);
            } catch (IOException e) {
                log.error("Error in finally send message Client.", e);
            }
        }
    }

    private Runnable messageListener() {
        return () -> {
            String message = "";
            while (!Objects.equals(message, Handler.BYE_FROM_SERVER)) {
                try {
                    message = bufferedReader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    log.info("In message listener error on readline client class", e);
                }
            }
            close();
        };
    }

    private void sendMessage(String messageToSend) throws IOException {
        bufferedWriter.write(userName + ": " + messageToSend);
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Error in close socket Client.", e);
        }
    }
}

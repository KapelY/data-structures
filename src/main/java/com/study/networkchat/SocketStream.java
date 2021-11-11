package com.study.networkchat;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Data
@Slf4j
public class SocketStream {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String userName = "";

    public SocketStream(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Error in SocketStream close method.", e);
        }
    }
}

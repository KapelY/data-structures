package com.study.networkchat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server {
    private static final String SERVER_STARTED = "----====SERVER STARTED====----";
    private static final String NEW_CLIENT_CONNECTED = "New client connected";
    private final int port;
    private final SocketPool socketPool;

    public Server(int port) {
        this.port = port;
        socketPool = new SocketPool();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            startHandler();
            log.info(SERVER_STARTED);

            while (true) {
                Socket socket = serverSocket.accept();
                log.info(NEW_CLIENT_CONNECTED);
                socketPool.addSocket(socket);
            }
        } catch (IOException e) {
            log.error("Error in server start method. ", e);
        }
    }

    private void startHandler() {
        Thread handler = new Thread(new Handler(socketPool));
        handler.start();
    }

    public static void main(String[] args) {
        Server server = new Server(3000);
        server.startServer();
    }
}

package com.study.networkchat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

@Slf4j
public class Handler implements Runnable {
    static final String BYE_FROM_SERVER = "SERVER: Bye see you!";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final SocketPool socketPool;

    public Handler(SocketPool socketPool) {
        this.socketPool = socketPool;
    }

    @Override
    public void run() {
        Iterator<SocketStream> iterator;
        SocketStream socketStream;

        while (true) {
            iterator = socketPool.iterator();
            while (iterator.hasNext()) {
                socketStream = iterator.next();
                try {
                    if (socketStream.getReader().ready()) {
                        String message = socketStream.getReader().readLine();

                        if (socketStream.getUserName().isEmpty()) {
                            socketStream.setUserName(message);
                            System.out.println(message);
                            sendMessageToAll(socketStream, "Server: " + message + " joined the room!");
                            continue;
                        }

                        if (message.toLowerCase().contains(Client.EXIT)) {
                            sendMessageToAll(socketStream,
                                    "Server: " + socketStream.getUserName() + " has left the room!");
                            log.info(socketStream.getUserName() + " has left the room!");
                            sendBye(socketStream);
                            socketStream.getSocket().close();
                            iterator.remove();
                            continue;
                        }
                        sendMessageToAll(socketStream, message);
                    }
                } catch (IOException e) {
                    log.error("Error in handler run method. ", e);
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToAll(SocketStream userData, String message) {
        for (SocketStream socketStream : socketPool) {
            if (!socketStream.getUserName().equals(userData.getUserName())) {
                try {
                    socketStream.getWriter().write(message);
                    socketStream.getWriter().write("\r\n");
                    socketStream.getWriter().flush();
                } catch (IOException e) {
                    log.error("Error in send message handler. ", e);
                }
            }
        }
    }

    public void sendBye(SocketStream userData) {
        try {
            userData.getWriter().write(BYE_FROM_SERVER);
            userData.getWriter().write("\r\n");
            userData.getWriter().flush();
        } catch (IOException e) {
            log.error("Error in send message handler. ", e);
        }
    }
}

package com.study.networkchat;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SocketPool implements Iterable<SocketStream> {
    private final Queue<SocketStream> sockets;

    public SocketPool() {
        this.sockets = new ConcurrentLinkedQueue<>();
    }

    public void  addSocket(Socket socket) throws IOException {
        sockets.add(new SocketStream(socket));
    }

    @Override
    public Iterator<SocketStream> iterator() {
        return sockets.iterator();
    }
}

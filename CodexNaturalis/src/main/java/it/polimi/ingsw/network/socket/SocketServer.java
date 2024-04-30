package it.polimi.ingsw.network.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    final BufferedReader input;
    final ServerProxy server;

    protected SocketServer(BufferedReader input, BufferedWriter output) {
        this.input = input;
        this.server = new ServerProxy(output);
    }
/*
    public static void main(String[] args) throws IOException {
        //its not necessary to use the localHost, because the serverSocket is only bound to the port
        //String host = args[0];
        //int port = Integer.parseInt(args[1]);

        ServerSocket listenSocket = new ServerSocket(1234);

        new SocketServer(listenSocket, new AdderController(1)).runServer();
    }
    */
}

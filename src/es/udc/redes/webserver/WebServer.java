package es.udc.redes.webserver;

import java.net.*;
import java.io.*;

public class WebServer {

    public static void main(String[] argv) {
        int port =  Integer.parseInt(argv[0]);
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from client");

                // Create a new thread for each client connection
                ServerThread thread = new ServerThread(clientSocket);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Error while listening on port " + port + ": " + e.getMessage());
        } finally {
            // Close the server socket
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error while closing server socket: " + e.getMessage());
            }
        }
    }
}

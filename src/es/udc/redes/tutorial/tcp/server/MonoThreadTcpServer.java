package es.udc.redes.tutorial.tcp.server;

import java.net.*;
import java.io.*;

/**
 * MonoThread TCP echo server.
 */
public class MonoThreadTcpServer {

    public static void main(String argv[]) {
        ServerSocket serverSocket = null;
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.MonoThreadTcpServer <port>");
            System.exit(-1);
        }
        try {
            // Create a server socket
            serverSocket = new ServerSocket(Integer.parseInt(argv[0]));

            // Set a timeout of 300 secs
            serverSocket.setSoTimeout(300000);

            while (true) {
                // Wait for connections
                Socket clientSocket = serverSocket.accept();

                // Set the input channel
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Set the output channel
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Receive the client message
                String clientMessage = in.readLine();
                System.out.println("SERVER: Received " + clientMessage + " from " + clientSocket.getRemoteSocketAddress());

                // Send response to the client
                out.println(clientMessage);
                System.out.println("SERVER: Sending " + clientMessage + " to " + clientSocket.getRemoteSocketAddress());

                // Close the streams
                in.close();
                out.close();

                // Close the socket
                clientSocket.close();
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the server socket
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing the server socket: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}



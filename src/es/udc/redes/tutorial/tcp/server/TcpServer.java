package es.udc.redes.tutorial.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** Multithread TCP echo server. */

public class TcpServer {

  public static void main(String argv[]) {
    if (argv.length != 1) {
      System.err.println("Format: es.udc.redes.tutorial.tcp.server.TcpServer <port>");
      System.exit(-1);
    }
    ServerSocket serverSocket = null;
    try {
      // Create a server socket
      serverSocket = new ServerSocket(Integer.parseInt(argv[0]));
      // Set a timeout of 300 secs
      serverSocket.setSoTimeout(300000);
      while (true) {
        // Wait for connections
        System.out.println("Waiting for connection...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Accepted connection from " + clientSocket.getInetAddress());
        // Create a ServerThread object, with the new connection as parameter
        ServerThread serverThread = new ServerThread(clientSocket);
        // Initiate thread using the start() method
        serverThread.start();
      }
    } catch (IOException e) {
      System.err.println("Error creating or accepting socket: " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      //Close the socket
      try {
        if (serverSocket != null) {
          serverSocket.close();
        }
      } catch (IOException e) {
        System.err.println("Error closing the server socket: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }
}

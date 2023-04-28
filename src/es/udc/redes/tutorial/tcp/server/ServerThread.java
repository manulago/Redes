package es.udc.redes.tutorial.tcp.server;
import java.net.*;
import java.io.*;

/** Thread that processes an echo server connection. */

public class ServerThread extends Thread {

  private Socket socket;

  public ServerThread(Socket s) {
    // Store the socket s
  }

  public void run() {
    try {
      // Set the input channel
      // Set the output channel 
      // Receive the message from the client
      // Sent the echo message to the client
      // Close the streams
    // Uncomment next catch clause after implementing the logic
    // } catch (SocketTimeoutException e) {
    //  System.err.println("Nothing received in 300 secs");
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      } finally {
	// Close the socket
    }
  }
}

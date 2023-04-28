package es.udc.redes.tutorial.udp.server;

import java.net.*;

/**
 * Implements a UDP echo server.
 */
public class UdpServer {

    public static void main(String argv[]) {
        DatagramSocket ds = null;
        int port;

        DatagramPacket dp;

        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.udp.server.UdpServer <port_number>");
            System.exit(-1);
        }
        try {
            port = Integer.parseInt(argv[0]);
            ds = new DatagramSocket(port);
            ds.setSoTimeout(300000);
            while (true) {
                // Prepare datagram for reception
                dp = new DatagramPacket(new byte[1024], 1024);

                // Receive the message
                ds.receive(dp);
                System.out.println(new String(dp.getData(),0,dp.getLength()));
                // Prepare datagram to send response
                System.out.println("SERVER: Received <<" + (new String(dp.getData(), 0, dp.getLength())) + ">> from"
                        + dp.getAddress() + ":" + argv[0]);


                // Send response
                ds.send(dp);
            }

            // Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
            System.err.println("No requests received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ds != null) {
                // Close the socket
                ds.close();
            }
        }
    }
}
package es.udc.redes.webserver;

import java.net.*;
import java.io.*;
import java.util.Date;

public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket s) {
        // Store the socket s
        this.socket = s;
    }

    public void run() {
        try {
            // Set the input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();

            // Get the request line
            String requestLine = in.readLine();

            // Split the request line into parts
            String[] requestParts = requestLine.split(" ");

            // Get the method and path from the request line
            String method = requestParts[0];
            String path = requestParts[1];

            // Check if the request method is GET or HEAD
            if (method.equals("GET") || method.equals("HEAD")) {
                String absolutePath = new File(".").getAbsolutePath();
                File file = new File(absolutePath + "/p1-files" + path);
                // Check if the requested resource exists
                if (file.exists()) {

                    // Check if the resource has been modified since the If-Modified-Since date
                    String ifModifiedSince = null;
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.startsWith("If-Modified-Since:")) {
                            ifModifiedSince = line.substring(20);
                        }
                        if (line.equals("")) {
                            break;
                        }
                    }

                    Date lastModified = new Date(file.lastModified());
                    if (ifModifiedSince != null && lastModified.compareTo(new Date(ifModifiedSince)) <= 0) {
                        // The resource has not been modified since the If-Modified-Since date
                        out.write("HTTP/1.0 304 Not Modified\r\n".getBytes());
                        out.write(("Date: " + new Date() + "\r\n").getBytes());
                        out.write("Server: ManuLago\r\n".getBytes());
                        out.write("\r\n".getBytes());

                    } else {
                        // The resource has been modified or no If-Modified-Since header was provided
                        String contentType = getContentType(path);
                        byte[] content = getContent(file);
                        out.write("HTTP/1.0 200 OK\r\n".getBytes());
                        out.write(("Date: " + new Date() + "\r\n").getBytes());
                        out.write("Server: ManuLago\r\n".getBytes());
                        out.write(("Content-Length: " + content.length + "\r\n").getBytes());
                        out.write(("Content-Type: " + contentType + "\r\n").getBytes());
                        out.write(("Last-Modified: " + lastModified + "\r\n").getBytes());
                        out.write("\r\n".getBytes());
                        if (method.equals("GET")) {
                            out.write(content);
                        }

                    }

                } else {
                    // The requested resource does not exist
                    out.write("HTTP/1.0 404 Not Found\r\n".getBytes());
                    out.write(("Date: " + new Date() + "\r\n").getBytes());
                    out.write(("Server: ManuLago\r\n").getBytes());
                    out.write("\r\n".getBytes());
                }

            } else {
                // The request method is not GET or HEAD
                out.write("HTTP/1.0 400 Bad Request\r\n".getBytes());
                out.write(("Date: " + new Date() + "\r\n").getBytes());
                out.write(("Server: ManuLago\r\n").getBytes());
                out.write("\r\n".getBytes());

            }

        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in in 300 secs");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Close the client socket
            try {
                this.socket.close();
            } catch (IOException e) {
                System.err.println("Error while closing client socket: " + e.getMessage());
            }
        }
    }
    private String getContentType(String path) {
        if (path.endsWith(".html") || path.endsWith(".htm")) {
            return "text/html";
        } else if (path.endsWith(".txt") || path.endsWith(".java") || path.endsWith(".c") || path.endsWith(".h")) {
            return "text/plain";
        } else if (path.endsWith(".gif")) {
            return "image/gif";
        } else if (path.endsWith(".class")) {
            return "application/octet-stream";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (path.endsWith(".png")) {
            return "image/png";
        } else if (path.endsWith(".pdf")) {
            return "application/pdf";
        } else if (path.endsWith(".ps")) {
            return "application/postscript";
        } else {
            return "application/octet-stream";
        }
    }

    private byte[] getContent(File file) throws IOException {
        byte[] content = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(content);
        fis.close();
        return content;
    }


}


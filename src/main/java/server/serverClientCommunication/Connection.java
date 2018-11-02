package main.java.server.serverClientCommunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection {
    private DataOutputStream out;
    private BufferedReader in;
    private Socket socket;
    private final int PORT = 3003;  // port on the server... same as in Server class file, if you change, change both!

    /**
     * Create input and output stream with the socket.
     * @param socket socket.
     */
    public Connection(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Can not make the connection!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create new connection with the server on the given ip address.
     * @param ipAddress IP address of the server.
     */
    public Connection(String ipAddress) {
        try {
            socket = new Socket(ipAddress, PORT);
            out = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Can not make the connection!");
            System.out.println(e.getMessage());
        }
    }

    public DataOutputStream getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }
}

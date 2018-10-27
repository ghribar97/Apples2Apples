package main.java.server.serverClientCommunication;

import java.io.IOException;

public class Postman {
    public static boolean sendMessage(Connection conn, String msg) {
        try {
            conn.getOut().writeBytes(msg + "\n");
        } catch (IOException e) {
            System.out.println("Unable to send this message: " + msg + "!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static String receiveMessage(Connection conn) {
        try {
            return conn.getIn().readLine();
        } catch (IOException e) {
            System.out.println("Unable to receive message!");
            System.out.println(e.getMessage());
            return "";
        }
    }
}

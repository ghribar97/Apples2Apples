package main.java.server;

import main.java.players.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static Server instance;

    private final int port = 3003;  // port on the server... same as in Connection class file, if you change, change both!
    private ArrayList<Player> players;  // List of players that are connected to the server.
    private ServerSocket socket;

    private Server() throws IOException  {
        players = new ArrayList<Player>();
        socket = new ServerSocket(port);  // Initialize the socket for upcoming connections.
    }

    /**
     * Return the instance of Server singleton class.
     * @return Server object
     */
    public static Server getInstance() {
        if (instance == null)
            try {
                instance = new Server();
            } catch (IOException e) {
                System.out.println("Can not start the server!");
                System.out.println(e.getMessage());
                System.exit(1);
            }
        return instance;
    }

    /**
     * Wait for online players to join the game, and add them in the list as the Player object.
     * @param numberOfOnlinePlayers - Online players that wants to join the game.
     */
    public void waitForOnlinePlayers(int numberOfOnlinePlayers) {
        for(int onlineClient=0; onlineClient<numberOfOnlinePlayers; onlineClient++) {
            Socket connectionSocket;
            try {
                connectionSocket = socket.accept();
            } catch (IOException e) {
                System.out.println("Can not accept connection from player with ID: " + numberOfOnlinePlayers + "!");
                System.out.println(e.getMessage());
                continue;
            }
            players.add(new Player(connectionSocket, onlineClient));
            System.out.println("Connected to " + "Player ID: " + (onlineClient));
        }
    }

}

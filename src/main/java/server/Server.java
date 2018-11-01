package main.java.server;

import main.java.players.ServerPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static Server instance;

    private final int PORT = 3003;  // port on the server... same as in Connection class file, if you change, change both!
    private final int MIN_PLAYERS = 4;
    private ArrayList<ServerPlayer> serverPlayers;  // List of serverPlayers that are connected to the server.
    private ServerSocket socket;

    private Server() throws IOException  {
        serverPlayers = new ArrayList<ServerPlayer>();
        socket = new ServerSocket(PORT);  // Initialize the socket for upcoming connections.
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
     * Wait for online serverPlayers to join the game, and add them in the list as the ServerPlayer object.
     * @param numberOfOnlinePlayers - Online serverPlayers that wants to join the game.
     */
    public void waitForOnlinePlayers(int numberOfOnlinePlayers) {
        System.out.println("waiting for players...");
        for(int onlineClient=0; onlineClient<numberOfOnlinePlayers; onlineClient++) {
            Socket connectionSocket;
            try {
                connectionSocket = socket.accept();
            } catch (IOException e) {
                System.out.println("Can not accept connection from player with ID: " + numberOfOnlinePlayers + "!");
                System.out.println(e.getMessage());
                continue;
            }
            serverPlayers.add(new ServerPlayer(connectionSocket, onlineClient));
            System.out.println("Connected to " + "ServerPlayer ID: " + (onlineClient));
        }
        addBotPlayers();
    }

    private void addBotPlayers(){
        for (int i=serverPlayers.size(); i<MIN_PLAYERS; i++) {
            serverPlayers.add(new ServerPlayer(i));
            System.out.println("Added bot with ID: " + i);
        }
    }

    public ArrayList<ServerPlayer> getPlayers() {
        return serverPlayers;
    }
}

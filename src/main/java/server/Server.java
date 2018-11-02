package main.java.server;

import main.java.players.client.notifier.Notifier;
import main.java.players.ServerPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static Server instance;

    private final int PORT = 3003;  // port on the server... same as in Connection class file, if you change, change both!
    private final int MIN_PLAYERS = 4;  // minimum required number of players to play the game
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
                Notifier.displayServerTrace("Can not start the server!");
                Notifier.displayServerTrace(e.getMessage());
                System.exit(1);
            }
        return instance;
    }

    /**
     * Wait for online serverPlayers to join the game, and add them in the list as the ServerPlayer object.
     * @param numberOfOnlinePlayers - Online serverPlayers that wants to join the game.
     */
    public void waitForOnlinePlayers(int numberOfOnlinePlayers) {
        Notifier.displayServerTrace("waiting for players...");
        for(int onlineClient=0; onlineClient<numberOfOnlinePlayers; onlineClient++) {
            Socket connectionSocket;
            try {
                connectionSocket = socket.accept();  // accept players request for connection
            } catch (IOException e) {
                Notifier.displayServerTrace("Can not accept connection from player with ID: " + numberOfOnlinePlayers + "!");
                Notifier.displayServerTrace(e.getMessage());
                continue;
            }
            serverPlayers.add(new ServerPlayer(connectionSocket, onlineClient));
            Notifier.displayServerTrace("Connected to " + "ServerPlayer ID: " + (onlineClient));
        }
        addBotPlayers();
    }

    /**
     * Add bots, so that the game can run with minimum number of players.
     */
    private void addBotPlayers(){
        for (int i=serverPlayers.size(); i<MIN_PLAYERS; i++) {
            serverPlayers.add(new ServerPlayer(i));
            Notifier.displayServerTrace("Added bot with ID: " + i);
        }
    }

    public ArrayList<ServerPlayer> getPlayers() {
        return serverPlayers;
    }

    /**
     * For testing purposes.
     * @param serverPlayers list of players (bots)
     */
    public void setServerPlayers(ArrayList<ServerPlayer> serverPlayers) {
        this.serverPlayers = serverPlayers;
    }
}

package main.java.players;

import main.java.cards.Card;
import main.java.cards.cardTypes.AppleCard;
import main.java.server.serverClientCommunication.Connection;
import main.java.server.serverClientCommunication.Postman;

import java.net.Socket;
import java.util.ArrayList;

public class Player {
    private Connection connection;
    private ArrayList<Card> hand;
    private ArrayList<AppleCard> greenApples;
    private boolean isBot;
    private boolean isNotReal;
    private int playerID;


    /**
     * Local player constructor.
     * @param ipAddress ip address of the server.
     */
    public Player(String ipAddress) {
        connection = new Connection(ipAddress);
        isBot = false;
        isNotReal = false;
        this.playerID = Integer.parseInt(Postman.receiveMessage(connection));
        initializeArrayLists();
    }

    /**
     * ServerPlayer constructor.
     * @param socket socket between server and player.
     * @param playerID id of the player.
     */
    public Player(Socket socket, int playerID) {
        connection = new Connection(socket);
        isBot = false;  // if it has a socket it is not bot...
        this.isNotReal = true;
        this.playerID = playerID;
        Postman.sendMessage(connection, playerID + "");  // sends id to a local player
        initializeArrayLists();
    }

    /**
     * Bot constructor.
     * @param playerID id of the player.
     */
    public Player(int playerID) {
        isBot = true;
        this.isNotReal = true;  // just to avoid possible problems.
        this.playerID = playerID;
        initializeArrayLists();
    }

    private void initializeArrayLists() {
        this.hand = new ArrayList<Card>();
        this.greenApples = new ArrayList<AppleCard>();
    }
}

package main.java.players;

import main.java.cards.Card;
import main.java.cards.cardTypes.AppleCard;
import main.java.errors.CardNotFoundException;
import main.java.server.serverClientCommunication.Connection;
import main.java.server.serverClientCommunication.Postman;

import java.net.Socket;
import java.util.ArrayList;

public class ServerPlayer {
    public final int MAX_NUM_OF_PLAYER_CARDS = 7;
    protected Connection connection;
    protected ArrayList<Card> hand;
    protected ArrayList<AppleCard> greenApples;
    protected int playerID;
    protected boolean judge;
    private boolean isBot;


    /**
     * ServerPlayer constructor.
     * @param socket socket between server and player.
     * @param playerID id of the player.
     */
    public ServerPlayer(Socket socket, int playerID) {
        connection = new Connection(socket);
        isBot = false;  // if it has a socket it is not bot...
        this.playerID = playerID;
        Postman.sendID(connection, playerID);  // sends id to a client player
        initializeArrayLists();
    }

    /**
     * Bot constructor.
     * @param playerID id of the player.
     */
    public ServerPlayer(int playerID) {
        isBot = true;
        this.playerID = playerID;
        initializeArrayLists();
    }

    /**
     * this constructor is for the needs of the client player.
     */
    protected ServerPlayer() {
        initializeArrayLists();
    }

    private void initializeArrayLists() {
        this.hand = new ArrayList<Card>();
        this.greenApples = new ArrayList<AppleCard>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public boolean isBot() {
        return isBot;
    }

    public Connection getConnection() {
        return connection;
    }

    public int getPlayerID() {
        return playerID;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<AppleCard> getGreenApples() {
        return greenApples;
    }

    public void setJudge(boolean judge) {
        this.judge = judge;
    }

    public void addGreenApple(AppleCard greenApple) {
        greenApples.add(greenApple);
    }

    public Card play() throws CardNotFoundException {
        int index = 0;  // default value of bot (it will always give first card away - simple bot)
        if (!isBot) {  // real player
            Card playedCard = Postman.receivePlayedCard(connection);  // wait for the player to pick a card
            for (int x = 0; x < hand.size(); x++) {  // get index of the played card from our hand
                if (playedCard.getText().equals(hand.get(x).getText()))
                    index = x;
            }
        }
        return hand.remove(index);  // remove the card from our hand
    }

    public int chooseWinningCard() {
        if (isBot)  // bot will always choose first card as the best one
            return 0;
        return Postman.receiveTheBestCard(connection);
    }

    @Override
    public String toString() {
        return isBot ? "Bot ID" + playerID : "Player ID" + playerID;
    }
}

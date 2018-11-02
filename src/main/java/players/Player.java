package main.java.players;

import main.java.cards.Card;
import main.java.cards.cardTypes.AppleCard;
import main.java.errors.CardNotFoundException;
import main.java.server.serverClientCommunication.Connection;

import java.util.ArrayList;

public abstract class Player {
    public static final int MAX_NUM_OF_PLAYER_CARDS = 7;  // how many cards player holds in hands
    protected Connection connection;
    protected ArrayList<Card> hand;
    protected ArrayList<AppleCard> greenApples;
    protected int playerID;
    protected boolean judge;
    protected boolean isBot;

    public void addCard(Card card) {
        hand.add(card);
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

    @Override
    public String toString() {
        return isBot ? "Bot ID" + playerID : "Player ID" + playerID;
    }

    /**
     * Here it is defined how player plays.
     * @return played Card
     * @throws CardNotFoundException if card cannot be created.
     */
    public abstract Card play() throws CardNotFoundException;


    /**
     * Here player chooses which is the winning card.
     * @return position of the card in the list.
     */
    public abstract int chooseWinningCard();
}

package main.java.players;

import main.java.cards.Card;
import main.java.cards.cardTypes.AppleCard;
import main.java.errors.CardNotFoundException;
import main.java.server.serverClientCommunication.Connection;
import main.java.server.serverClientCommunication.Postman;

import java.net.Socket;
import java.util.ArrayList;

public class ServerPlayer extends Player {
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

    private void initializeArrayLists() {
        this.hand = new ArrayList<Card>();
        this.greenApples = new ArrayList<AppleCard>();
    }

    /**
     * Check on which position in the hand the given card is located.
     * @param card card in which we are interested in
     * @return card position in the hand
     */
    private int getPositionOfTheCardInHand(Card card) {
        for (int x = 0; x < hand.size(); x++) {  // get index of the played card from our hand
            if (card.toString().equals(hand.get(x).toString()))  // if cards are the same
                return x;
        }
        return -1;  // card is not in the hand
    }

    public Card play() throws CardNotFoundException {
        /*
        Bot is simple, so it will always choose first card in the list for the winning one.
        If this is server player (Not a bot) it will get the information from the actual player.
         */
        return hand.remove(isBot ? 0 : getPositionOfTheCardInHand(Postman.receivePlayedCard(connection)));
    }

    public int chooseWinningCard() {
        /*
        Bot is simple, so it will always choose first card in the list for the winning one.
        If this is server player (Not a bot) it will get the information from the actual player.
         */
        return isBot ? 0 : Postman.receiveTheBestCard(connection);
    }
}

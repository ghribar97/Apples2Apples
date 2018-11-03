package main.java.gameLogic;

import main.java.cards.Card;
import main.java.decks.Deck;
import main.java.decks.DeckFactory;
import main.java.errors.DeckNotFoundException;
import main.java.players.ServerPlayer;
import main.java.players.client.notifier.Notifier;
import main.java.server.Server;
import main.java.server.serverClientCommunication.Postman;

import java.io.File;
import java.util.ArrayList;

public class Dealer {
    private static Dealer instance;

    public static String redApplesDeckLocation;
    public static String greenApplesDeckLocation;

    private Deck redCardsDeck;
    private Deck greenCardsDeck;
    private Server server;

    private Dealer() {
        server = Server.getInstance();
        try {
            String mainDir = System.getProperty("user.dir");  // home execution directory of the project
            redApplesDeckLocation = mainDir + File.separator + "redApples.txt";
            greenApplesDeckLocation = mainDir + File.separator + "greenApples.txt";
            redCardsDeck = DeckFactory.createDeck(redApplesDeckLocation, "redDeck");
            greenCardsDeck = DeckFactory.createDeck(greenApplesDeckLocation, "greenDeck");
        } catch (DeckNotFoundException e) {
            Notifier.displayServerTrace(e.getMessage());
            System.exit(2);
        }
    }

    /**
     * Return the instance of Server singleton class.
     * @return Server object
     */
    public static Dealer getInstance() {
        if (instance == null)
            instance = new Dealer();
        return instance;
    }

    /**
     * Deals cards to each player connected to the server.
     */
    public void dealRedCards() {
        for (ServerPlayer serverPlayer : server.getPlayers()) {
            for (int x=0; x<ServerPlayer.MAX_NUM_OF_PLAYER_CARDS; x++) {
                serverPlayer.addCard(redCardsDeck.drawCard());
            }
            Postman.sendHand(serverPlayer.getConnection(), serverPlayer.getHand());
        }
    }

    /**
     * Give new cards to players which submitted a red card.
     * @param player target player
     */
    public void fillDiscardedCards(ServerPlayer player) {
        ArrayList<Card> newCards = new ArrayList<Card>();
        for (int x=player.getHand().size(); x<ServerPlayer.MAX_NUM_OF_PLAYER_CARDS; x++) {
            Card card = redCardsDeck.drawCard();
            player.addCard(card);  // to server player
            newCards.add(card);  // for actual player
        }
        Postman.sendNewCards(player.getConnection(), newCards);
    }

    public Deck getRedCardsDeck() {
        return redCardsDeck;
    }

    public Deck getGreenCardsDeck() {
        return greenCardsDeck;
    }

    public void resetDealer() {
        instance = null;
    }
}

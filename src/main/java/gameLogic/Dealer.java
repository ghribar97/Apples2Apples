package main.java.gameLogic;

import javafx.geometry.Pos;
import main.java.cards.Card;
import main.java.decks.Deck;
import main.java.decks.DeckFactory;
import main.java.errors.DeckNotFoundException;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import main.java.server.serverClientCommunication.Postman;

import java.util.ArrayList;

public class Dealer {
    private static Dealer instance;

    private Deck redCardsDeck;
    private Deck greenCardsDeck;
    private Server server;

    public Dealer() {
        server = Server.getInstance();
        try {
            redCardsDeck = DeckFactory.createDeck("redApples.txt", "redDeck");
            greenCardsDeck = DeckFactory.createDeck("greenApples.txt", "greenDeck");
        } catch (DeckNotFoundException e) {
            System.out.println(e.getMessage());
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
            for (int x=0; x<serverPlayer.MAX_NUM_OF_PLAYER_CARDS; x++) {
                serverPlayer.addCard(redCardsDeck.drawCard());
            }
            Postman.sendHand(serverPlayer.getConnection(), serverPlayer.getHand());
        }
    }

    public void dealMissingCards(ServerPlayer player) {
        ArrayList<Card> newCards = new ArrayList<Card>();
        for (int x=player.getHand().size(); x<player.MAX_NUM_OF_PLAYER_CARDS; x++) {
            Card card = redCardsDeck.drawCard();
            player.addCard(card);
            newCards.add(card);
        }
        Postman.sendNewCards(player.getConnection(), newCards);
    }

    public Deck getRedCardsDeck() {
        return redCardsDeck;
    }

    public Deck getGreenCardsDeck() {
        return greenCardsDeck;
    }
}

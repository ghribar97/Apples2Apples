package main.test.commonfFunctionsForTesting;

import main.java.cards.Card;
import main.java.cards.CardFactory;
import main.java.cards.cardTypes.AppleCard;
import main.java.errors.CardNotFoundException;
import main.java.gameLogic.GameLogic;
import main.java.players.Player;
import main.java.players.ServerPlayer;
import main.java.server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestFunctions {
    /**
     * generate x bots.
     * @param number number of bots
     * @return list of bots
     */
    public static ArrayList<ServerPlayer> generateBotPlayers(int number) {
        ArrayList<ServerPlayer> players = new ArrayList<ServerPlayer>();
        for(int i=0; i<number; i++) {
            players.add(new ServerPlayer(i));
        }
        return players;
    }

    /**
     * Add x green apples to the player.
     * @param player target player
     * @param numberOfApples number of green apples
     * @throws CardNotFoundException if not existing card
     */
    public static void addXGreenApplesToPlayer(ServerPlayer player, int numberOfApples) throws CardNotFoundException {
        for (int x=0; x<numberOfApples; x++) {
            player.addGreenApple((AppleCard) CardFactory.createCard("GreenApple", "greenAppleCard"));
        }
    }

    /**
     * Adds custom amount of players to the server.
     * @param number number of players.
     */
    public static void setBotPlayers(int number) {
        Server server = Server.getInstance();
        server.setServerPlayers(generateBotPlayers(number));
    }

    /**
     * Fill all players hands with random cards.
     * @param number number of cards
     * @throws CardNotFoundException if card doesn't exists
     */
    public static void addRandomCardsToHand(int number) throws CardNotFoundException {
        Server server = Server.getInstance();
        for (ServerPlayer player : server.getPlayers()) {
            for (int x=0; x<number; x++) {
                player.addCard(CardFactory.createCard("Random Card", "redAppleCard"));
            }
        }
    }

    /**
     * Sets random judge in the game.
     * @return number of the judge
     */
    public static int setRandomJudge() {
        Server server = Server.getInstance();
        int number = new Random().nextInt(server.getPlayers().size());
        for(int i=0; i<server.getPlayers().size(); i++) {
            server.getPlayers().get(i).setJudge(i==number);
        }
        return number;
    }

    public static void setPlayedCardsAndDisplayedCards() throws CardNotFoundException {
        TestFunctions.setBotPlayers(4);
        TestFunctions.addRandomCardsToHand(7);
        GameLogic.judge = TestFunctions.setRandomJudge();
        Server server = Server.getInstance();
        Map<Card, ServerPlayer> playedCards = new HashMap<>();
        ArrayList<Card> shuffledCards = new ArrayList<>();
        for (int x=0; x<server.getPlayers().size(); x++) {
            if(x != GameLogic.judge) {
                ServerPlayer player = server.getPlayers().get(x);
                Card playedCard = player.play();
                playedCards.put(playedCard, player);
                shuffledCards.add(playedCard);
            }
        }
        GameLogic.playedCards = playedCards;
        GameLogic.displayedCards = shuffledCards;
    }
}

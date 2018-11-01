package main.java.gameLogic.phases.primaryPhases;

import main.java.cards.Card;
import main.java.errors.CardNotFoundException;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.Phase;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import main.java.server.serverClientCommunication.Postman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollectingRedApplesPhase implements Phase {
    @Override
    public void execute() {
        GameLogic.playedCards = new HashMap<>();
        Server server = Server.getInstance();
        ArrayList<ServerPlayer> players = server.getPlayers();
        ExecutorService threadPool = Executors.newFixedThreadPool(players.size()-1);

        for(int i=0; i<players.size(); i++) {
            if(i != GameLogic.judge) {
                ServerPlayer currentPlayer = players.get(i);
                Runnable task = new Runnable() { //Make sure every player can answer at the same time
                    @Override
                    public void run() {
                        try {
                            Card playedCard = currentPlayer.play();
                            playedCard.activate();  // activate the special effects of the card
                            GameLogic.playedCards.put(playedCard, currentPlayer);
                            System.out.println(currentPlayer.toString() + " played this card: " + playedCard.getText());
                        } catch (CardNotFoundException e) {
                            System.out.println(currentPlayer.toString() + " couldn't play any card");
                        }
                    }
                };
                threadPool.execute(task);
            }
        }
        threadPool.shutdown();

        //wait for all the answers to come in
        while(!threadPool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        ArrayList<Card> shuffled = new ArrayList<>(GameLogic.playedCards.keySet());
        randomizePlayedCards(shuffled);
        GameLogic.displayedCards = shuffled;
        sendPlayedCardsToPlayers(shuffled, players);
    }

    private void sendPlayedCardsToPlayers(ArrayList<Card> playedCards, ArrayList<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            Postman.sendAllPlayedCards(player.getConnection(), playedCards);
        }
    }

    private void randomizePlayedCards(ArrayList<Card> cards) {
        Collections.shuffle(cards);
    }
}

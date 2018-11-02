package main.java.gameLogic.phases.primaryPhases;

import main.java.cards.Card;
import main.java.errors.CardNotFoundException;
import main.java.gameLogic.GameLogic;
import main.java.players.client.notifier.Notifier;
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
            if(i != GameLogic.judge) {  // is not a judge so the player must play
                ServerPlayer currentPlayer = players.get(i);
                Runnable task = new Runnable() { //Make sure every player can answer at the same time
                    @Override
                    public void run() {
                        playerPlayProcedure(currentPlayer);
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
                Notifier.displayServerTrace(e.getMessage());
            }
        }

        ArrayList<Card> shuffled = new ArrayList<>(GameLogic.playedCards.keySet());  // submitted cards
        randomizePlayedCards(shuffled);
        GameLogic.displayedCards = shuffled;  // this is how the card will be displayed on client side
        sendPlayedCardsToPlayers(shuffled, players);  // send to all players
    }

    private void playerPlayProcedure(ServerPlayer player) {
        try {
            Card playedCard = player.play();  // receive which card the player has played
            playedCard.activate();  // activate the special effects of the card
            GameLogic.playedCards.put(playedCard, player);
            Notifier.displayServerTrace(playedCard.toString() + " played this card: " + playedCard.getText());
        } catch (CardNotFoundException e) {
            Notifier.displayServerTrace(player.toString() + " couldn't play any card");
        }
    }

    /**
     * Send the played card to all online players.
     * @param playedCards card that was played
     * @param players ServerPlayers
     */
    private void sendPlayedCardsToPlayers(ArrayList<Card> playedCards, ArrayList<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            Postman.sendAllPlayedCards(player.getConnection(), playedCards);
        }
    }

    /**
     * Shuffle the submitted card before displaying it to the players.
     * @param cards played cards
     */
    private void randomizePlayedCards(ArrayList<Card> cards) {
        Collections.shuffle(cards);
    }
}

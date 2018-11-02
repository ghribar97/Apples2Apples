package main.java.players.client;

import main.java.cards.Card;
import main.java.cards.cardTypes.AppleCard;
import main.java.errors.CardNotFoundException;
import main.java.players.Player;
import main.java.players.client.notifier.Notifier;
import main.java.server.serverClientCommunication.Connection;
import main.java.server.serverClientCommunication.Postman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ClientPlayer extends Player {
    private AppleCard currentGreenApple;  // which green apple is currently in game
    private ArrayList<Card> playedCards;  // played cards from all players (when they are submitted)

    public ClientPlayer(String ipAddress) {
        connection = new Connection(ipAddress);  // connects player with the server
    }

    /**
     * initialize player's data (hand and id).
     */
    public void startPlayer() {
        playerID = Postman.receiveID(connection);
        System.out.println("You are connected to the server as player with ID: " + playerID);
        System.out.println("Waiting for other players...");
        hand = Postman.receiveHand(connection);
        gameLoop();
    }

    /**
     * The main loop of the player cycle.
     */
    private void gameLoop() {
        int winningID = -1;  // nobody won
        while (winningID < 0){
            judge = Postman.receiveJudgeInfo(connection);
            displayHeader();
            try {
                play();
            } catch (CardNotFoundException e) { System.out.println(e.getMessage()); }
            showAllPlayedCard();
            chooseWinningCard();
            addDiscardedCards();
            winningID = Postman.receiveEndOfTheGame(connection);  // will return id of the winning player or -1
        }
        System.out.println(winningID == playerID ? "You won!" : "Player with ID: " + winningID + " won!");
    }

    /**
     * Display all the cards that players have chosen.
     */
    private void showAllPlayedCard() {
        playedCards = Postman.receiveAllPlayedCards(connection);
        Notifier.displayPlayedCards(playedCards, currentGreenApple);
    }

    /**
     * Display the header of the current round... If the player is judge and what is the green apple.
     */
    private void displayHeader() {
        Notifier.displayNewRound(judge);
        try {
            currentGreenApple = Postman.receiveGreenAppleCard(connection);
            Notifier.displayGreenApple(currentGreenApple);
        } catch (CardNotFoundException e) {
            System.out.println("Could not retrieve green apple card from the server!" + System.lineSeparator());
        }
    }

    /**
     * Take the input from the player.
     * @param text text that is displayed when retrieving player's input
     * @param maxNumber what is max number player can click (to prevent wrong inputs)
     * @return input of the player
     */
    private int getPlayersInput(String text, int maxNumber) {
        int choice = -1;  // wrong input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!(choice >= 0 && choice < maxNumber)) {
            try {
                System.out.print("\n" + text);
                choice = Integer.parseInt(br.readLine());
            } catch (IOException e) { System.out.println(e.getMessage());
            } catch (NumberFormatException n) {  // invalid input (not a number)
                System.out.println("Please enter the digit! Choose again");
                continue;
            }
            if (!(choice >= 0 && choice < maxNumber))  // invalid input (invalid number)
                System.out.println("Wrong number! Choose again");
        }
        return choice;
    }

    public Card play() throws CardNotFoundException {
        if (!judge) {
            Notifier.displayHand(hand);  // display cards in hand
            Card playedCard = hand.remove(getPlayersInput("I will play card number: ", hand.size()));  // discard
            Postman.sendPlayedCard(connection, playedCard);  // send played card
            System.out.println("Waiting for other players...\n");  // waiting for other players to react
            return playedCard;
        }
        return null;
    }

    public int chooseWinningCard() {
        if (judge)   // has to choose which card is the closest to the green apple
            Postman.sendTheBestCard(connection, getPlayersInput("Choose which red apple wins: ", playedCards.size()));
        // has to receive which card was the best and who played it
        int card = Postman.receiveTheBestCard(connection);
        Notifier.displayWinningCard(playedCards.get(card), Postman.receiveWinnerOfTheRound(connection));
        return card;
    }

    /**
     * New cards from the server (replace the discarded cards).
     */
    private void addDiscardedCards() {
        if (hand.size() < MAX_NUM_OF_PLAYER_CARDS)
            hand.addAll(Postman.receiveNewCards(connection));
    }
}

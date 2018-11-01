package main.java.players;

import main.java.cards.Card;
import main.java.errors.CardNotFoundException;
import main.java.gameLogic.Notifier;
import main.java.server.serverClientCommunication.Connection;
import main.java.server.serverClientCommunication.Postman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ClientPlayer extends ServerPlayer {
    private Card currentGreenApple;
    private ArrayList<Card> playedCards;

    public static void main(String[] args) {
        ClientPlayer clientPlayer = new ClientPlayer("localhost");
    }

    public ClientPlayer(String ipAddress) {
        connection = new Connection(ipAddress);
        playerID = Postman.receiveID(connection);
        System.out.println("You are connected to the server as player with ID: " + playerID);
        System.out.println("Waiting for other players...");
        hand = Postman.receiveHand(connection);
        gameLoop();
    }

    private void gameLoop() {
        int winningID = -1;  // nobody won
        while (winningID < 0){
            judge = Postman.receiveJudgeInfo(connection);
            displayHeader();
            play();
            playedCards = Postman.receiveAllPlayedCards(connection);
            Notifier.displayPlayedCards(playedCards, currentGreenApple);
            selectWinningCard();
            addMissingCards();
            winningID = Postman.receiveEndOfTheGame(connection);  // will return id of the winning player or -1
        }
        System.out.println(winningID == playerID ? "You won!" : "Player with ID: " + winningID + " won!");
    }

    private void displayHeader() {
        Notifier.displayNewRound(judge);
        try {
            currentGreenApple = Postman.receiveGreenAppleCard(connection);
            Notifier.displayGreenApple(currentGreenApple);
        } catch (CardNotFoundException e) {
            System.out.println("Could not get green apple card from the server!" + System.lineSeparator());
        }
    }

    private int getPlayersInput(String text, int maxNumber) {
        int choice = -1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!(choice >= 0 && choice < maxNumber)) {
            try {
                System.out.print("\n" + text);
                String input = br.readLine();
                choice = Integer.parseInt(input);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (!(choice >= 0 && choice < maxNumber))
                System.out.println("Wrong number! Choose again");
        }
        return choice;
    }

    @Override
    public Card play() {
        if (!judge) {
            Notifier.displayHand(hand);
            Card playedCard = hand.remove(getPlayersInput("I will play card number: ", hand.size()));
            Postman.sendPlayedCard(connection, playedCard);
            System.out.println("Waiting for other players...\n");
            return playedCard;
        }
        return null;
    }

    private void selectWinningCard() {
        if (judge) {
            int choice = getPlayersInput("Choose which red apple wins: ", playedCards.size());
            Postman.sendTheBestCard(connection, choice);
        }
        int card = Postman.receiveTheBestCard(connection);
        Notifier.displayWinningCard(playedCards.get(card), Postman.receiveWinnerOfTheRound(connection));
    }

    private void addMissingCards() {
        if (hand.size() < MAX_NUM_OF_PLAYER_CARDS) {
            hand.addAll(Postman.receiveNewCards(connection));
        }
    }
}

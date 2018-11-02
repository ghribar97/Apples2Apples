package main.java.players.client.notifier;

import main.java.cards.Card;
import main.java.cards.cardTypes.AppleCard;
import main.java.gameLogic.GameLogic;

import java.util.ArrayList;

public class Notifier {
    public static void displayNewRound(boolean judge) {
        System.out.println("*****************************************************");
        if(judge)
            System.out.println("**                 NEW ROUND - JUDGE               **");
        else
            System.out.println("**                    NEW ROUND                    **");
        System.out.println("*****************************************************");
    }

    public static void displayHand(ArrayList<Card> hand) {
        System.out.println("Choose a red apple to play:");
        for(int i=0; i<hand.size(); i++) {
            System.out.println("["+i+"]   " + hand.get(i).getText());
        }
    }

    public static void displayGreenApple(Card greenApple) {
        System.out.println("Green apple: " + greenApple.getText() + System.lineSeparator());
    }

    public static void displayPlayedCards(ArrayList<Card> hand, AppleCard greenApple) {
        System.out.println(System.lineSeparator() + "The following apples were played:");
        System.out.println(greenApple.getText());
        for(int i=0; i<hand.size(); i++) {
            System.out.println("\t["+i+"]   " + hand.get(i).getText());
        }
    }

    public static void displayWinningCard(Card winningCard, String winningPlayerID) {
        System.out.println(winningPlayerID + " won with: " + winningCard.getText() + System.lineSeparator());
    }

    /**
     * If server is in debug mode it will print important actions with new line.
     * @param msg message to be printed
     */
    public static void displayServerTrace(String msg) {
        displayServerTraceNoNL(msg + System.lineSeparator());
    }

    /**
     * If server is in debug mode it will print important actions without new line.
     * @param msg message to be printed
     */
    public static void displayServerTraceNoNL(String msg) {
        if (GameLogic.DEBUG_MODE)
            System.out.print(msg);
    }
}

package main.java.gameLogic;

import main.java.cards.Card;

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

    public static void displayPlayedCards(ArrayList<Card> hand, Card greenApple) {
        System.out.println(System.lineSeparator() + "The following apples were played:");
        System.out.println(greenApple.getText());
        for(int i=0; i<hand.size(); i++) {
            System.out.println("\t["+i+"]   " + hand.get(i).getText());
        }
    }

    public static void displayWinningCard(Card winningCard, String winningPlayerID) {
        System.out.println(winningPlayerID + " won with: " + winningCard.getText() + System.lineSeparator());
    }
}

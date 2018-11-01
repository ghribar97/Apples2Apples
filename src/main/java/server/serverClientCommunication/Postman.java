package main.java.server.serverClientCommunication;

import main.java.cards.Card;
import main.java.cards.CardFactory;
import main.java.errors.CardNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

public class Postman {
    private static final String STRING_SEPERATOR = "##";

    private static boolean sendMessage(Connection conn, String msg) {
        if (conn == null || msg.equals(""))  // in case we are sending data to bot
            return false;
        try {
            conn.getOut().writeBytes(msg + "\n");
        } catch (IOException e) {
            System.out.println("Unable to send this message: " + msg + "!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static String receiveMessage(Connection conn) {
        try {
            return conn.getIn().readLine();
        } catch (IOException e) {
            System.out.println("Unable to receive message!");
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static boolean sendID(Connection conn, int id) {
        return Postman.sendMessage(conn, id + "");
    }

    public static int receiveID(Connection conn) {
        return Integer.parseInt(Postman.receiveMessage(conn));
    }

    public static void sendHand(Connection conn, ArrayList<Card> cards) {
        String handString = "";
        for (Card card : cards) {
            handString += card.getText() + Postman.STRING_SEPERATOR + card.getType() + Postman.STRING_SEPERATOR;
        }
        Postman.sendMessage(conn, handString);
    }

    public static ArrayList<Card> receiveHand(Connection conn) {
        ArrayList<Card> cards = new ArrayList<Card>();
        String[] hand = Postman.receiveMessage(conn).split(Postman.STRING_SEPERATOR);
        for (int x=0; x<hand.length; x+=2) {
            try {
                cards.add(Postman.stringToCard(hand[x] + Postman.STRING_SEPERATOR + hand[x + 1]));
            } catch (CardNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return cards;
    }

    public static void sendDrawnGreenAppleCard(Connection conn, Card card) {
        Postman.sendMessage(conn, card.getText() + Postman.STRING_SEPERATOR + card.getType());
    }

    public static Card receiveGreenAppleCard(Connection conn) throws CardNotFoundException {
        return Postman.stringToCard(Postman.receiveMessage(conn));
    }

    public static void sendJudgeInfo(Connection conn, boolean judge) {
        Postman.sendMessage(conn, judge ? "true": "false");
    }

    public static boolean receiveJudgeInfo(Connection conn) {
        return Boolean.parseBoolean(Postman.receiveMessage(conn));
    }

    public static Card receivePlayedCard(Connection conn) throws CardNotFoundException {
        return Postman.stringToCard(Postman.receiveMessage(conn));
    }

    public static void sendPlayedCard(Connection conn, Card card) {
        Postman.sendMessage(conn, card.getText() + Postman.STRING_SEPERATOR + card.getType());
    }

    public static void sendEndOfTheGame(Connection conn, int winningID) {
        Postman.sendMessage(conn, winningID + "");
    }

    public static int receiveEndOfTheGame(Connection conn) {
        return Integer.parseInt(Postman.receiveMessage(conn));
    }

    public static void sendAllPlayedCards(Connection conn, ArrayList<Card> cards) {
        Postman.sendHand(conn, cards);  // the functionality is the same
    }

    public static ArrayList<Card> receiveAllPlayedCards(Connection conn) {
        return Postman.receiveHand(conn);  // judge doesn't submit card
    }

    public static void sendTheBestCard(Connection conn, int card) {
        sendEndOfTheGame(conn, card);
    }

    public static int receiveTheBestCard(Connection conn) {
        return receiveEndOfTheGame(conn);
    }

    public static void sendWinnerOfTheRound(Connection conn, String player) {
        sendMessage(conn, player);
    }

    public static String receiveWinnerOfTheRound(Connection conn) {
        return receiveMessage(conn);
    }

    public static void sendNewCards(Connection conn, ArrayList<Card> cards) {
        Postman.sendHand(conn, cards);  // the functionality is the same
    }

    public static ArrayList<Card> receiveNewCards(Connection conn) {
        return Postman.receiveHand(conn);  // judge doesn't submit card
    }


    private static Card stringToCard(String str) throws CardNotFoundException {
        String[] data = str.split(Postman.STRING_SEPERATOR);
        return CardFactory.createCard(data[0], data[1]);
    }
}

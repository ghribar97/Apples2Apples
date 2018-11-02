package main.java.server.serverClientCommunication;

import main.java.cards.Card;
import main.java.cards.CardFactory;
import main.java.cards.cardTypes.AppleCard;
import main.java.errors.CardNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

public class Postman {
    private static final String STRING_SEPARATOR = "##";

    /**
     * Sends a String message on the other end of the connection.
     * @param conn connection between to entities
     * @param msg String message
     * @return success
     */
    private static boolean sendMessage(Connection conn, String msg) {
        if (conn == null || msg.equals(""))  // in case we are sending data to bot or sending empty message
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

    /**
     * Receive a String message from the other end of the connection.
     * @param conn connection between to entities
     * @return Message that was send from the other end of the connection.
     */
    private static String receiveMessage(Connection conn) {
        try {
            return conn.getIn().readLine();
        } catch (IOException e) {
            System.out.println("Unable to receive message!");
            System.out.println(e.getMessage());
            return "Unable to receive message!";
        }
    }

    private static int receiveInt(Connection conn) {
        return Integer.parseInt(Postman.receiveMessage(conn));
    }

    private static void sendInt(Connection conn, int num) {
        Postman.sendMessage(conn, num + "");
    }

    private static boolean receiveBoolean(Connection conn) {
        return Boolean.parseBoolean(Postman.receiveMessage(conn));
    }

    private static void sendCards(Connection conn, ArrayList<Card> cards) {
        String cardString = "";
        for (Card card : cards) {
            cardString += card.getText() + Postman.STRING_SEPARATOR + card.getType() + Postman.STRING_SEPARATOR;
        }
        Postman.sendMessage(conn, cardString);
    }

    private static ArrayList<Card> receiveCards(Connection conn) {
        ArrayList<Card> cards = new ArrayList<Card>();
        String[] hand = Postman.receiveMessage(conn).split(Postman.STRING_SEPARATOR);
        for (int x=0; x<hand.length; x+=2) {
            try {
                cards.add(Postman.stringToCard(hand[x] + Postman.STRING_SEPARATOR + hand[x + 1]));
            } catch (CardNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return cards;
    }

    public static void sendID(Connection conn, int id) {
        Postman.sendInt(conn, id);
    }

    public static int receiveID(Connection conn) {
        return Postman.receiveInt(conn);
    }

    public static void sendHand(Connection conn, ArrayList<Card> cards) {
        Postman.sendCards(conn, cards);
    }

    public static ArrayList<Card> receiveHand(Connection conn) {
        return Postman.receiveCards(conn);
    }

    public static void sendDrawnGreenAppleCard(Connection conn, Card card) {
        Postman.sendMessage(conn, card.getText() + Postman.STRING_SEPARATOR + card.getType());
    }

    public static AppleCard receiveGreenAppleCard(Connection conn) throws CardNotFoundException {
        return (AppleCard) Postman.stringToCard(Postman.receiveMessage(conn));
    }

    // if the player is judge or not
    public static void sendJudgeInfo(Connection conn, boolean judge) {
        Postman.sendMessage(conn, judge ? "true": "false");
    }

    // if the player is judge or not
    public static boolean receiveJudgeInfo(Connection conn) {
        return Postman.receiveBoolean(conn);
    }

    public static Card receivePlayedCard(Connection conn) throws CardNotFoundException {
        return Postman.stringToCard(Postman.receiveMessage(conn));
    }

    public static void sendPlayedCard(Connection conn, Card card) {
        Postman.sendMessage(conn, card.getText() + Postman.STRING_SEPARATOR + card.getType());
    }

    // if there is a winner it will send winner's ID, otherwise -1
    public static void sendEndOfTheGame(Connection conn, int winningID) {
        Postman.sendInt(conn, winningID);
    }

    public static int receiveEndOfTheGame(Connection conn) {
        return Postman.receiveInt(conn);
    }

    // All cards that were played by the players
    public static void sendAllPlayedCards(Connection conn, ArrayList<Card> cards) {
        Postman.sendCards(conn, cards);
    }

    public static ArrayList<Card> receiveAllPlayedCards(Connection conn) {
        return Postman.receiveCards(conn);
    }

    // judge chooses this card
    public static void sendTheBestCard(Connection conn, int card) {
        Postman.sendInt(conn, card);
    }

    public static int receiveTheBestCard(Connection conn) {
        return Postman.receiveInt(conn);
    }

    public static void sendWinnerOfTheRound(Connection conn, String player) {
        Postman.sendMessage(conn, player);
    }

    public static String receiveWinnerOfTheRound(Connection conn) {
        return receiveMessage(conn);
    }

    public static void sendNewCards(Connection conn, ArrayList<Card> cards) {
        Postman.sendCards(conn, cards);
    }

    public static ArrayList<Card> receiveNewCards(Connection conn) {
        return Postman.receiveCards(conn);
    }

    /**
     * Transform String into Card
     * @param str String representation of the card
     * @return Card object
     * @throws CardNotFoundException if the card does not exist
     */
    private static Card stringToCard(String str) throws CardNotFoundException {
        String[] data = str.split(Postman.STRING_SEPARATOR);
        return CardFactory.createCard(data[0], data[1]);
    }
}

package main.java.decks;

import main.java.errors.CardNotFoundException;
import main.java.cards.Card;
import main.java.cards.CardFactory;
import main.java.decks.FileReader.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Deck {
    private ArrayList<Card> cards;
    private String color;

    public Deck(String filePath, String color) {
        this.color = color;
        this.cards = new ArrayList<Card>();
        formDeck(filePath);
    }

    public Card drawCard() {
        return this.cards.remove(0);
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Forms a deck of cards from a .txt file.
     * @param path path of the .txt file
     */
    private void formDeck(String path) {
        ArrayList<String> file;
        try {
            // return file representation in arrayList (each element is one line)
            file = FileReader.getArrayListFromFile(path);  // I know, I shouldn't load the entire file in a variable
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        // adds the card class to the deck. Because it is a basic deck, we only add basic cards (red and green apples)
        for(String text : file) {
            cards.add(stringToCard(text, color + "AppleCard"));
        }
    }

    /**
     * Transform String to a CardObject.
     * @param text text of the card
     * @return Card object
     */
    private Card stringToCard(String text, String cardType) {
        try {
            return CardFactory.createCard(text, cardType);
        } catch (CardNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * In this function we can add some extra cards to the deck.
     */
    public abstract void addCustomCards();

    @Override
    public abstract String toString();
}

package main.java.decks.DeckTypes;

import main.java.decks.Deck;

import java.util.Arrays;

public class BasicDeck extends Deck {
    public BasicDeck(String filePath, String color) {
        super(filePath, color);
    }

    /**
     * Normal deck doesn't add any custom cards.
     */
    @Override
    public void addCustomCards() {}

    @Override
    public String toString() {
        return "I am a " + getColor() + " BasicDeck, without any extra cards. My cards: " + Arrays.toString(getCards().toArray());
    }
}

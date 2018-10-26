package main.java.cards.cardTypes;

import main.java.cards.Card;

public class AppleCard extends Card {
    public AppleCard(String text, String color) {
        super(text, color);
    }

    /**
     * Normal apple card doesn't have any functionality, except displaying a text.
     */
    @Override
    public void activate() {
    }

    @Override
    public String toString() {
        return "I am a " + getColor() + " AppleCard, without any functionality. My text: " + getText();
    }
}

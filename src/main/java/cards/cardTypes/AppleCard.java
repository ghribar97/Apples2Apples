package main.java.cards.cardTypes;

import main.java.cards.Card;

public class AppleCard extends Card {
    public AppleCard(String text, String type) {
        super(text, type);
    }

    /**
     * Normal apple card doesn't have any functionality, except displaying a text.
     */
    @Override
    public void activate() {
    }

    @Override
    public String toString() {
        return "I am a " + getType() + ", without any functionality. My text: " + getText();
    }
}

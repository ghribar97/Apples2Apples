package main.java.cards;

import main.java.errors.CardNotFoundException;
import main.java.cards.cardTypes.AppleCard;

public class CardFactory {
    public static Card createCard(String text, String type) throws CardNotFoundException {
        switch (type) {
            case "redAppleCard": return new AppleCard(text, type);
            case "greenAppleCard": return new AppleCard(text, type);
            // case "newCard": return new newCard(text, "red"/"green")  -> example of creating a newCard
            default: throw new CardNotFoundException(type);
        }
    }
}

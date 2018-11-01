package main.java.decks;

import main.java.errors.DeckNotFoundException;
import main.java.decks.DeckTypes.BasicDeck;

public class DeckFactory {
    public static Deck createDeck(String filePath, String deck) throws DeckNotFoundException {
        switch (deck) {
            case "redDeck": return new BasicDeck(filePath, "red");
            case "greenDeck": return new BasicDeck(filePath, "green");
            // case "newDeck": return new newDeck(filePath, "red"/"green")  -> example of creating a newDeck
            default: throw new DeckNotFoundException(deck);
        }
    }
}

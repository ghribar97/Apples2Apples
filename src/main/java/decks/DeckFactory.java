package main.java.decks;

import main.java.errors.DeckNotFoundException;
import main.java.decks.DeckTypes.Basicdeck;

public class DeckFactory {
    public static Deck createDeck(String filePath, String deck) throws DeckNotFoundException {
        switch (deck) {
            case "redDeck": return new Basicdeck(filePath, "red");
            case "greenDeck": return new Basicdeck(filePath, "green");
            // case "newDeck": return new newDeck(filePath, "red"/"green")  -> example of creating a newDeck
            default: throw new DeckNotFoundException("This deck does not exists: " + deck);
        }
    }
}

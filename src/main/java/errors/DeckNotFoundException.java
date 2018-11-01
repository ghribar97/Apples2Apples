package main.java.errors;

public class DeckNotFoundException extends Exception {
    public DeckNotFoundException(String deck) {
        super("This deck does not exists: " + deck);
    }
}

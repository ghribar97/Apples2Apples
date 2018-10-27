package main.java.errors;

public class DeckNotFoundException extends Exception {
    public DeckNotFoundException(String msg) {
        super(msg);
    }
}

package main.java.Errors;

public class DeckNotFoundException extends Exception {
    public DeckNotFoundException(String msg) {
        super(msg);
    }
}

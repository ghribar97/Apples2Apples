package main.java.errors;

public class CardNotFoundException extends Exception {
    public CardNotFoundException(String type) {
        super("This card does not exists: " + type);
    }
}

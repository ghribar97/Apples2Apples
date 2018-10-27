package main.java.errors;

public class CardNotFoundException extends Exception {
    public CardNotFoundException(String msg) {
        super(msg);
    }
}

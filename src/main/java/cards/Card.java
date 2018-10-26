package main.java.cards;

public abstract class Card {
    private String text;
    private String color;

    public Card(String text, String color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    /**
     * Method where some extra functionality can be added.
     */
    public abstract void activate();

    @Override
    public abstract String toString();
}

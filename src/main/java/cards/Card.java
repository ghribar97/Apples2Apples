package main.java.cards;

public abstract class Card {
    private String text;
    private String type;

    protected Card(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    /**
     * Method where some extra functionality can be added.
     */
    public abstract void activate();

    @Override
    public abstract String toString();
}

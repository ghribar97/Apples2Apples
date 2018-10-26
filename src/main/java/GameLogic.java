package main.java;


import main.java.Errors.DeckNotFoundException;
import main.java.cards.Card;
import main.java.decks.Deck;
import main.java.decks.DeckFactory;

public class GameLogic {
    public static void main(String[] args) throws DeckNotFoundException  {
        Deck greenDeck = DeckFactory.createDeck("greenApples.txt", "greenDeck");
        Deck redDeck = DeckFactory.createDeck("redApples.txt", "redDeck");
        greenDeck.shuffleDeck();
        Card card = greenDeck.drawCard();
        System.out.println(card.toString());
    }
}

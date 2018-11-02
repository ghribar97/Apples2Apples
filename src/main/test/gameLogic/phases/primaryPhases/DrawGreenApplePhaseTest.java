package main.test.gameLogic.phases.primaryPhases;

import main.java.gameLogic.Dealer;
import main.java.gameLogic.phases.primaryPhases.DrawGreenApplePhase;
import org.junit.Assert;
import org.junit.Test;


public class DrawGreenApplePhaseTest {
    @Test
    public void testDrawGreenApple() {
        Dealer dealer = Dealer.getInstance();
        int numberOfGreenCardsInGreenDeck = dealer.getGreenCardsDeck().getCards().size();
        new DrawGreenApplePhase().execute();
        int newNumberOfGreenCardsInGreenDeck = dealer.getGreenCardsDeck().getCards().size();
        Assert.assertEquals(newNumberOfGreenCardsInGreenDeck, numberOfGreenCardsInGreenDeck - 1);
    }
}

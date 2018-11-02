package main.test.gameLogic.phases.primaryPhases;

import main.java.cards.CardFactory;
import main.java.cards.cardTypes.AppleCard;
import main.java.errors.CardNotFoundException;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.primaryPhases.SelectAWinnerPhase;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import main.test.commonfFunctionsForTesting.TestFunctions;
import org.junit.Assert;
import org.junit.Test;

public class SelectAWinnerPhaseTest {
    @Test
    public void testSelectAWinner() throws CardNotFoundException {
        TestFunctions.setPlayedCardsAndDisplayedCards();
        GameLogic.greenApple = (AppleCard) CardFactory.createCard("GreenApple", "greenAppleCard");
        new SelectAWinnerPhase().execute();
        boolean haveApple = false;
        for (ServerPlayer player : Server.getInstance().getPlayers()) {
            if(!haveApple && player.getGreenApples().get(0).toString().equals(GameLogic.greenApple.toString()))
                haveApple = true;
        }
        Assert.assertTrue(haveApple);
    }
}

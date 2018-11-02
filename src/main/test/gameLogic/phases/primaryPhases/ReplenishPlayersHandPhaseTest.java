package main.test.gameLogic.phases.primaryPhases;

import main.java.errors.CardNotFoundException;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.primaryPhases.ReplenishPlayersHandPhase;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import main.test.commonfFunctionsForTesting.TestFunctions;
import org.junit.Assert;
import org.junit.Test;

public class ReplenishPlayersHandPhaseTest {
    @Test
    public void testNextPlayerIsJudge() {
        TestFunctions.setBotPlayers(4);
        int judge = TestFunctions.setRandomJudge();
        GameLogic.judge = judge;
        new ReplenishPlayersHandPhase().execute();
        Assert.assertTrue(GameLogic.judge != judge);
        Assert.assertEquals(GameLogic.judge,((judge + 1) % Server.getInstance().getPlayers().size()));
    }

    @Test
    public void testReplenishCards() throws CardNotFoundException {
        TestFunctions.setPlayedCardsAndDisplayedCards();
        new ReplenishPlayersHandPhase().execute();
        for (ServerPlayer player : Server.getInstance().getPlayers()) {
            Assert.assertEquals(player.getHand().size(), ServerPlayer.MAX_NUM_OF_PLAYER_CARDS);
        }
    }
}

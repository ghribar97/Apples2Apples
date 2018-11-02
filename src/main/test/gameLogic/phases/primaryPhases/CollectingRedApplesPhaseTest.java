package main.test.gameLogic.phases.primaryPhases;

import main.java.errors.CardNotFoundException;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.primaryPhases.CollectingRedApplesPhase;
import main.java.server.Server;
import main.test.commonfFunctionsForTesting.TestFunctions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollectingRedApplesPhaseTest {
    private Server server;
    private int judge;

    @Before
    public void setUp() throws CardNotFoundException {
        TestFunctions.setBotPlayers(4);
        TestFunctions.addRandomCardsToHand(7);
        judge = TestFunctions.setRandomJudge();
        server = Server.getInstance();
        GameLogic.judge = judge;
    }

    @Test
    public void testAllPlayersChooseOneRedAppleFromTheirHandExceptJudge() {
        new CollectingRedApplesPhase().execute();
        for (int x=0; x<server.getPlayers().size(); x++) {
            if(x == judge && server.getPlayers().get(x).getHand().size() == 6)
                Assert.fail();
            if(x != judge && server.getPlayers().get(x).getHand().size() == 7)
                Assert.fail();
        }
    }

    @Test
    public void testIfReceivedCardsAreShuffled() {
        new CollectingRedApplesPhase().execute();
        Assert.assertNotSame(GameLogic.displayedCards, GameLogic.playedCards.keySet());
    }
}

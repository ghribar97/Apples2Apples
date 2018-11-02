package main.test.gameLogic.phases;

import main.java.errors.CardNotFoundException;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.WinningTheGamePhase;
import main.java.server.Server;
import main.test.commonfFunctionsForTesting.TestFunctions;
import org.junit.Assert;
import org.junit.Test;

public class WinningTheGamePhaseTest {
    // it also check requirement 14 (otherwise would know when is over)
    @Test
    public void testWinWith4Players() throws Exception {
        testWin(4, 8);
    }

    @Test
    public void testWinWith5Players() throws Exception {
        testWin(5, 7);
    }

    @Test
    public void testWinWith6Players() throws Exception {
        testWin(6, 6);
    }

    @Test
    public void testWinWith7Players() throws Exception {
        testWin(7, 5);
    }

    @Test
    public void testWinWith8Players() throws Exception {
        testWin(8, 4);
    }

    @Test
    public void testWinWith8AndMorePlayers() throws Exception {
        testWin(9, 4);
        testWin(10, 4);
        testWin(15, 4);
    }


    /**
     * Check if the game is finished.
     * @param numberOfPlayers number of players
     * @param requiredApples number of required apples
     * @throws CardNotFoundException if not existing card
     */
    private void testWin(int numberOfPlayers, int requiredApples) throws CardNotFoundException {
        Server server = Server.getInstance();
        server.setServerPlayers(TestFunctions.generateBotPlayers(numberOfPlayers));
        TestFunctions.addXGreenApplesToPlayer(server.getPlayers().get(0), requiredApples);
        new WinningTheGamePhase().execute();
        Assert.assertTrue(GameLogic.gameFinished);
    }
}

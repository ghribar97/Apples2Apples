package main.test;

import main.test.gameLogic.phases.SettingUpTheGamePhaseTest;
import main.test.gameLogic.phases.WinningTheGamePhaseTest;
import main.test.gameLogic.phases.primaryPhases.CollectingRedApplesPhaseTest;
import main.test.gameLogic.phases.primaryPhases.DrawGreenApplePhaseTest;
import main.test.gameLogic.phases.primaryPhases.ReplenishPlayersHandPhaseTest;
import main.test.gameLogic.phases.primaryPhases.SelectAWinnerPhaseTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CollectingRedApplesPhaseTest.class,
        DrawGreenApplePhaseTest.class,
        ReplenishPlayersHandPhaseTest.class,
        SelectAWinnerPhaseTest.class,
        SettingUpTheGamePhaseTest.class,
        WinningTheGamePhaseTest.class
})
public class TestRunner {
}

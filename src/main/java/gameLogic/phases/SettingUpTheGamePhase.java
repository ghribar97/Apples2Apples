package main.java.gameLogic.phases;

import main.java.gameLogic.Dealer;
import main.java.gameLogic.GameLogic;
import main.java.server.Server;

import java.util.Random;

public class SettingUpTheGamePhase implements Phase {
    @Override
    public void execute() {
        Dealer dealer = Dealer.getInstance();
        Server server = Server.getInstance();
        dealer.getGreenCardsDeck().shuffleDeck();
        dealer.getRedCardsDeck().shuffleDeck();
        dealer.dealRedCards();  // deal red cards to all players
        // randomly decide who is the judge, if there are no players the judge is 0 (for testing purposes)
        GameLogic.judge = (server.getPlayers().size() == 0) ? 0 : new Random().nextInt(server.getPlayers().size());
    }
}

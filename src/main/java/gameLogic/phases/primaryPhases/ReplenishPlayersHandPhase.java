package main.java.gameLogic.phases.primaryPhases;

import main.java.gameLogic.Dealer;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.Phase;
import main.java.players.ServerPlayer;
import main.java.server.Server;

import java.util.ArrayList;

public class ReplenishPlayersHandPhase implements Phase {
    @Override
    public void execute() {
        Server server = Server.getInstance();
        ArrayList<ServerPlayer> players = server.getPlayers();
        Dealer dealer = Dealer.getInstance();
        for (ServerPlayer player : players) {
            dealer.dealMissingCards(player);
        }
        GameLogic.judge = (GameLogic.judge + 1) % server.getPlayers().size();
    }
}

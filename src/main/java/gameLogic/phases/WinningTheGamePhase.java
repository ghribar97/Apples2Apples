package main.java.gameLogic.phases;

import main.java.gameLogic.GameLogic;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import main.java.server.serverClientCommunication.Postman;

import java.util.ArrayList;

public class WinningTheGamePhase implements Phase {
    @Override
    public void execute() {
        Server server = Server.getInstance();
        int winningID = checkIfGameIsOver(server.getPlayers());
        for (ServerPlayer player : server.getPlayers()) {
            Postman.sendEndOfTheGame(player.getConnection(), winningID);
        }
        GameLogic.gameFinished = winningID >= 0;
    }

    private int checkIfGameIsOver(ArrayList<ServerPlayer> players) {
        if (players.size() < 4) {
            System.out.println("Not enough players on the server!");
            System.exit(3);
        }
        int nobodyWonID = -1;  // must be negative or change the value in clientPlayer
        for (ServerPlayer player : players) {
            switch (players.size()) {
                case 4: if (checkIfPlayerWon(player, 8)) return player.getPlayerID();
                case 5: if (checkIfPlayerWon(player, 7)) return player.getPlayerID();
                case 6: if (checkIfPlayerWon(player, 6)) return player.getPlayerID();
                case 7: if (checkIfPlayerWon(player, 5)) return player.getPlayerID();
                default: if (checkIfPlayerWon(player, 4)) return player.getPlayerID();
            }
        }
        return nobodyWonID;
    }

    private boolean checkIfPlayerWon(ServerPlayer player, int applesForWin) {
        return player.getGreenApples().size() == applesForWin;
    }
}

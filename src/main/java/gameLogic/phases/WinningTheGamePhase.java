package main.java.gameLogic.phases;

import main.java.gameLogic.GameLogic;
import main.java.players.client.notifier.Notifier;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import main.java.server.serverClientCommunication.Postman;

import java.util.ArrayList;

public class WinningTheGamePhase implements Phase {
    @Override
    public void execute() {
        Server server = Server.getInstance();
        int winningID = checkIfGameIsOver(server.getPlayers());
        for (ServerPlayer player : server.getPlayers()) {  // notify players about the game status
            Postman.sendEndOfTheGame(player.getConnection(), winningID);  // -1 means the game is still running
            Notifier.displayServerTraceNoNL(player.toString() + ": " + player.getGreenApples().size() + ", ");
        }
        Notifier.displayServerTrace("");
        GameLogic.gameFinished = winningID >= 0;  // update
    }

    /**
     * Validate if the game is over.
     * @param players all players on the server
     * @return if the game is over
     */
    private int checkIfGameIsOver(ArrayList<ServerPlayer> players) {
        if (players.size() < 4) {  // in case there is less than 4 players
            Notifier.displayServerTrace("Not enough players on the server!");
            System.exit(3);
        }
        int nobodyWonID = -1;  // must be negative or change the value in clientPlayer
        for (ServerPlayer player : players) {  // for each player check if it has conditions for victory
            switch (players.size()) {
                // if you don't understand the numbers look at the game rules (Winning the game)
                case 4: if (checkIfPlayerWon(player, 8)) return player.getPlayerID(); break;
                case 5: if (checkIfPlayerWon(player, 7)) return player.getPlayerID(); break;
                case 6: if (checkIfPlayerWon(player, 6)) return player.getPlayerID(); break;
                case 7: if (checkIfPlayerWon(player, 5)) return player.getPlayerID(); break;
                default: if (checkIfPlayerWon(player, 4)) return player.getPlayerID();
            }
        }
        return nobodyWonID;
    }

    /**
     * Check if player has a sufficient amount of green apples.
     * @param player target player
     * @param applesForWin number of green apples required for the win
     * @return if the player won or not
     */
    private boolean checkIfPlayerWon(ServerPlayer player, int applesForWin) {
        return player.getGreenApples().size() == applesForWin;
    }
}

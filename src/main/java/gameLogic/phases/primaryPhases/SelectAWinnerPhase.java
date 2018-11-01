package main.java.gameLogic.phases.primaryPhases;

import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.Phase;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import main.java.server.serverClientCommunication.Postman;

import java.util.ArrayList;

public class SelectAWinnerPhase implements Phase {
    @Override
    public void execute() {
        Server server = Server.getInstance();
        ArrayList<ServerPlayer> players = server.getPlayers();
        int bestCard = players.get(GameLogic.judge).chooseWinningCard();
        ServerPlayer cardOwner = GameLogic.playedCards.get(GameLogic.displayedCards.get(bestCard));
        System.out.println(cardOwner.toString() + " won this round.");
        cardOwner.addGreenApple(GameLogic.greenApple);
        for (int i=0; i<players.size(); i++) {
            Postman.sendTheBestCard(players.get(i).getConnection(), bestCard);
            Postman.sendWinnerOfTheRound(players.get(i).getConnection(), cardOwner.toString());
        }
    }
}

package main.java.gameLogic.phases.primaryPhases;

import main.java.cards.cardTypes.AppleCard;
import main.java.gameLogic.Dealer;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.Phase;
import main.java.players.ServerPlayer;
import main.java.players.client.notifier.Notifier;
import main.java.server.Server;
import main.java.server.serverClientCommunication.Connection;
import main.java.server.serverClientCommunication.Postman;

import java.util.ArrayList;

public class DrawGreenApplePhase implements Phase {
    @Override
    public void execute() {
        Notifier.displayServerTrace("The judge is player with ID: " + GameLogic.judge);
        Dealer dealer = Dealer.getInstance();
        Server server = Server.getInstance();
        ArrayList<ServerPlayer> players = server.getPlayers();
        GameLogic.greenApple = (AppleCard) dealer.getGreenCardsDeck().drawCard();
        for (int i=0; i<players.size(); i++) {
            Connection conn = players.get(i).getConnection();
            Postman.sendJudgeInfo(conn, GameLogic.judge == i);  // notify player if it is a judge
            Postman.sendDrawnGreenAppleCard(conn, GameLogic.greenApple);  // show green card to player
            players.get(i).setJudge(GameLogic.judge == i);  // send judge on ServerPlayer
        }
    }
}

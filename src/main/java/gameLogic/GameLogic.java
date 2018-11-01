package main.java.gameLogic;

import main.java.cards.Card;
import main.java.cards.cardTypes.AppleCard;
import main.java.gameLogic.phases.SettingUpTheGamePhase;
import main.java.gameLogic.phases.WinningTheGamePhase;
import main.java.gameLogic.phases.primaryPhases.CollectingRedApplesPhase;
import main.java.gameLogic.phases.primaryPhases.DrawGreenApplePhase;
import main.java.gameLogic.phases.primaryPhases.ReplenishPlayersHandPhase;
import main.java.gameLogic.phases.primaryPhases.SelectAWinnerPhase;
import main.java.players.ServerPlayer;
import main.java.server.Server;

import java.util.ArrayList;
import java.util.Map;

public class GameLogic {
    public static boolean gameFinished;
    public static int judge;
    public static AppleCard greenApple;
    public static Map<Card, ServerPlayer> playedCards;
    public static ArrayList<Card> displayedCards;

    public void startGame() {
        Server server = Server.getInstance();
        server.waitForOnlinePlayers(1);
        new SettingUpTheGamePhase().execute();  // this phase doesn't use judge anyway
        while (!gameFinished) {
            System.out.println("The judge is player with ID: " + judge);
            new DrawGreenApplePhase().execute();
            new CollectingRedApplesPhase().execute();
            new SelectAWinnerPhase().execute();
            new ReplenishPlayersHandPhase().execute();
            new WinningTheGamePhase().execute();
        }
    }
}
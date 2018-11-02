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
    public static AppleCard greenApple;  // current green apple
    public static Map<Card, ServerPlayer> playedCards;
    public static ArrayList<Card> displayedCards;  // how the cards are displayed on player's screen
    public final static boolean DEBUG_MODE = false;  // set to true if you want to see output of the server

    /**
     * Initialize the game and wait for the players to join.
     * @param numberOfOnlinePlayers how many actual players are there (not bots)
     */
    public void startGame(int numberOfOnlinePlayers) {
        Server server = Server.getInstance();
        server.waitForOnlinePlayers(numberOfOnlinePlayers);
        gameCycle();
    }


    /**
     * The main cycle of the game.
     */
    private void gameCycle() {
        new SettingUpTheGamePhase().execute();
        while (!gameFinished) {
            new DrawGreenApplePhase().execute();
            new CollectingRedApplesPhase().execute();
            new SelectAWinnerPhase().execute();
            new ReplenishPlayersHandPhase().execute();
            new WinningTheGamePhase().execute();
        }
    }
}
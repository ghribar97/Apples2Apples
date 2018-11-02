package main.java;

import main.java.gameLogic.GameLogic;
import main.java.players.client.ClientPlayer;

public class Apples2Apples {
    public static void main(String[] args) {
        if(args.length == 0) {
            try {
                startServerInSeparateThread(1);
                new ClientPlayer("localhost").startPlayer();
            } catch (Exception e) {e.printStackTrace(System.out);}
        } else {
            try {
                //If just a number is submitted then this is the Server and there are online clients
                int numberOfOnlineClients = Integer.parseInt(args[0]);
                startServerInSeparateThread(numberOfOnlineClients + 1);
                new ClientPlayer("localhost").startPlayer();
            } catch(NumberFormatException e) {
                //If it is not a number then we assume it's an URL and then this is one of the online clients
                try {
                    new ClientPlayer(args[0]).startPlayer();
                } catch (Exception err){System.out.println(err.getMessage());}
            } catch(Exception e) {
                e.printStackTrace(System.out);
                System.out.println("Something went wrong");
            }
        }
    }

    public static void startServerInSeparateThread(int numberOfPlayers) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GameLogic game = new GameLogic();
                game.startGame(numberOfPlayers);
            }
        }).start();
    }
}

package main.java;


import main.java.errors.DeckNotFoundException;
import main.java.server.Server;

public class GameLogic {
    public static void main(String[] args) throws DeckNotFoundException  {
        Server server = Server.getInstance();
        System.out.println("waiting for players...");
        server.waitForOnlinePlayers(3);
    }
}

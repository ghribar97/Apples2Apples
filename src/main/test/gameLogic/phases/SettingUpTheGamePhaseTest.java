package main.test.gameLogic.phases;

import main.java.decks.Deck;
import main.java.decks.DeckFactory;
import main.java.errors.DeckNotFoundException;
import main.java.gameLogic.Dealer;
import main.java.gameLogic.GameLogic;
import main.java.gameLogic.phases.SettingUpTheGamePhase;
import main.java.players.ServerPlayer;
import main.java.server.Server;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SettingUpTheGamePhaseTest {
    private Dealer dealer;

    @Before
    public void setUp() {
        new SettingUpTheGamePhase().execute();
        dealer = Dealer.getInstance();
    }

    @Test
    public void testReadAllGreenApplesAndAddToGreenDeck() {
        Deck deck = dealer.getGreenCardsDeck();
        Assert.assertEquals(deck.getCards().size(), 614);  // hardcoded for greenApples.txt
        Assert.assertEquals(deck.getCards().size(), getRealNumberOfLinesInFile(Dealer.greenApplesDeckLocation));
        Assert.assertEquals(deck.getColor(), "green");
    }

    @Test
    public void testReadAllRedApplesAndAddToRedDeck() {
        Deck deck = dealer.getRedCardsDeck();
        Assert.assertEquals(deck.getCards().size(), 1826);  // hardcoded for redApples.txt
        Assert.assertEquals(deck.getCards().size(), getRealNumberOfLinesInFile(Dealer.redApplesDeckLocation));
        Assert.assertEquals(deck.getColor(), "red");
    }

    @Test
    public void testShuffleDeck() throws DeckNotFoundException {
        Deck greenDeck = DeckFactory.createDeck(Dealer.greenApplesDeckLocation, "greenDeck");
        Deck redDeck = DeckFactory.createDeck(Dealer.redApplesDeckLocation, "redDeck");
        Assert.assertNotSame(dealer.getGreenCardsDeck().toString(), greenDeck.toString());
        Assert.assertNotSame(dealer.getRedCardsDeck().toString(), redDeck.toString());
        Assert.assertEquals(greenDeck.getCards().size(), dealer.getGreenCardsDeck().getCards().size());
        Assert.assertEquals(redDeck.getCards().size(), dealer.getRedCardsDeck().getCards().size());
    }

    @Test
    public void testDealCardsToEachPlayer() {
        Server server = Server.getInstance();
        server.waitForOnlinePlayers(0);  // only 4 bots
        new SettingUpTheGamePhase().execute();
        for (ServerPlayer players : server.getPlayers()) {
            Assert.assertEquals(players.getHand().size(), ServerPlayer.MAX_NUM_OF_PLAYER_CARDS);  // MAX_NUM... = 7 -> hardcoded
        }
    }

    @Test
    public void testRandomizeJudge() {
        int[] judges = new int[4];
        for(int x=0; x<32; x++) {
            Server server = Server.getInstance();
            server.waitForOnlinePlayers(0);  // only 4 bots
            new SettingUpTheGamePhase().execute();
            judges[GameLogic.judge] += 1;
        }
        // highly unlikely scenarios
        Assert.assertFalse(judges[0] == judges[1] && judges[1] == judges[2] && judges[2] == judges[3]);
        Assert.assertTrue(judges[0] > 0);
        Assert.assertTrue(judges[1] > 0);
        Assert.assertTrue(judges[2] > 0);
        Assert.assertTrue(judges[3] > 0);
    }

    private long getRealNumberOfLinesInFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath), Charset.defaultCharset())) {
            return lines.count();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}

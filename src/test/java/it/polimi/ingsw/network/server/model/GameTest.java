package it.polimi.ingsw.network.server.model;

import it.polimi.ingsw.network.server.DummyServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("Pablo");
        player2 = new Player("player2");
        player3 = new Player("player3");
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
        game.getPlayers().add(player3);

    }

    @AfterEach
    void tearDown() {
        player1 = null;
        player2 = null;
        player3 = null;
        game = null;
    }

    @Test
    void nextPlayer() {
        game.setCurrentPlayer(player1);
        game.nextPlayer();
        assertTrue(game.isCurrentPlayer(player2.getNickName()));
        game.nextPlayer();
        assertTrue(game.isCurrentPlayer(player3.getNickName()));
        game.nextPlayer();
        assertTrue(game.isCurrentPlayer(player1.getNickName()));
    }

    @Test
    void lastPlayerOfRound() {
        game.setCurrentPlayer(player3);
        assertTrue(game.lastPlayerOfRound());
    }

    @Test
    void firstPlayerOfRound() {
        game.setCurrentPlayer(player1);
        assertTrue(game.firstPlayerOfRound());
    }


    @Test
    void isExpertMode() {
        game.setMode("expert");
        assertTrue(game.isExpertMode());
    }

    @Test
    void isCurrentPlayer() {
        game.setCurrentPlayer(player1);
        assertTrue(game.isCurrentPlayer(player1.getNickName()));
    }
}
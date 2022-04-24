package it.polimi.ingsw.model;

import it.polimi.ingsw.network.server.model.Game;
import it.polimi.ingsw.network.server.model.Phase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    @BeforeEach
    void setUp() {
        game= new Game();
    }

    @AfterEach
    void tearDown() {
        game=null;
    }

    /**
     * Testing if the method for switching between phases works properly
     * the phase is initially set to planning then it should became action;
     */
    @Test
    void nextPhase() {
        assertEquals(Phase.PLANNING, game.getPhase());
        game.nextPhase();
        assertEquals(Phase.ACTION,game.getPhase());
        game.nextPhase();
        assertEquals(Phase.PLANNING,game.getPhase());
    }
}
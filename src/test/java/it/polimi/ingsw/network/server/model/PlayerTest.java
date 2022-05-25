package it.polimi.ingsw.network.server.model;

import it.polimi.ingsw.network.server.model.Player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    Player player;

    @BeforeEach
    void setUp(){
        player=new Player("player1");
    }

    @AfterEach
    void tearDown(){
        player=null;
    }

    @Test
    void testInitPlayer(){
        assertEquals(1,player.getDeck().get(0).getMovement());
        assertEquals(1,player.getDeck().get(1).getMovement());
        assertEquals(2,player.getDeck().get(2).getMovement());
        assertEquals(2,player.getDeck().get(3).getMovement());
        assertEquals(3,player.getDeck().get(4).getMovement());
        assertEquals(3,player.getDeck().get(5).getMovement());
        assertEquals(4,player.getDeck().get(6).getMovement());
        assertEquals(4,player.getDeck().get(7).getMovement());
        assertEquals(5,player.getDeck().get(8).getMovement());
        assertEquals(5,player.getDeck().get(9).getMovement());
    }


}

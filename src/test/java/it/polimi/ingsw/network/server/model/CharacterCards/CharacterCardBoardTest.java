package it.polimi.ingsw.network.server.model.CharacterCards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardBoardTest {

    private CharacterCardBoard board;

    @BeforeEach
    void setUp(){
        board= new CharacterCardBoard();
    }

    @AfterEach
    void tearDown(){
        board=null;
    }

    @Test
    void initBoard(){
        board=new CharacterCardBoard();
        assertEquals(1,board.getAllCard().get(0).getCardId());
        assertEquals(1,board.getAllCard().get(0).getPrice());
        assertEquals(2,board.getAllCard().get(1).getCardId());
        assertEquals(2,board.getAllCard().get(1).getPrice());
        assertEquals(3,board.getAllCard().get(2).getCardId());
        assertEquals(3,board.getAllCard().get(2).getPrice());
        assertEquals(4,board.getAllCard().get(3).getCardId());
        assertEquals(1,board.getAllCard().get(3).getPrice());
        assertEquals(5,board.getAllCard().get(4).getCardId());
        assertEquals(2,board.getAllCard().get(4).getPrice());
        assertEquals(6,board.getAllCard().get(5).getCardId());
        assertEquals(3,board.getAllCard().get(5).getPrice());
        assertEquals(7,board.getAllCard().get(6).getCardId());
        assertEquals(1,board.getAllCard().get(6).getPrice());
        assertEquals(8,board.getAllCard().get(7).getCardId());
        assertEquals(2,board.getAllCard().get(7).getPrice());
        assertEquals(9,board.getAllCard().get(8).getCardId());
        assertEquals(3,board.getAllCard().get(8).getPrice());
        assertEquals(10,board.getAllCard().get(9).getCardId());
        assertEquals(1,board.getAllCard().get(9).getPrice());
        assertEquals(11,board.getAllCard().get(10).getCardId());
        assertEquals(2,board.getAllCard().get(10).getPrice());
        assertEquals(12,board.getAllCard().get(11).getCardId());
        assertEquals(3,board.getAllCard().get(11).getPrice());
    }

    @Test
    void initAvailableCC(){
        board.initializeAvailableCC();
        ArrayList<CharacterCard> cards = new ArrayList<>();
        cards.addAll(board.getAvailableCards());
        for(int i =0; i<3; i++){
            assertFalse(board.getAllCard().contains(cards.get(i)));
        }
    }

}
package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.GameBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardGroup2Test {
    GameBoard board;

    CharacterCard card;
    @BeforeEach
    void setUp() {
        board = new GameBoard(2);
        card=new CharacterCardGroup2();
        card.setGameBoard(board);
    }

    @AfterEach
    void tearDown() {
        card=null;
    }

    @Test
    void activate() {
    }
}
package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;
import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCardGroup2;
import it.polimi.ingsw.network.server.model.GameBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
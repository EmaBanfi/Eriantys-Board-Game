package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.model.GameBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class CharacterCardGroup5Test {
    CharacterCardGroup5 cardGroup5;
    GameBoard board;

    @BeforeEach
    void setUp() {
        cardGroup5 = new CharacterCardGroup5();
        board = new GameBoard(2);
        cardGroup5.setGameBoard(board);
    }

    @AfterEach
    void tearDown() {
        cardGroup5 = null;
    }

    /**
     * testing if the characterCard n°5 make the right changes based on its effect
     */
    @Test
    void activateCC5() {
        cardGroup5.setCardId(5);

        assertEquals(FALSE, cardGroup5.getGameBoard().getIsland(2).isBlockCard());
        cardGroup5.activate(2);
        assertEquals(TRUE, cardGroup5.getGameBoard().getIsland(2).isBlockCard());

        cardGroup5.getGameBoard().getIsland(2).removeBlockCard();
        assertEquals(FALSE, cardGroup5.getGameBoard().getIsland(2).isBlockCard());
    }

    /**
     * testing if the characterCard n°6 make the right changes based on its effect
     */
    @Test
    void activateCC6() {
        cardGroup5.setCardId(6);

        assertEquals(FALSE, cardGroup5.getGameBoard().getIsland(3).isIgnoreTower());
        cardGroup5.activate(3);
        assertEquals(TRUE, cardGroup5.getGameBoard().getIsland(3).isIgnoreTower());

        cardGroup5.getGameBoard().getIsland(3).setIgnoreTower(FALSE);
        assertEquals(FALSE, cardGroup5.getGameBoard().getIsland(3).isIgnoreTower());
    }
}
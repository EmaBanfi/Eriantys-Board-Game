package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.model.StudentColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterCardGroup1Test {
    CharacterCardGroup1 cardGroup1;
    GameBoard board;
    int chosenIsland;
    int numOfStudents;
    StudentColor color = null;

    @BeforeEach
    void setUp() {
        board = new GameBoard(2);
        cardGroup1 = new CharacterCardGroup1();
        cardGroup1.setGameBoard(board);
        chosenIsland = 2;
        numOfStudents = 0;
    }

    @AfterEach
    void tearDown() {
        cardGroup1 = null;
        board = null;
        color = null;
    }

    /**
     * testing if there are 4 students on cardGroup1
     */
    @Test
    void studentsOnCard() throws LastStudentDrawnException {
        cardGroup1.putStudentsOnCard();
        assertEquals(4, cardGroup1.getStudentsOnCard().size());
    }

    /**
     * testing if the characterCard n°1 add one student to an Island and refill the students on itself
     */
    @Test
    void activateCC1() throws LastStudentDrawnException {
        if (cardGroup1.getGameBoard().getMotherNature().getCurrentIsland() == 2)
            chosenIsland = 3;

        cardGroup1.putStudentsOnCard();
        color = cardGroup1.getStudentsOnCard().get(0);
        numOfStudents = cardGroup1.getGameBoard().getIsland(chosenIsland).countStudentColor(color);

        cardGroup1.activate(color, chosenIsland);

        assertEquals(numOfStudents + 1, cardGroup1.getGameBoard().getIsland(chosenIsland).countStudentColor(color));

        assertEquals(4, cardGroup1.getStudentsOnCard().size());
    }

}
package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCardGroup4;
import it.polimi.ingsw.network.server.model.GameBoard;
import it.polimi.ingsw.network.server.model.Player;
import it.polimi.ingsw.network.server.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardGroup4Test {
    CharacterCardGroup4 cardGroup4;
    GameBoard board;
    StudentColor color = null;

    @BeforeEach
    void setUp() {
        board = new GameBoard(2);
        cardGroup4 = new CharacterCardGroup4();
        board = new GameBoard(2);
        cardGroup4.setGameBoard(board);
    }

    @AfterEach
    void tearDown2() {
        cardGroup4 = null;
        board = null;
        color = null;
    }

    /**
     * testing if the characterCard n°9 make the right changes based on its effect
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    @Test
    void activateCC9() throws LastStudentDrawnException {
        cardGroup4.setCardId(9);

        color = StudentColor.BLUE;
        cardGroup4.activate(color);

        assertEquals(StudentColor.BLUE, cardGroup4.getGameBoard().getIgnoredColor());

        cardGroup4.getGameBoard().resetIgnoredColor();

        color = StudentColor.RED;
        cardGroup4.activate(color);

        assertEquals(StudentColor.RED, cardGroup4.getGameBoard().getIgnoredColor());
    }

    /**
     * it fills the DiningHall of player with 17 students
     */
    void fillDiningHall(Player player) throws LastStudentDrawnException {
        player.getBoard().getDiningHall().addStudents(cardGroup4.getGameBoard().getBag().draw(17));
    }

    /**
     * testing if the characterCard n°11 make the right changes based on its effect
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    @Test
    void activateCC11() throws LastStudentDrawnException {
        cardGroup4.setCardId(11);

        Player player1;
        player1 = new Player("Paolo");
        fillDiningHall(player1);
        cardGroup4.getGameBoard().setCurrentPlayer(player1);
        cardGroup4.setCurrentPlayer(cardGroup4.getGameBoard().getCurrentPlayer());

        cardGroup4.setCardId(11);

        cardGroup4.putStudentsOnCard();
        assertEquals(4, cardGroup4.getStudentsOnCard().size());

        color = cardGroup4.getStudentsOnCard().get(0);
        int numOfPlayers = cardGroup4.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().countStudentColor(color);
        cardGroup4.activate(color);
        assertEquals(numOfPlayers + 1, cardGroup4.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().countStudentColor(color));
        assertEquals(3, cardGroup4.getStudentsOnCard().size());
    }

    /**
     * testing if the characterCard n°12 make the right changes based on its effect
     */
    @Test
    void activateCC12() throws LastStudentDrawnException {
        cardGroup4.setCardId(12);

        Player player1;
        player1 = new Player("Paolo");
        fillDiningHall(player1);
        Player player2;
        player2 = new Player("Marco");
        fillDiningHall(player2);
        cardGroup4.getGameBoard().getPlayers().add(player1);
        cardGroup4.getGameBoard().getPlayers().add(player2);


        ArrayList<StudentColor> colors = cardGroup4.getGameBoard().getBag().draw(1);
        color = colors.get(0);

        int numOfStudents1 = cardGroup4.getGameBoard().getPlayers().get(0).getBoard().getDiningHall().countStudentColor(color);
        int numOfStudents2 = cardGroup4.getGameBoard().getPlayers().get(1).getBoard().getDiningHall().countStudentColor(color);
        cardGroup4.activate(color);
        if (numOfStudents1 > 3)
            assertEquals(numOfStudents1 - 3, cardGroup4.getGameBoard().getPlayers().get(0).getBoard().getDiningHall().countStudentColor(color));
        else
            assertEquals(0, cardGroup4.getGameBoard().getPlayers().get(0).getBoard().getDiningHall().countStudentColor(color));
        if (numOfStudents2 > 3)
            assertEquals(numOfStudents2 - 3, cardGroup4.getGameBoard().getPlayers().get(1).getBoard().getDiningHall().countStudentColor(color));
        else
            assertEquals(0, cardGroup4.getGameBoard().getPlayers().get(1).getBoard().getDiningHall().countStudentColor(color));

    }
}
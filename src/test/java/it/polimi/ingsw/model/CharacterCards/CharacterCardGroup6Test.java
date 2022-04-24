package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCardGroup6;
import it.polimi.ingsw.network.server.model.GameBoard;
import it.polimi.ingsw.network.server.model.Player;
import it.polimi.ingsw.network.server.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterCardGroup6Test {
    CharacterCardGroup6 cardGroup6;
    GameBoard board;
    Player currentPlayer;
    ArrayList<StudentColor> array1;
    ArrayList<StudentColor> array2;
    HashMap<StudentColor, Integer> numOfStudents1;
    HashMap<StudentColor, Integer> numOfStudents2;

    @BeforeEach
    void setUp() throws LastStudentDrawnException {
        cardGroup6 = new CharacterCardGroup6();
        board = new GameBoard(2);
        cardGroup6.setGameBoard(board);
        currentPlayer = new Player("Mick");
        cardGroup6.getGameBoard().setCurrentPlayer(currentPlayer);
        array1 = new ArrayList<>();
        array2 = new ArrayList<>();
        numOfStudents1 = new HashMap<>();
        numOfStudents2 = new HashMap<>();
        cardGroup6.setCurrentPlayer(currentPlayer);
        fillHall(currentPlayer);
    }

    @AfterEach
    void tearDown() {
        cardGroup6 = null;
        board = null;
        currentPlayer = null;
        array1 = null;
        array2 = null;
        for (StudentColor color : StudentColor.values())
            numOfStudents1.put(color, 0);
        for (StudentColor color : StudentColor.values())
            numOfStudents2.put(color, 0);
    }

    /**
     * testing if the characterCard n°7 make the right changes based on its effect (first test)
     */
    @Test
    void activateCC7_1() throws LastStudentDrawnException {
        cardGroup6.setCardId(7);

        cardGroup6.putStudentsOnCard();
        for (StudentColor color : StudentColor.values())
            numOfStudents1.put(color, cardGroup6.countStudentColor(color));
        for (StudentColor color : StudentColor.values())
            numOfStudents2.put(color, cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        StudentColor color1 = cardGroup6.getStudentsOnCard().get(0);
        array1.add(color1);
        StudentColor color2 = cardGroup6.getStudentsOnCard().get(1);
        array1.add(color2);
        StudentColor color3 = cardGroup6.getStudentsOnCard().get(3);
        array1.add(color3);

        StudentColor color4 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(0);
        array2.add(color4);
        StudentColor color5 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(1);
        array2.add(color5);
        StudentColor color6 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(2);
        array2.add(color6);

        cardGroup6.activate(array1, array2);

        for (StudentColor color : StudentColor.values()) {
            if (color == color4)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color5)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color6)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color1)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
            if (color == color2)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
            if (color == color3)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents2.get(color), cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        for (StudentColor color : StudentColor.values()) {
            if (color == color1)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color2)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color3)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color4)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
            if (color == color5)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
            if (color == color6)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents1.get(color), cardGroup6.countStudentColor(color));
    }

    /**
     * testing if the characterCard n°7 make the right changes based on its effect (second test)
     */
    @Test
    void activateCC7_2() throws LastStudentDrawnException {
        cardGroup6.setCardId(7);

        cardGroup6.putStudentsOnCard();
        for (StudentColor color : StudentColor.values())
            numOfStudents1.put(color, cardGroup6.countStudentColor(color));
        for (StudentColor color : StudentColor.values())
            numOfStudents2.put(color, cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        StudentColor color1 = cardGroup6.getStudentsOnCard().get(0);
        array1.add(color1);
        StudentColor color2 = cardGroup6.getStudentsOnCard().get(2);
        array1.add(color2);

        StudentColor color4 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(0);
        array2.add(color4);
        StudentColor color5 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(1);
        array2.add(color5);

        cardGroup6.activate(array1, array2);

        for (StudentColor color : StudentColor.values()) {
            if (color == color4)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color5)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color1)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
            if (color == color2)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents2.get(color), cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        for (StudentColor color : StudentColor.values()) {
            if (color == color1)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color2)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color4)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
            if (color == color5)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents1.get(color), cardGroup6.countStudentColor(color));
    }

    /**
     * testing if the characterCard n°7 make the right changes based on its effect (third test)
     */
    @Test
    void activateCC7_3() throws LastStudentDrawnException {
        cardGroup6.setCardId(7);

        cardGroup6.putStudentsOnCard();
        for (StudentColor color : StudentColor.values())
            numOfStudents1.put(color, cardGroup6.countStudentColor(color));
        for (StudentColor color : StudentColor.values())
            numOfStudents2.put(color, cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        StudentColor color1 = cardGroup6.getStudentsOnCard().get(0);
        array1.add(color1);

        StudentColor color4 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(0);
        array2.add(color4);

        cardGroup6.activate(array1, array2);

        for (StudentColor color : StudentColor.values()) {
            if (color == color4)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color1)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents2.get(color), cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        for (StudentColor color : StudentColor.values()) {
            if (color == color1)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color4)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents1.get(color), cardGroup6.countStudentColor(color));
    }

    /**
     * testing if the characterCard n°10 make the right changes based on its effect (first test)
     */
    @Test
    void activateCC10_1() throws LastStudentDrawnException {
        cardGroup6.setCardId(10);

        fillDiningHall(currentPlayer);

        for (StudentColor color : StudentColor.values())
            numOfStudents1.put(color, cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));
        for (StudentColor color : StudentColor.values())
            numOfStudents2.put(color, cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().countStudentColor(color));

        StudentColor color1 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().getStudents().get(0);
        array1.add(color1);
        StudentColor color2 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().getStudents().get(1);
        array1.add(color2);

        StudentColor color3 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(0);
        array2.add(color3);
        StudentColor color4 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(1);
        array2.add(color4);

        cardGroup6.activate(array1, array2);

        for (StudentColor color : StudentColor.values()) {
            if (color == color3)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color4)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color1)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
            if (color == color2)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents1.get(color), cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        for (StudentColor color : StudentColor.values()) {
            if (color == color1)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color2)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color3)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
            if (color == color4)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents2.get(color), cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().countStudentColor(color));
    }

    /**
     * testing if the characterCard n°10 make the right changes based on its effect (second test)
     */
    @Test
    void activateCC10_2() throws LastStudentDrawnException {
        cardGroup6.setCardId(10);

        fillDiningHall(currentPlayer);

        for (StudentColor color : StudentColor.values())
            numOfStudents1.put(color, cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));
        for (StudentColor color : StudentColor.values())
            numOfStudents2.put(color, cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().countStudentColor(color));

        StudentColor color1 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().getStudents().get(0);
        array1.add(color1);

        StudentColor color3 = cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().getStudents().get(0);
        array2.add(color3);

        cardGroup6.activate(array1, array2);

        for (StudentColor color : StudentColor.values()) {
            if (color == color3)
                numOfStudents1.put(color, numOfStudents1.get(color) - 1);
            if (color == color1)
                numOfStudents1.put(color, numOfStudents1.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents1.get(color), cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getHall().countStudentColor(color));

        for (StudentColor color : StudentColor.values()) {
            if (color == color1)
                numOfStudents2.put(color, numOfStudents2.get(color) - 1);
            if (color == color3)
                numOfStudents2.put(color, numOfStudents2.get(color) + 1);
        }
        for (StudentColor color : StudentColor.values())
            assertEquals(numOfStudents2.get(color), cardGroup6.getGameBoard().getCurrentPlayer().getBoard().getDiningHall().countStudentColor(color));
    }

    /**
     * it fills the Hall with the Player with 5 students
     */
    void fillHall(Player player) throws LastStudentDrawnException {
        player.getBoard().getHall().addStudents(cardGroup6.getGameBoard().getBag().draw(5));
    }

    /**
     * it fills the DiningHall with the Player with 5 students
     */
    void fillDiningHall(Player player) throws LastStudentDrawnException {
        player.getBoard().getDiningHall().addStudents(cardGroup6.getGameBoard().getBag().draw(5));
    }
}

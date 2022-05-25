package it.polimi.ingsw.model;

import it.polimi.ingsw.network.server.model.PlayerBoard;
import it.polimi.ingsw.network.server.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {
    PlayerBoard playerBoard;

    @BeforeEach
    void setUp() {
        playerBoard = new PlayerBoard();
    }

    @AfterEach
    void tearDown() {
        playerBoard = null;
    }

    /**
     * test of addStudentsToHall(), addStudentsToDiningHall(), moveStudents() and switchStudents() methods
     */
    @Test
    void manipulateStudentsInTheHallAndInTheDiningHall() {
        ArrayList<StudentColor> arrayListHall = new ArrayList<>();
        // fill the array of StudetColor that I'll put in the Hall
        int blueStudents = 2;
        for (int i=0; i<blueStudents; i++)
            arrayListHall.add(StudentColor.BLUE);
        int purpleStudents = 3;
        for (int i=0; i<purpleStudents; i++)
            arrayListHall.add(StudentColor.PURPLE);
        int yellowStudents;
        int redStudents;
        int greenStudents = 4;
        for (int i=0; i<greenStudents; i++)
            arrayListHall.add(StudentColor.GREEN);
        playerBoard.addStudentsToHall(arrayListHall);

        assertEquals(arrayListHall, playerBoard.getHall().getStudents());

        ArrayList<StudentColor> arrayListDiningHall = new ArrayList<>();
        // fill the array of StudetColor that I'll put in the DiningHall
        blueStudents = 7;
        for (int i=0; i<blueStudents; i++)
            arrayListDiningHall.add(StudentColor.BLUE);
        purpleStudents = 2;
        for (int i=0; i<purpleStudents; i++)
            arrayListDiningHall.add(StudentColor.PURPLE);
        yellowStudents = 6;
        for (int i=0; i<yellowStudents; i++)
            arrayListDiningHall.add(StudentColor.YELLOW);
        redStudents = 9;
        for (int i=0; i<redStudents; i++)
            arrayListDiningHall.add(StudentColor.RED);
        greenStudents = 3;
        for (int i=0; i<greenStudents; i++)
            arrayListDiningHall.add(StudentColor.GREEN);
        playerBoard.addStudentsToDiningHall(arrayListDiningHall);
        assertEquals(playerBoard.getDiningHall().getStudents(), arrayListDiningHall);

        arrayListDiningHall.clear();
        blueStudents = 2;
        for (int i=0; i<blueStudents; i++)
            arrayListDiningHall.add(StudentColor.BLUE);
        purpleStudents = 1;
        for (int i=0; i<purpleStudents; i++)
            arrayListDiningHall.add(StudentColor.PURPLE);
        playerBoard.moveStudents(arrayListDiningHall);

        assertEquals(0, playerBoard.getHall().countStudentColor(StudentColor.BLUE));
        assertEquals(2, playerBoard.getHall().countStudentColor(StudentColor.PURPLE));

        assertEquals(9, playerBoard.getDiningHall().countStudentColor(StudentColor.BLUE));
        assertEquals(3, playerBoard.getDiningHall().countStudentColor(StudentColor.PURPLE));

        arrayListHall.clear();
        for (int i=0; i<purpleStudents; i++)
            arrayListHall.add(StudentColor.PURPLE);
        greenStudents = 1;
        for (int i=0; i<greenStudents; i++)
            arrayListHall.add(StudentColor.GREEN);
        arrayListDiningHall.clear();
        for (int i=0; i<blueStudents; i++)
            arrayListDiningHall.add(StudentColor.BLUE);

        playerBoard.switchStudents(arrayListDiningHall, arrayListHall);

        assertEquals(2, playerBoard.getHall().countStudentColor(StudentColor.BLUE));
        assertEquals(1, playerBoard.getHall().countStudentColor(StudentColor.PURPLE));
        assertEquals(3, playerBoard.getHall().countStudentColor(StudentColor.GREEN));

        assertEquals(7, playerBoard.getDiningHall().countStudentColor(StudentColor.BLUE));
        assertEquals(4, playerBoard.getDiningHall().countStudentColor(StudentColor.PURPLE));
        assertEquals(4, playerBoard.getDiningHall().countStudentColor(StudentColor.GREEN));
    }

    /**
     * test of getCoins() and coinsUsage() methods
     */
    @Test
    void coinsUsage() {
        assertEquals(0, playerBoard.getCoins());

        assertEquals(0, playerBoard.getCoins());

        ArrayList<StudentColor> arrayList = new ArrayList<>();
        // fill the array of StudentColor that I'll put in the Hall
        int blueStudents = 2;
        for (int i=0; i<blueStudents; i++)
            arrayList.add(StudentColor.BLUE);
        int purpleStudents = 4;
        for (int i=0; i<purpleStudents; i++)
            arrayList.add(StudentColor.PURPLE);
        int yellowStudents = 0;
        int redStudents = 0;
        int greenStudents = 9;
        for (int i=0; i<greenStudents; i++)
            arrayList.add(StudentColor.GREEN);
        playerBoard.addStudentsToDiningHall(arrayList);

        playerBoard.assignCoins();

        assertEquals(4, playerBoard.getCoins());

        playerBoard.useCoins(1);
        assertEquals(3, playerBoard.getCoins());
    }
}
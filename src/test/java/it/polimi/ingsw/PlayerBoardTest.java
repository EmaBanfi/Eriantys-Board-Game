package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.NotEnoughMoneyException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

    @Test
    // test of addStudentsToHall, addStudentsToDiningHall, moveStudents, switchStudentsToH and switchStudentsToD methods
    void manipulateStudentsInTheHallAndInTheDiningHall() {
        PlayerBoard playerBoard = new PlayerBoard();

        ArrayList<StudentColor> arrayListHall = new ArrayList<>();
        // fill the array of StudetColor that I'll put in the Hall
        int blueStudents = 2;
        for (int i=0; i<blueStudents; i++)
            arrayListHall.add(StudentColor.BLUE);
        int purpleStudents = 3;
        for (int i=0; i<purpleStudents; i++)
            arrayListHall.add(StudentColor.PURPLE);
        int yellowStudents = 0;
        for (int i=0; i<yellowStudents; i++)
            arrayListHall.add(StudentColor.YELLOW);
        int redStudents = 0;
        for (int i=0; i<redStudents; i++)
            arrayListHall.add(StudentColor.RED);
        int greenStudents = 4;
        for (int i=0; i<greenStudents; i++)
            arrayListHall.add(StudentColor.GREEN);
        playerBoard.addStudentsToHall(arrayListHall);

        assertEquals(playerBoard.getHall().getStudents(), arrayListHall);

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

        playerBoard.switchStudentsToH(arrayListDiningHall);

        assertEquals(2, playerBoard.getHall().countStudentColor(StudentColor.BLUE));
        assertEquals(3, playerBoard.getHall().countStudentColor(StudentColor.PURPLE));

        assertEquals(7, playerBoard.getDiningHall().countStudentColor(StudentColor.BLUE));
        assertEquals(2, playerBoard.getDiningHall().countStudentColor(StudentColor.PURPLE));

        playerBoard.switchStudentsToD(arrayListDiningHall);

        assertEquals(0, playerBoard.getHall().countStudentColor(StudentColor.BLUE));
        assertEquals(2, playerBoard.getHall().countStudentColor(StudentColor.PURPLE));

        assertEquals(9, playerBoard.getDiningHall().countStudentColor(StudentColor.BLUE));
        assertEquals(3, playerBoard.getDiningHall().countStudentColor(StudentColor.PURPLE));
    }

    @Test
    // test of
    void coinsUsage() throws NotEnoughMoneyException {
        PlayerBoard playerBoard = new PlayerBoard();

        assertEquals(0, playerBoard.getCoins());

        assertEquals(0, playerBoard.getCoins());

        ArrayList<StudentColor> arrayList = new ArrayList<>();
        // fill the array of StudetColor that I'll put in the Hall
        int blueStudents = 2;
        for (int i=0; i<blueStudents; i++)
            arrayList.add(StudentColor.BLUE);
        int purpleStudents = 4;
        for (int i=0; i<purpleStudents; i++)
            arrayList.add(StudentColor.PURPLE);
        int yellowStudents = 0;
        for (int i=0; i<yellowStudents; i++)
            arrayList.add(StudentColor.YELLOW);
        int redStudents = 0;
        for (int i=0; i<redStudents; i++)
            arrayList.add(StudentColor.RED);
        int greenStudents = 9;
        for (int i=0; i<greenStudents; i++)
            arrayList.add(StudentColor.GREEN);
        playerBoard.addStudentsToDiningHall(arrayList);

        assertEquals(4, playerBoard.getCoins());

        playerBoard.useCoins(1);
        assertEquals(3, playerBoard.getCoins());
    }
}
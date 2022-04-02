package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiningHallTest {


    @Test
    void checkCoin() {
        DiningHall diningHall = new DiningHall();

        ArrayList<StudentColor> array = new ArrayList<>();
        for (int i=0; i<10; i++)
            array.add(StudentColor.BLUE);
        for (int i=0; i<6; i++)
            array.add(StudentColor.RED);
        for (int i=0; i<4; i++)
            array.add(StudentColor.PURPLE);
        array.add(StudentColor.YELLOW);
        for (int i=0; i<6; i++)
            array.add(StudentColor.GREEN);
        diningHall.addStudents(array);

        int coins = diningHall.checkCoin(StudentColor.BLUE);
        assertEquals(3, coins);

        coins = diningHall.checkCoin(StudentColor.RED);
        assertEquals(2, coins);

        coins = diningHall.checkCoin(StudentColor.PURPLE);
        assertEquals(1, coins);

        coins = diningHall.checkCoin(StudentColor.YELLOW);
        assertEquals(0, coins);

        diningHall.removeStudent(StudentColor.BLUE);
        int num = diningHall.countStudentColor(StudentColor.BLUE);
        assertEquals(7, num);

        num = diningHall.countStudentColor(StudentColor.RED);
        assertEquals(6, num);
        ArrayList<StudentColor> array2 = new ArrayList<>();
        array2.add(StudentColor.GREEN);
        diningHall.removeStudents(array2);
        num = diningHall.countStudentColor(StudentColor.GREEN);
        assertEquals(5, num);
        coins = diningHall.checkCoin(StudentColor.GREEN);
        assertEquals(1, coins);
    }
}
package it.polimi.ingsw.model;

import it.polimi.ingsw.model.DiningHall;
import it.polimi.ingsw.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiningHallTest {
    DiningHall diningHall;
    ArrayList<StudentColor> array;

    @BeforeEach
    void setUp() {
        diningHall = new DiningHall();
        array = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        diningHall = null;
        array = null;
    }

    /**
     * test of checkCoin method and the methods inherited from the Hall
     */
    @Test
    void checkCoin() {
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

    StudentColor color = null;

    @AfterEach
    void tearDown2() {
        color = null;
    }

    /**
     * first test of the method removeStudent()
     */
    @Test
    void removeStudent1() {
        color = StudentColor.BLUE;
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.RED);
        array.add(StudentColor.RED);
        array.add(StudentColor.GREEN);
        diningHall.addStudents(array);

        assertEquals(3, diningHall.removeStudent(color));
    }

    /**
     * second test of the method removeStudent()
     */
    @Test
    void removeStudent2() {
        color = StudentColor.BLUE;
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.RED);
        array.add(StudentColor.RED);
        array.add(StudentColor.GREEN);
        diningHall.addStudents(array);

        assertEquals(3, diningHall.removeStudent(color));
    }

    /**
     * third test of the method removeStudent()
     */
    @Test
    void removeStudent3() {
        color = StudentColor.RED;
        array.add(StudentColor.RED);
        array.add(StudentColor.RED);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.GREEN);
        diningHall.addStudents(array);

        assertEquals(2, diningHall.removeStudent(color));
    }

    /**
     * fourth test of the method removeStudent()
     */
    @Test
    void removeStudent4() {
        color = StudentColor.RED;
        array.add(StudentColor.RED);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.GREEN);
        diningHall.addStudents(array);

        assertEquals(1, diningHall.removeStudent(color));
    }

    /**
     * fifth test of the method removeStudent()
     */
    @Test
    void removeStudent5() {
        color = StudentColor.RED;
        array.add(StudentColor.BLUE);
        array.add(StudentColor.BLUE);
        array.add(StudentColor.GREEN);
        diningHall.addStudents(array);

        assertEquals(0, diningHall.removeStudent(color));
    }
}
package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Hall;
import it.polimi.ingsw.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HallTest {
    Hall hall;
    ArrayList<StudentColor> students;

    @BeforeEach
    void setup() {
        hall = new Hall();
        students = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        hall = null;
        students = null;
    }

    /**
     * test of the method countStudentColor()
     */
    @Test
    void countStudentColor() {
        for (int i=0; i<44; i++)
            students.add(StudentColor.BLUE);
        for (int i=0; i<4; i++)
            students.add(StudentColor.YELLOW);
        hall.addStudents(students);

        assertEquals(44, hall.countStudentColor(StudentColor.BLUE));
        assertEquals(4, hall.countStudentColor(StudentColor.YELLOW));
        assertEquals(0, hall.countStudentColor(StudentColor.PURPLE));
    }

    ArrayList<StudentColor> similar;

    @BeforeEach
    void setup2() {
        similar = new ArrayList<>();
    }

    @AfterEach
    void tearDown2() {
        similar = null;
    }

    /**
     * test of the method addStudents()
     */
    @Test
    void addStudents() {
        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.GREEN);

        similar.add(StudentColor.BLUE);
        similar.add(StudentColor.RED);
        similar.add(StudentColor.GREEN);

        hall.addStudents(students);

        assertEquals(hall.getStudents(), similar);
    }

    ArrayList<StudentColor> toDelete;

    @BeforeEach
    void setup3() {
        toDelete = new ArrayList<>();
    }

    @AfterEach
    void tearDown3() {
        toDelete = null;
    }

    /**
     * first test of the method removeStudents()
     */
    @Test
    void removeStudents1() {
        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.GREEN);
        hall.addStudents(students);

        toDelete.add(StudentColor.BLUE);
        toDelete.add(StudentColor.RED);

        similar.add(StudentColor.GREEN);

        hall.removeStudents(toDelete);

        assertEquals(hall.getStudents(), similar);
    }

    /**
     * second test of the method removeStudents()
     */
    @Test
    void removeStudents2() {
        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.GREEN);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.RED);
        students.add(StudentColor.RED);
        students.add(StudentColor.RED);
        hall.addStudents(students);

        toDelete.add(StudentColor.BLUE);
        toDelete.add(StudentColor.BLUE);
        toDelete.add(StudentColor.RED);
        toDelete.add(StudentColor.RED);
        toDelete.add(StudentColor.RED);
        toDelete.add(StudentColor.GREEN);

        similar.add(StudentColor.BLUE);
        similar.add(StudentColor.RED);
        similar.add(StudentColor.RED);

        hall.removeStudents(toDelete);

        assertEquals(hall.getStudents(), similar);
    }
}
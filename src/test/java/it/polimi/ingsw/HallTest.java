package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HallTest {

    @Test
    void addStudents() {
        Hall hall = new Hall();
        ArrayList<StudentColor> students = new ArrayList<>();
        ArrayList<StudentColor> similar = new ArrayList<>();

        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.GREEN);

        similar.add(StudentColor.BLUE);
        similar.add(StudentColor.RED);
        similar.add(StudentColor.GREEN);

        hall.addStudents(students);

        assertEquals(hall.getStudents(), similar);
    }

    @Test
    void removeStudents() {
        Hall hall = new Hall();
        ArrayList<StudentColor> students = new ArrayList<>();
        ArrayList<StudentColor> similar = new ArrayList<>();
        ArrayList<StudentColor> toDelete = new ArrayList<>();

        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.GREEN);
        hall.addStudents(students);

        toDelete.add(StudentColor.BLUE);
        toDelete.add(StudentColor.RED);

        similar.add(StudentColor.GREEN);

        hall.removeStudents(toDelete);

        assertEquals(hall.getStudents(), similar);

        // new test
        hall = new Hall();
        students = new ArrayList<>();
        similar = new ArrayList<>();
        toDelete = new ArrayList<>();

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

    @Test
    void removeStudent() {
        Hall hall = new Hall();
        ArrayList<StudentColor> students = new ArrayList<>();
        StudentColor color = StudentColor.BLUE;
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.RED);
        students.add(StudentColor.GREEN);
        hall.addStudents(students);

        assertEquals(3, hall.removeStudent(color));

        // new test
        hall = new Hall();
        students = new ArrayList<>();
        color = StudentColor.BLUE;
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.RED);
        students.add(StudentColor.RED);
        students.add(StudentColor.GREEN);
        hall.addStudents(students);

        assertEquals(3, hall.removeStudent(color));

        // new test
        hall = new Hall();
        students = new ArrayList<>();
        color = StudentColor.RED;
        students.add(StudentColor.RED);
        students.add(StudentColor.RED);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.GREEN);
        hall.addStudents(students);

        assertEquals(2, hall.removeStudent(color));

        // new test
        hall = new Hall();
        students = new ArrayList<>();
        color = StudentColor.RED;
        students.add(StudentColor.RED);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.GREEN);
        hall.addStudents(students);

        assertEquals(1, hall.removeStudent(color));

        // new test
        hall = new Hall();
        students = new ArrayList<>();
        color = StudentColor.RED;
        students.add(StudentColor.BLUE);
        students.add(StudentColor.BLUE);
        students.add(StudentColor.GREEN);
        hall.addStudents(students);

        assertEquals(0, hall.removeStudent(color));
    }

    @Test
    void countStudentColor() {
        Hall hall = new Hall();
        ArrayList<StudentColor> arrayList = new ArrayList<>();
        for (int i=0; i<44; i++)
            arrayList.add(StudentColor.BLUE);
        for (int i=0; i<4; i++)
            arrayList.add(StudentColor.YELLOW);
        hall.addStudents(arrayList);

        assertEquals(44, hall.countStudentColor(StudentColor.BLUE));
        assertEquals(4, hall.countStudentColor(StudentColor.YELLOW));
        assertEquals(0, hall.countStudentColor(StudentColor.PURPLE));
    }
}
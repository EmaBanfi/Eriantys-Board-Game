package it.polimi.ingsw.network.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    Cloud cloud;
    ArrayList<StudentColor> studentColors;

    @BeforeEach
    void setUp() {
        cloud = new Cloud(3);
        studentColors = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        cloud= null;
        studentColors= null;
    }

    @Test
    void addStudents(){
        for (int i=0; i<4;i++)
            studentColors.add(StudentColor.RED);
        cloud.addStudents(studentColors);
        assertEquals(3, cloud.getStudents().size());
        studentColors.clear();
        assertFalse(cloud.getStudents().isEmpty());
    }

    @Test
    void takeStudents(){
        ArrayList<StudentColor> studentsOnCloud = cloud.getStudents();
        ArrayList<StudentColor> studentsTakenFormCloud = cloud.takeStudents();
        assertTrue(studentsTakenFormCloud.containsAll(studentsOnCloud));
        assertTrue(cloud.getStudents().isEmpty());
    }

    @Test
    void addRemoveAndCount(){
        ArrayList<StudentColor> students = new ArrayList<>();
        for(StudentColor color: StudentColor.values()){
            cloud.addStudent(color);
            assertEquals(1, cloud.countStudentColor(color));
            students.add(color);
        }
        cloud.removeStudents(students);
        assertTrue(cloud.getStudents().isEmpty());
    }
}
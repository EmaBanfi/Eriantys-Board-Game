package it.polimi.ingsw.model;

import java.util.ArrayList;

public interface ManageStudents {
    /**
     * add the students to the ArrayList students
     * @param students ArrayList which must be added
     */
    void addStudents(ArrayList<StudentColor> students);

    /**
     * add one student to the ArrayList students
     * @param student the student that must be added to the ArrayList students
     */
    void addStudent(StudentColor student);

    /**
     * remove the students to the ArrayList students
     * @param students ArrayList which must be deleted
     */
    void removeStudents(ArrayList<StudentColor> students);

    /**
     * count the students of that specified color
     * @param color color of the students which must be counted
     * @return num of students for the specified color
     */
    int countStudentColor(StudentColor color);

    /**
     * get the ArrayList of students on that object
     * @return the ArrayList of students on that object
     */
    ArrayList<StudentColor> getStudents();
}

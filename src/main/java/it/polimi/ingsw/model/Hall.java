package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Hall {
    private final ArrayList<StudentColor> students;

    /**
     * create a new ArrayList of StudentColor
     */
    public Hall() {
        this.students = new ArrayList<>();
    }

    public ArrayList<StudentColor> getStudents() {
        return this.students;
    }

    /**
     * add the students to the ArrayList students
     * @param students: ArrayList which must be added
     */
    public void addStudents(ArrayList<StudentColor> students) {
        this.students.addAll(students);
    }

    public void addStudent(StudentColor student){students.add(student);}

    /**
     * remove the students to the ArrayList students
     * @param students: ArrayList which must be deleted
     */
    public void removeStudents(ArrayList<StudentColor> students) {
        for (StudentColor color : students)
            this.students.remove(color);
    }

    /**
     * remove three students from the DiningHall, useful for the effect of the character card n°12
     * @param color: the color of the three students (or less) that must be removed
     * @return the num of students deleted
     */
    public int removeStudent(StudentColor color) {
        int numOfStudents = countStudentColor(color);
        int count = 0;
        while (numOfStudents > 0 & count < 3) {
            students.remove(color);
            count++;
            numOfStudents--;
        }

        return count;
    }

    /**
     * count the students of that specified color
     * @param color: color of the students which must be counted
     * @return num of students for the specified color
     */
    public int countStudentColor(StudentColor color) {
        return (int) students.stream().filter(x -> x.equals(color)).count();
    }
}

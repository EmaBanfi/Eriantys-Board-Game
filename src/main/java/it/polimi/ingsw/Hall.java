package it.polimi.ingsw;

import java.util.ArrayList;

public class Hall {
    private final ArrayList<StudentColor> students;

    public Hall() {
        this.students = new ArrayList<>();
    }

    public ArrayList<StudentColor> getStudents() {
        return this.students;
    }

    public void addStudents(ArrayList<StudentColor> students) {
        this.students.addAll(students);
    }

    public void addStudent(StudentColor student){students.add(student);}

    public void removeStudents(ArrayList<StudentColor> students) {
        for (StudentColor color : students)
            this.students.remove(color);
    }

    // return the num of students deleted by the effect of the character card n°12 (used in the DiningHall)
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

    // return the num of students for a specified color
    public int countStudentColor(StudentColor color) {
        return (int) students.stream().filter(x -> x.equals(color)).count();
    }
}

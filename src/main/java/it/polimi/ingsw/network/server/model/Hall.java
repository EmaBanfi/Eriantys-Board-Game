package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;

public class Hall implements ManageStudents {
    private final ArrayList<StudentColor> students;

    public Hall() {
        students = new ArrayList<>();
    }

    public void addStudents(ArrayList<StudentColor> students) {
        this.students.addAll(students);
    }

    public void addStudent(StudentColor student){students.add(student);}

    public void removeStudents(ArrayList<StudentColor> students) {
        for (StudentColor color : students)
            this.students.remove(color);
    }

    public int countStudentColor(StudentColor color) {
        return (int) students.stream().filter(x -> x.equals(color)).count();
    }

    public ArrayList<StudentColor> getStudents() {
        return this.students;
    }
}

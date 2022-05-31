package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class CloudView {
    private final int maxStudents;
    private final ArrayList<StudentColor> students;

    public CloudView(int maxStudents, ArrayList<StudentColor> students) {
        this.maxStudents = maxStudents;
        this.students = students;
    }

    public int getMaxStudents(){
        return maxStudents;
    }

    public void addStudents(ArrayList<StudentColor> students) {
        this.students.addAll(students);
    }

    public void addStudent(StudentColor student){students.add(student);}

    public void removeStudents() {
        students.clear();
    }

    public int countStudentColor(StudentColor color) {
        return (int) students.stream().filter(x -> x.equals(color)).count();
    }

    public ArrayList<StudentColor> getStudents() {
        return this.students;
    }


}

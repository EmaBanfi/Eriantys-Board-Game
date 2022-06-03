package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class CloudView {
    private final ArrayList<StudentColor> students;

    public CloudView() {
        this.students = new ArrayList<>();
    }

    public void addStudents(ArrayList<StudentColor> students) {
        for(StudentColor color: students)
            this.students.add(color);
    }

    public void addStudent(StudentColor student){students.add(student);}

    public void removeStudents() {
        students.clear();
    }
    
    public ArrayList<StudentColor> getStudents() {
        return this.students;
    }


}

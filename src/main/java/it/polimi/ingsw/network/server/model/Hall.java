package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;

public class Hall implements ManageStudents {
    private final ArrayList<StudentColor> students;

    public Hall() {
        students = new ArrayList<>();
    }

    public void addStudents(ArrayList<StudentColor> students) {
        for(StudentColor studentColor: students){
            this.students.add(studentColor);
        }
    }

    public void addStudent(StudentColor student){students.add(student);}

    public void removeStudents(ArrayList<StudentColor> students) {
        for (StudentColor color : students)
            if(this.students.contains(color))
                this.students.remove(color);
    }

    public int countStudentColor(StudentColor color) {
        return (int) students.stream().filter(x -> x.equals(color)).count();
    }

    public ArrayList<StudentColor> getStudents() {
        ArrayList<StudentColor> studentColors = new ArrayList<>();
        for (StudentColor color: students){
            studentColors.add(color);
        }
        return studentColors;
    }
}

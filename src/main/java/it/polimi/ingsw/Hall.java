package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;

public class Hall {
    protected final Collection<StudentColor> students;

    public Hall() {
        this.students = new ArrayList<>();
    }

    public Collection<StudentColor> getStudents() {
        return students;
    }

    public void addStudentsColor(ArrayList<StudentColor> students) {
        this.students.addAll(students);
    }

    public void removeStudentsColor(StudentColor color) {
        this.students.remove(color);
    }

    // return the num of students for a specified color
    public int countStudentColor(StudentColor color) {
        return (int) students.stream().filter(x -> x.equals(color)).count();
    }
}

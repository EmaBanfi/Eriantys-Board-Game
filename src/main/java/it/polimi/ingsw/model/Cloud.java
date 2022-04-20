package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Cloud implements ManageStudents {
    private final int maxStudents;
    private final ArrayList<StudentColor> students;

    /**
     * instance a new cloud with its students ArrayList
     * @param maxStudents the maximum number of students that could stay on that Cloud
     */
    public Cloud(int maxStudents){
        students = new ArrayList<>();
        this.maxStudents = maxStudents;
    }

    public int getMaxStudents(){
        return maxStudents;
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



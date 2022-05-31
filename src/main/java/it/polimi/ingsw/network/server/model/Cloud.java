package it.polimi.ingsw.network.server.model;

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
        for(StudentColor studentColor : students){
            if(this.students.size()<maxStudents)
                this.students.add(studentColor);
            else
                break;
        }
    }

    public ArrayList<StudentColor> takeStudents(){
        ArrayList<StudentColor> studentColors=new ArrayList<>();
        for(StudentColor color: students){
            studentColors.add(color);
        }
        students.clear();
        return studentColors;
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
        for(StudentColor color: students){
            studentColors.add(color);
        }
        return studentColors;
    }

}



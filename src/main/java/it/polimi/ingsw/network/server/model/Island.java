package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;

public class Island implements ManageStudents {
    private Tower tower;
    private final ArrayList<StudentColor> students;
    private boolean blockCard;

    public Island() {
        students = new ArrayList<>();
        numOfTowers=1;
        blockCard=false;
        tower=null;
        ignoreTower=false;
    }

    /**
     * This attribute is used to implement the effect of the characterCard n°5.
     * If it's value is set to true then the majority must not be calculated on this island when it is reached by motherNature
     */


    public boolean isBlockCard() {
        return blockCard;
    }

    public boolean isIgnoreTower() {
        return ignoreTower;
    }

    private boolean ignoreTower;
    private int numOfTowers;

    public Tower getTower(){
        return tower;
    }

    public void setTower(Tower newTower){
        tower = newTower;
    }

    public void addBlockCard() {
        blockCard = true;
    }

    public void removeBlockCard() {
        blockCard = false;
    }

    public void setIgnoreTower(boolean value) {
        ignoreTower = value;
    }

    public int getNumOfTowers() {
        return numOfTowers;
    }

    public void addTower(int towers){
        numOfTowers++;
    }

    public void addStudents(ArrayList<StudentColor> studentsToAdd) {
        for(StudentColor studentColor: studentsToAdd){
            this.students.add(studentColor);
        }
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
        ArrayList<StudentColor> studentColors = new ArrayList<>();
        for (StudentColor color: students){
            studentColors.add(color);
        }
        return studentColors;
    }
}

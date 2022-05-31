package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.model.StudentColor;
import it.polimi.ingsw.network.server.model.Tower;

import java.util.ArrayList;

public class IslandView {
    private Tower tower;
    private ArrayList<StudentColor> students;
    private boolean ignoreTower;
    private int numOfTowers;
    private boolean blockCard;

    public IslandView(Tower tower, ArrayList<StudentColor> students, boolean ignoreTower, int numOfTowers, boolean blockCard) {
        this.tower = tower;
        this.students = students;
        this.ignoreTower = ignoreTower;
        this.numOfTowers = numOfTowers;
        this.blockCard = blockCard;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public void setStudents(ArrayList<StudentColor> students) {
        this.students = students;
    }

    public void setIgnoreTower(boolean ignoreTower) {
        this.ignoreTower = ignoreTower;
    }

    public void setNumOfTowers(int numOfTowers) {
        this.numOfTowers = numOfTowers;
    }

    public void setBlockCard(boolean blockCard) {
        this.blockCard = blockCard;
    }

    public Tower getTower() {
        return tower;
    }

    public ArrayList<StudentColor> getStudents() {
        return students;
    }

    public boolean isIgnoreTower() {
        return ignoreTower;
    }

    public int getNumOfTowers() {
        return numOfTowers;
    }

    public boolean isBlockCard() {
        return blockCard;
    }

    public void addStudent(StudentColor student){
        students.add(student);
    }
}

package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;
import java.util.HashMap;

public class IslandView {
    private HashMap<StudentColor, Integer> students;
    private boolean blockCard;
    private String tower;
    private int numOfTowers;

    public IslandView(){
        students = new HashMap<>();
        blockCard = false;
        tower = null;
        numOfTowers = 1;
    }

    public String getStudents(){
        String text = " ";
        for(StudentColor color : StudentColor.values()){
            text = text + color.toString().toLowerCase() + " students: " + students.get(color) + "\n";
        }
        return text;
    }

    public void addStudent(StudentColor student){
        int num = this.students.get(student);
        num++;
        this.students.put(student, num);
    }

    public void addStudents(ArrayList<StudentColor> students){
        for(StudentColor student : students) {
            int num = this.students.get(student);
            num++;
            this.students.put(student, num);
        }
    }

    public Integer getStudentsByColor(StudentColor color){
        return students.get(color);
    }

    public void addBlockCard(){
        blockCard = true;
    }

    public void removeBlockCard(){
        blockCard = false;
    }

    public boolean getBlockCard(){
        return blockCard;
    }

    public void setTower(String tower){
        this.tower = tower;
    }

    public String getTower(){
        return tower;
    }

    public void addTowers(int num){
        numOfTowers += num;
    }

    public int getNumOfTowers(){
        return numOfTowers;
    }

    public ArrayList<StudentColor> studentsOnIsland(){
        ArrayList<StudentColor> onIsland = new ArrayList<>();
        for(StudentColor student : StudentColor.values()){
            for(int i = 0; i<this.students.get(student); i++){
                onIsland.add(student);
            }
        }
        return onIsland;
    }
}

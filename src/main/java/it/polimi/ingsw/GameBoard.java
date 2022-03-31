package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class GameBoard {
    private MotherNature motherNature;
    private ArrayList<Cloud> clouds;
    private ArrayList<Island> islands;
    private StudentColor ignoreColor;
    private Bag bag;
    private ArrayList<Tower> towers;

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature(){
        return motherNature;
    }

    public GameBoard(int numberOfPlayers) {
        bag = new Bag();

        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            islands.add(new Island());

        motherNature = new MotherNature(islands);
        for (int i = 0; i < 12; i++)
            if (i != (motherNature.getCurrentIsland() + 6) % 12) {
                try {
                    islands.get(i).addStudents(bag.draw(2));
                } catch (LastStudentDrawnException e) {
                    e.printStackTrace();
                }
            }
        clouds = new ArrayList<>();
        int maxStudents;
        if (numberOfPlayers % 2 == 0)
            maxStudents = 3;
        else
            maxStudents = 4;
        for (int i = 0; i < numberOfPlayers; i++) {
            clouds.add(new Cloud(maxStudents));
        }

        towers = new ArrayList<>();

        ignoreColor = null;
    }



    public void mergeIslands(int first, int second) throws EndGameException{

        islands.get(first).addStudents(islands.get(second).getStudents());
        islands.get(first).addTower(islands.get(second).getNumOfTowers());
        islands.remove(second);
        if(islands.size()==3)throw new EndGameException();
    }

    public void addIgnoredColor(StudentColor color){
        ignoreColor = color;
    }

    public void resetIgnoredColors(){
        ignoreColor = null;
    }

    public void refillCloud(int cloud) throws LastStudentDrawnException{
            clouds.get(cloud).addStudents(bag.draw(clouds.get(cloud).getMaxStudents()));
        }

    public Collection<StudentColor> takeStudents(int cloud){
        return clouds.get(cloud).getStudents();
    }

    public void moveMotherNature(int movements){
        motherNature.move(movements);
    }

    public void addStudentsToIsland(HashMap <Integer, ArrayList<StudentColor>> studentsToAdd){
        for (Integer index: studentsToAdd.keySet()) {
            islands.get(index).addStudents(studentsToAdd.get(studentsToAdd.get(index)));
        }
    }

    public void setTower(Tower tower, int island){
        islands.get(island).setTower(tower);
    }

    public ArrayList<Tower> getTowers(){
        return towers;
    }
}

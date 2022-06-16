package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;
import java.util.Random;


public class MotherNature {
    private  ArrayList<Island> islands;
    private int currentIsland;

    public MotherNature(ArrayList<Island> gameBoardIslands){
        islands = gameBoardIslands;
        Random startIsland = new Random();
        currentIsland = startIsland.nextInt(12)  ;
    }

    public int getCurrentIsland(){
        return currentIsland;
    }


    public void move(int movements){
        currentIsland = (currentIsland + movements) % islands.size();
    }


    public ArrayList<Island> getIslands(){
        return islands;
    }

    /**
     * used to set the position of mother nature
     * @param isl index of the island ( first index is 0)
     */
    public void setCurrentIsland(int isl){
        currentIsland = isl;
    }
}

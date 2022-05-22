package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.model.Island;

import java.util.ArrayList;

public class MotherNatureView {
    private ArrayList<Island> islands;
    private int currentIsland;

    public MotherNatureView(ArrayList<Island> islands, int currentIsland) {
        this.islands = islands;
        this.currentIsland = currentIsland;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public void setIslands(ArrayList<Island> islands) {
        this.islands = islands;
    }

    public int getCurrentIsland() {
        return currentIsland;
    }

    public void setCurrentIsland(int currentIsland) {
        this.currentIsland = currentIsland;
    }

    public void move(int movements){
        currentIsland = (currentIsland + movements) % islands.size();
    }

    public void removeIsland(int island){
        islands.remove(island);
    }
}

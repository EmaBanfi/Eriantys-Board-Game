package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.EndGameException;

public class Tower {
    private final String towerColor;
    private int influencePoints;
    private int availableTowers;

    public Tower(String towerColor) {
        this.towerColor = towerColor;
        availableTowers=8;
    }
    public void setInfluencePoints(int points){
        influencePoints=points;
    }

    public void increaseAvailableTowers(int towers){
        availableTowers=availableTowers+towers;
    }
    public void decreaseAvailableTowers  (int towers)throws EndGameException{
        availableTowers=availableTowers-towers;
        if(availableTowers==0)
            throw new EndGameException();
    }

    public String getTowerColor() {
        return towerColor;
    }

    public int getInfluencePoints() {
        return influencePoints;
    }

    public int getAvailableTowers() {
        return availableTowers;
    }
}

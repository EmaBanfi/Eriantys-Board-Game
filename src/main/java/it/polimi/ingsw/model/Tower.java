package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.EndGameException;

public class Tower {
    private final String towerColor;
    /**
     * This attribute is thought for the fourPlayers mode
     * and it is equal to the sum of the influence of points of the teammates who share the same towers
     */
    private int influencePoints;
    private int availableTowers;

    public Tower(String towerColor) {
        this.towerColor = towerColor;
        availableTowers=8;
    }
    public void setInfluencePoints(int points){
        influencePoints=points;
    }

    /**
     * This method is used to increase the number of available towers
     * after an island or a group of islands has been conquered by another towerColor
     * @param towers: it is equal to the number of islands the have changed their tower color
     */
    public void increaseAvailableTowers(int towers){
        availableTowers=availableTowers+towers;
    }

    /**
     * This method is used to decrease the number of available towers
     * after an island or a group of island has been conquered by this towerColor
     * @param towers it is equal to the number of islands the have changed their tower color
     * @throws EndGameException when the number of available towers reaches 0
     */
    public void decreaseAvailableTowers  (int towers) throws EndGameException{
        availableTowers=availableTowers-towers;
        if(availableTowers<=0)
            throw new EndGameException("There are No more available "+ towerColor+" towers");
    }

    /*
    public String getTowerColor() {
        return towerColor;
    }
     */

    public int getInfluencePoints() {
        return influencePoints;
    }

    /*
    public int getAvailableTowers() {
        return availableTowers;
    }
     */

    /**
     * This method is used to increase the influence points of the tower
     * @param points: is the number of influence points added. It is equal to the influence points of a Player that owns this towerColor
     */
    public void addInfluencePoints(int points){
        influencePoints=influencePoints+points;
    }
}

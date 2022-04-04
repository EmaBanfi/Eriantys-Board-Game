package it.polimi.ingsw;

import java.util.ArrayList;

public class Island extends Hall {
    private Tower tower;
    /**
     * This attribute is used to implement the effect of the characterCard n°5.
     * If it's value is set to true then the majority must not be calculated on this island when it is reached by motherNature
     */
    private boolean blockCard;

    public boolean isBlockCard() {
        return blockCard;
    }

    public boolean isIgnoreTower() {
        return ignoreTower;
    }

    private boolean ignoreTower;
    private int numOfTowers;

    public Island() {
        super();
        numOfTowers=1;
        blockCard=false;
        tower=null;
        ignoreTower=false;
    }

    public Tower getTower(){
        return tower;
    }

    public void setTower(Tower newTower){
        tower = newTower;
    }

    public boolean getBlockedCard() {
        return blockCard;
    }

    public void addBlockCard() {
        blockCard = true;
    }

    public void removeBlockCard() {
        blockCard = false;
    }

    public void ignoreTower() {
        ignoreTower = true;
    }

    public int getNumOfTowers() {
        return numOfTowers;
    }

    public void addTower(int towers){
        numOfTowers++;
    }
}

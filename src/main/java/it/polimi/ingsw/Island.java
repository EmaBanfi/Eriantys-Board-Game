package it.polimi.ingsw;

import java.util.ArrayList;

public class Island extends Hall {
    private Tower tower;
    private boolean blockCard;
    private boolean ignoreTower;
    private int numOfTowers;

    public Island() {
        super();
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

package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;

public class Game {

    private int numOfPlayers;
    private ArrayList<Player> players;
    private boolean gameStarted;
    private Phase phase;
    private String mode;
    private boolean isEndGame;


    public Game() {
        isEndGame=false;
        phase=Phase.PLANNING;
        gameStarted=false;
    }


    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isEndGame() {
        return isEndGame;
    }

    public void setEndGame() {
        isEndGame = true;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public Phase getPhase() {
        return phase;
    }

    public String getMode() {
        return mode;
    }

    /**
     * This method is used to switch between planning and action phase
     */
    public void nextPhase(){
     if(phase.equals(Phase.PLANNING))
         phase=Phase.ACTION;
     else
         phase=Phase.PLANNING;
    }
}


package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game {
    private HashMap <StudentColor,Player> teachers;
    private int numOfPlayers;
    private ArrayList<Player> players;
    private boolean gameStarted;
    private Phase phase;
    private int mode;
    private boolean isEndGame;

    public Game() {
        isEndGame=false;
        phase=Phase.PLANNING;
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

    public int getMode() {
        return mode;
    }

    public Player getTeacher(StudentColor color) {
        return teachers.get(color);
    }

    public void setTeachers(HashMap<StudentColor, Player> teachers) {
        this.teachers = teachers;
    }

    public void nextPhase(){
     if(phase.equals(Phase.PLANNING))
         phase=Phase.ACTION;
     else
         phase=Phase.PLANNING;
    }
}


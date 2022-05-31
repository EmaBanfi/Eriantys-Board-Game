package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private int numOfPlayers;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private boolean gameIsSet;
    private String mode;
    private boolean lastSupportCardUsed;
    private boolean lastStudentDrawn;

    private HashMap<String,Integer> availableTowers;

    public Game() {
        lastSupportCardUsed =false;
        lastStudentDrawn =false;
        gameIsSet=false;
        players = new ArrayList<>();
    }

    /**
     * @return true if numOfPlayer and mode are set
     */
    public boolean gameIsSet(){
        return gameIsSet;
    }

    /**
     * set the current player as the player in position 0 of players
     */
    public void setCurrentPlayer(){
        currentPlayer= players.get(0);
    }


    /**
     * this method is used to set the number of students
     * @param num num of students
     */
    public void setNumOfPlayers(int num){
        numOfPlayers=num;
        gameIsSet=true;
    }


    public ArrayList<Player> getPlayers(){
        return players;
    }


    public boolean lastSupportCardUsed() {
        return lastSupportCardUsed;
    }

    public void setLastSupportCardUsed() {
        lastSupportCardUsed = true;
    }

    public void setLastStudentDrawn( boolean last){
        lastStudentDrawn = last;
    }

    public boolean lastStudentDrawn(){
        return lastStudentDrawn;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * @return true if the number of players connected is equal to numOfPlayers
     */
    public boolean gameIsFull(){
        return players.size()==numOfPlayers;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String m){
        mode=m;
    }

    /**
     * this method is used to determine the next player
     */
    public void nextPlayer(){
        if(lastPlayerOfRound())
            currentPlayer=players.get(0);
        else {
            int index=players.indexOf(currentPlayer);
            currentPlayer = players.get(index + 1);
        }
    }

    public boolean lastPlayerOfRound(){
        return players.indexOf(currentPlayer)==players.size()-1;
    }

    public boolean firstPlayerOfRound(){
        return players.indexOf(currentPlayer)==0;
    }

    public void setCurrentPlayer(Player player){
        currentPlayer=player;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean isExpertMode(){
        return mode.equals("expert");
    }

    public boolean isCurrentPlayer(String player){
        return currentPlayer.getNickName().equals(player);
    }

}


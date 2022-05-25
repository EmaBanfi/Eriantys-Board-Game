package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private int numOfPlayers;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private boolean gameStarted;
    private String mode;
    private boolean lastSupportCardUsed;
    private boolean lastStudentDrawn;

    private HashMap<String,Integer> availableTowers;

    public Game() {
        lastSupportCardUsed =false;
        lastStudentDrawn =false;
        gameStarted=false;
        players = new ArrayList<>();
    }


    /*public boolean gameIsReady(){
        return players.size()==numOfPlayers;
    }

     */


    public void setNumOfPlayers(int num){numOfPlayers=num;}

    public ArrayList<Player> getPlayers(){
        return players;
    }

    /*public boolean isGameStarted() {
        return gameStarted;
    }
     */

    /*public void setGameStarted() {
        gameStarted = true;
    }*/

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


    public String getMode() {
        return mode;
    }

    public void setMode(String m){
        mode=m;
    }

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


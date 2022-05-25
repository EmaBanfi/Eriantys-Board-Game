package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private int numOfPlayers;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private boolean gameStarted;
    private Phase phase;
    private String mode;
    private boolean lastSupportCardUsed;
    private boolean lastStudentDrawn;
    //
    // private ArrayList<String> availableDecks;

    private HashMap<String,Integer> availableTowers;

    public Game() {
        lastSupportCardUsed =false;
        lastStudentDrawn =false;
        phase=null;
        gameStarted=false;

    }

    /*private void initAvailableDecks(){
        availableDecks=new ArrayList<>();
        availableDecks.add("KingDeck");
        availableDecks.add("MageDeck");
        availableDecks.add("WitchDeck");
        availableDecks.add("SageDeck");
    }*/

    /*private void initAvailableTowers(){
        availableTowers= new HashMap<>();
        if(numOfPlayers==3){
            availableTowers.put("White",1);
            availableTowers.put("Black",1);
            availableTowers.put("Gray",1);
        }
        else {
            availableTowers.put("White",2);
            availableTowers.put("Black",2);
        }
    }*/

    public ArrayList<String> getAvailableTowers(){
        return new ArrayList<>(availableTowers.keySet());
    }

    public boolean gameIsReady(){
        return players.size()==numOfPlayers;
    }


    public void setNumOfPlayers(int num){numOfPlayers=num;}

    public ArrayList<Player> getPlayers(){
        return players;
    }
    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted() {
        gameStarted = true;
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

    public Phase getPhase() {
        return phase;
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

    public Player getPlayerByTurnOrder(int i){
        return players.get(i);
    }

    public boolean isCurrentPlayer(String player){
        return currentPlayer.getNickName().equals(player);
    }

}


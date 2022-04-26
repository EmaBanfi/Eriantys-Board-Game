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
    private ArrayList<String> availableDecks;

    private HashMap<String,Integer> availableTowers;

    public Game() {
        lastSupportCardUsed =false;
        lastStudentDrawn =false;
        phase=null;
        gameStarted=false;
        initAvailableDecks();
        initAvailableTowers();
    }

    private void initAvailableDecks(){
        availableDecks=new ArrayList<>();
        availableDecks.add("KingDeck");
        availableDecks.add("MagedDeck");
        availableDecks.add("WitchDeck");
        availableDecks.add("SageDeck");
    }

    private void initAvailableTowers(){
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
    }

    public ArrayList<String> getAvailableDecks(){return availableDecks;}

    public ArrayList<String> getAvailableTowers(){
        ArrayList<String> towers = new ArrayList<>(availableTowers.keySet());
        return towers;
    }

    public  void assignDeck(String deck){
        availableDecks.remove(deck);
    }

    public boolean gameIsReady(){
        return players.size()==numOfPlayers;
    }

    public void assignTower(String tower){
        if(availableTowers.get(tower)==2)
            availableTowers.put(tower,1);
        else if(availableTowers.get(tower)==1)
            availableTowers.remove(tower);
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

    public void setLastStudentDrawn(){
        lastStudentDrawn =true;
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
}


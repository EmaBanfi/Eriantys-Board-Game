package it.polimi.ingsw.communicationProtocolTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game {

    private final Server server;
    private int numOfPlayers = 2;
    private final ArrayList<String> players;

    public Game(Server server) {
        this.server = server;
        players = new ArrayList<>();
    }

    public void sendMessage(String message){
    }

    public void notifyAll (String message) {
        server.notifyAll(message);
    }

    public void setNumOfPlayers(int num){
        numOfPlayers = num;
    }

    public void addPlayers(String nick){
        players.add(nick);
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }

    public String answer() {
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        String str1 = "";

        try {
            str1 = kb.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str1;
    }

}

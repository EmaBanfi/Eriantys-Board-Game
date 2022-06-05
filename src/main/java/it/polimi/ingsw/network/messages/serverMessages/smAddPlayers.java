package it.polimi.ingsw.network.messages.serverMessages;

import java.util.ArrayList;

public class smAddPlayers extends ServerMessage{

    ArrayList<String> players;


    public smAddPlayers() {
        players = new ArrayList<>();
        setType("add players");
    }
    public void addNick(String nick){
        players.add(nick);
    }
}

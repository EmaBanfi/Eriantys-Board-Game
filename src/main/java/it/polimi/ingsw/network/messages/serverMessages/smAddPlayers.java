package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

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

    @Override
    public void processMessage(Client client) {
        client.getView().addPlayers(players);
    }
}

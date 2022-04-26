package it.polimi.ingsw.network.message.serverMessages;

import java.util.ArrayList;

public class smAskNickname extends ServerMessage{

    ArrayList<String> players;
    int numOfPlayers;
    String mode;

    public smAskNickname(ArrayList<String> players, int numOfPlayers, String mode) {
        this.players = players;
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
    }


}

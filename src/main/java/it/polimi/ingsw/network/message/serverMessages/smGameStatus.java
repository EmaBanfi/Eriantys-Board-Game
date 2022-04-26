package it.polimi.ingsw.network.message.serverMessages;

import java.util.ArrayList;

public class smGameStatus extends ServerMessage{
    ArrayList<String> nickname;
    int numOfPlayers;
    String mode;

    public smGameStatus(ArrayList<String> nickname, int numOfPlayers, String mode) {
        this.nickname = nickname;
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
    }
}

package it.polimi.ingsw.network.message.serverMessages;

import java.util.ArrayList;

public class smNotifyResults extends ServerMessage{
    ArrayList<String> winners;
    ArrayList<String> losers;

    public smNotifyResults(ArrayList<String> winners, ArrayList<String> losers) {
        this.winners = winners;
        this.losers = losers;
    }
}

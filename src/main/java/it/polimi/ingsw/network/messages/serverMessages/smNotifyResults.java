package it.polimi.ingsw.network.messages.serverMessages;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyResults extends ServerMessage {

    private ArrayList<String> winners;
    private ArrayList<String> losers;

    public smNotifyResults() {
    }

    public smNotifyResults(String message, ArrayList<String> winners, ArrayList<String> losers) {
        super(message);
        this.winners = winners;
        this.losers = losers;
        setType("notify results");
    }

}

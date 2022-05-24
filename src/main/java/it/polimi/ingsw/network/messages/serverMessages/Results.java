package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

import java.util.ArrayList;

public class Results extends ServerMessage {

    private ArrayList<String> winners;
    private ArrayList<String> losers;

    public Results() {
    }

    public Results(String message, ArrayList<String> winners, ArrayList<String> losers) {
        super(message);
        this.winners = winners;
        this.losers = losers;
        setType("results");
    }

    /**
     * print the message and call the method showGameResults() of the view sending it the winners and the losers
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().showGameResults(winners, losers);
    }
}

package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smAskGameStatus extends ServerMessage {

    public smAskGameStatus() {
    }

    public smAskGameStatus(String message) {
        super(message);
        setType("ask status");
    }

    @Override
    public void processMessage(Client client) {
        System.out.println(getMessage());

        client.askGameStatus();
    }
}

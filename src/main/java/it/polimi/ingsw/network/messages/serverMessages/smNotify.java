package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smNotify extends ServerMessage{

    public smNotify() {
    }

    public smNotify(String message) {
        super(message);
        setType("notify");
    }

    @Override
    public void processMessage(Client client) {
        System.out.println(getMessage());
    }

}
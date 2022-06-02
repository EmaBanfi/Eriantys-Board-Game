package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smNotify extends ServerMessage{

    public smNotify() {
    }

    public smNotify(String message) {
        super(message);
        setType("notify");
    }

    /**
     * print the message
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);
    }
}
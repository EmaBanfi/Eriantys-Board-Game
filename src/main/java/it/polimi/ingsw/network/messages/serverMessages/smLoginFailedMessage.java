package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smLoginFailedMessage extends ServerMessage{

    public smLoginFailedMessage() {
        super();
    }

    public smLoginFailedMessage(String message) {
        super(message);
        setType("login failed");
    }

    @Override
    public void processMessage(Client client) {
        System.out.println(getMessage());

        client.closeConnection();
    }
}

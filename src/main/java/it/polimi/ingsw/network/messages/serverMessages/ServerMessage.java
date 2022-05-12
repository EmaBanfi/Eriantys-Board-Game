package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

public class ServerMessage extends Message {

    private String message;

    public ServerMessage() {
    }

    public ServerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void processMessage(Client client) {
    }

}

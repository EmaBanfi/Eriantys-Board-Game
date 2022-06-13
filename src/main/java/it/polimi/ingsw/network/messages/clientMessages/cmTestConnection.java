package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmTestConnection extends ClientMessage {

    public cmTestConnection() {
        setType("test");
    }

    @Override
    public void processMessage(ClientHandler handler) {

    }
}

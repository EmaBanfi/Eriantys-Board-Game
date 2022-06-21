package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmDisconnect extends ClientMessage {


    public cmDisconnect() {
        setType("disconnect");
    }

    @Override
    public void processMessage(ClientHandler handler) {
        handler.closeClientConnection();
    }
}

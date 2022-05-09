package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public abstract class ClientMessage extends Message {

    public ClientMessage() {
        super();
    }

    public void processMessage(ClientHandler handler){

    }

}

package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmDisconnect extends ClientMessage {

    private String nick;

    public cmDisconnect() {
    }

    public cmDisconnect(String nick) {
        this.nick = nick;
        setType("disconnect");
    }

    @Override
    public void processMessage(ClientHandler handler) {
        handler.closeClientConnection(nick);
    }
}

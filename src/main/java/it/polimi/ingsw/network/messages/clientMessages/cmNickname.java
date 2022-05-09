package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class cmNickname extends ClientMessage {

    private String nick;

    public cmNickname() {
    }

    public cmNickname(String nick) {
        this.nick = nick;
        setType("nickname");
    }

    @Override
    public void processMessage(ClientHandler handler) {
        handler.addToGame(nick);
    }
}

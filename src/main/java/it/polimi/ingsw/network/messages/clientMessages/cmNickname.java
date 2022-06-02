package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmNickname extends ClientMessage {

    private String nick;

    public cmNickname() {
    }

    public cmNickname(String nick) {
        this.nick = nick;
        setType("nickname");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        handler.addToGame(nick);
    }
}

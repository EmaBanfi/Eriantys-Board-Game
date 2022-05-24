package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class Nickname extends ClientMessage {

    private String nick;

    public Nickname() {
    }

    public Nickname(String nick) {
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

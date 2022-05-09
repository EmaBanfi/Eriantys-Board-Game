package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class smClientFirstMessage {

    private String message;

    public smClientFirstMessage() {
    }

    public smClientFirstMessage(String message) {
        this.message = message;
    }

    public void processMessage(ClientHandler handler) {
        System.out.println(message);

        handler.addToGame(message);
    }

}

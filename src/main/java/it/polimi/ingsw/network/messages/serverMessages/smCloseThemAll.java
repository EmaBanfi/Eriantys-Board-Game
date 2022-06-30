package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smCloseThemAll extends ServerMessage {

    private String text;

    public smCloseThemAll() {
    }

    public smCloseThemAll(String text) {
        this.text = text;
        setType("closeAll");
    }

    @Override
    public void processMessage(Client client) {
        client.getView().closeGame(text, 0);
    }
}

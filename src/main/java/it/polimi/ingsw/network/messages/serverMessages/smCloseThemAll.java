package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smCloseThemAll extends ServerMessage {

    public smCloseThemAll() {
    }

    public smCloseThemAll(String message) {
        super(message);
        setType("closeAll");
    }

    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().closeGame();
    }
}

package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smAskGameStatus extends ServerMessage {

    public smAskGameStatus() {
    }

    public smAskGameStatus(String message) {
        super(message);
        setType("status");
    }

    /**
     * print on the screen the message and call the method askSetGameStatus() of the view
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().askSetGameStatus();
    }

}

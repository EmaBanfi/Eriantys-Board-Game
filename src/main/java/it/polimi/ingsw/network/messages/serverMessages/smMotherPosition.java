package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smMotherPosition extends ServerMessage {

    private int island;

    public smMotherPosition() {
    }

    public smMotherPosition(String message, int island) {
        super(message);
        this.island = island;
        setType("mother position");
    }

    /**
     * print the message and tell to the player the new position of mother nature
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updateMotherPosition(island);
    }
}

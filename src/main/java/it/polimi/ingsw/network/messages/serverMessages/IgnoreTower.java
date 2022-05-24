package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class IgnoreTower extends ServerMessage{

    private int island;
    private boolean ignored;

    public IgnoreTower() {
    }

    public IgnoreTower(String message, int island, boolean ignored) {
        super(message);
        this.island = island;
        this.ignored = ignored;
        setType("ignore tower");
    }

    /**
     * print the message and ignore (or reconsider) if on a specific island the tower must be ignored for the calculation of the influence
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        if (ignored)
            client.getView().ignoreTower(island);
        else
            client.getView().notIgnoreTower(island);
    }
}

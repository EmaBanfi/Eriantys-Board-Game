package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smBlockOnIsland extends ServerMessage{

    private int island;
    private boolean blocked;

    public smBlockOnIsland() {
    }

    public smBlockOnIsland(String message, int island, boolean blocked) {
        super(message);
        this.island = island;
        this.blocked = blocked;
        setType("block on island");
    }

    /**
     * print the message and block or unblock an island
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        if(blocked)
            client.getView().getViewController().blockIsland(island);
        else
            client.getView().getViewController().unlockIsland(island);
    }
}

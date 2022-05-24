package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class Merge extends ServerMessage {

    private int mergeToIsland;
    private int islandToMerge;

    public Merge() {
    }

    public Merge(String message, int mergeToIsland, int islandToMerge) {
        super(message);
        this.mergeToIsland = mergeToIsland;
        this.islandToMerge = islandToMerge;
        setType("merge");
    }

    /**
     * print the message and say to the player which islands has been merged
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().mergeIslands(islandToMerge, mergeToIsland);
    }
}

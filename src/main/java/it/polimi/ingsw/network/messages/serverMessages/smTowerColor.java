package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smTowerColor extends ServerMessage {

    private String color;
    private int island;

    public smTowerColor() {
    }

    public smTowerColor(String message, int island, String color) {
        super(message);
        this.color = color;
        this.island = island;
        setType("tower color");
    }

    /**
     * print the message and call the method updateTowerOnIsland() of the view sending it the island and the color of the tower
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updateTowerOnIsland(island, color);
    }
}

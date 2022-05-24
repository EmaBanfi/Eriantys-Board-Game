package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

import java.util.ArrayList;

public class PlayerOrder extends ServerMessage {

    private ArrayList<String> playerOrder;

    public PlayerOrder() {
    }

    public PlayerOrder(String message, ArrayList<String> playerOrder) {
        super(message);
        this.playerOrder = playerOrder;
        setType("player order");
    }

    /**
     * print the message and communicate to the player the player order
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updatePlayerOrder(playerOrder);
    }
}

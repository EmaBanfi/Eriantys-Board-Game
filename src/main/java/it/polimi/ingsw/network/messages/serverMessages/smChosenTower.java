package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smChosenTower extends ServerMessage {

    private String tower;

    public smChosenTower() {
    }

    public smChosenTower(String message, String tower) {
        super(message);
        this.tower = tower;
        setType("chosen tower");
    }

    /**
     * print the message and call the method updateTowerColor() of the view sending it the color of the tower and the name of the player who chose it
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().getViewController().updateTowerColor(tower);
    }
}

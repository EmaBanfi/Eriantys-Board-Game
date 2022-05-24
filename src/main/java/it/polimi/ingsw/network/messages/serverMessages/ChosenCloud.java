package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class ChosenCloud extends ServerMessage {

    private int cloud;

    public ChosenCloud() {
    }

    public ChosenCloud(String message, int cloud) {
        super(message);
        this.cloud = cloud;
        setType("chosen cloud");
    }

    /**
     * print the message and call the method updateEmptyCloud() of the view sending it the cloud chosen
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updateEmptyCloud(cloud);
    }
}

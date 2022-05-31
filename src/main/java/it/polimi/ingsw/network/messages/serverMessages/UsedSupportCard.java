package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class UsedSupportCard extends ServerMessage{

    private int supportCardId;
    private double additionalTurnOrder;

    public UsedSupportCard() {
    }

    public UsedSupportCard(String message, int supportCardId, double additionalTurnOrder) {
        super(message);
        this.supportCardId = supportCardId;
        this.additionalTurnOrder = additionalTurnOrder;
        setType("used support card");
    }

    /**
     * print the message and
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        if (additionalTurnOrder != 0)
            client.getView().setAdditionalTurnOrder(supportCardId, additionalTurnOrder);

        client.getView().showSupportCard(supportCardId);
    }
}

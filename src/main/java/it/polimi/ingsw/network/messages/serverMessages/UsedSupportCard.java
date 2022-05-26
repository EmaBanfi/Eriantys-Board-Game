package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class UsedSupportCard extends ServerMessage{

    private int supportCardId;
    boolean additionalTurnOrder;

    public UsedSupportCard() {
    }

    public UsedSupportCard(String message, int supportCardId, boolean additionalTurnOrder) {
        super(message);
        this.additionalTurnOrder = additionalTurnOrder;
        this.supportCardId = supportCardId;
        setType("used support card");
    }

    /**
     * print the message and
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        if (additionalTurnOrder)
            client.getView().setAdditionalTurnOrder(supportCardId);
        client.getView().showSupportCard(supportCardId);
    }
}

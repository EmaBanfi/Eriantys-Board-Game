package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smNotValidSupportCard extends ServerMessage {

    public smNotValidSupportCard() {
    }

    public smNotValidSupportCard(String message) {
        super(message);
        setType("not valid support card");
    }

    /**
     * print the message and
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().askSupportCard();
    }
}

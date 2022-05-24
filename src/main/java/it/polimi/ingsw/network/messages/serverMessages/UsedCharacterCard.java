package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class UsedCharacterCard extends ServerMessage {

    private int cardId;
    private boolean increasedPrice;

    public UsedCharacterCard() {
        super();
    }

    public UsedCharacterCard(String message, int cardId, boolean increasedPrice) {
        super(message);
        this.cardId = cardId;
        this.increasedPrice = increasedPrice;
        setType("used character card");
    }

    /**
     * print the message and communicate to the client the character card to be showed and, if the boolean increasedPrice is true, to increase the price
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().showCharacterCard(cardId);
        if(increasedPrice)
            client.getView().updateCharacterCardPrice(cardId);
    }
}

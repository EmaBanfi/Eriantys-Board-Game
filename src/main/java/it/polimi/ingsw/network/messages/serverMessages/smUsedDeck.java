package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smUsedDeck extends ServerMessage {

    private String deck;

    public smUsedDeck() {
    }

    public smUsedDeck(String message, String deck) {
        super(message);
        this.deck = deck;
        setType("used deck");
    }

    /**
     * print the message and
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().getViewController().setPlayerDeck(deck);
    }
}

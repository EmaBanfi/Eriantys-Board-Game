package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmDeck extends ClientMessage {

    private String deck;

    public cmDeck() {
    }

    public cmDeck(String deck) {
        this.deck = deck;
        setType("deck");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        handler.getServer().getController().setDeck(deck);
    }
}

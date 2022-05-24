package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class Deck extends ClientMessage {

    private String deck;

    public Deck() {
    }

    public Deck(String deck) {
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

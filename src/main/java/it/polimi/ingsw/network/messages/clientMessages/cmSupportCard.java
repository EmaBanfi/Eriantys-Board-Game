package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmSupportCard extends ClientMessage {

    private int id;

    public cmSupportCard() {
    }

    public cmSupportCard(int id) {
        this.id = id;
        setType("support card");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        handler.getServer().getController().setUsedSupportCard(id);
    }
}

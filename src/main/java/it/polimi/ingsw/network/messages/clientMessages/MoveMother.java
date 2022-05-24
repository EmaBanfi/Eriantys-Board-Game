package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

@SuppressWarnings("FieldCanBeLocal")
public class MoveMother extends ClientMessage {

    private int position;

    public MoveMother() {
    }

    public MoveMother(int position) {
        this.position = position;
        setType("move mother");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        handler.getServer().getController().moveMotherNature(position);
    }
}

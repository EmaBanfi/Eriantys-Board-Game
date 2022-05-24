package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class Tower extends ClientMessage {

    private String tower;

    public Tower() {
    }

    public Tower(String tower) {
        this.tower = tower;
        setType("tower");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        handler.getServer().getController().setTower(tower);
    }
}

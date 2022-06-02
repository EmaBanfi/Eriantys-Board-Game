package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmTower extends ClientMessage {

    private String tower;

    public cmTower() {
    }

    public cmTower(String tower) {
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

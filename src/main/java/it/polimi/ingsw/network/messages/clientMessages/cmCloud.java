package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmCloud extends ClientMessage{

    private int cloud;

    public cmCloud() {
    }

    public cmCloud(int cloud) {
        this.cloud = cloud;
        setType("cloud");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        handler.getServer().getController().setChosenCloud(cloud);
    }
}

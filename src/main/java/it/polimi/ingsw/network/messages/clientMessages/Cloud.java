package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class Cloud extends ClientMessage{

    private int cloud;

    public Cloud() {
    }

    public Cloud(int cloud) {
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

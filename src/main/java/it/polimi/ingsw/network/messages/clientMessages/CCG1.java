package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.model.StudentColor;

public class CCG1 extends ClientMessage{

    private int island;
    private StudentColor color;

    public CCG1() {
    }

    public CCG1(int island, StudentColor color) {
        this.island = island;
        this.color = color;
        setType("CCG1");
    }

    /**
     *
     * @param handler
     */
    @Override
    public void processMessage(ClientHandler handler) {

    }
}

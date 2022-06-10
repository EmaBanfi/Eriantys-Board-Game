package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Controller;
import it.polimi.ingsw.network.server.model.StudentColor;

public class cmCCG1 extends ClientMessage{

    private int island;
    private StudentColor color;

    public cmCCG1() {
    }

    public cmCCG1(int island, StudentColor color) {
        this.island = island;
        this.color = color;
        setType("CCG1");
    }

    /**
     * call the methods needed to apply the effect of the character card 1
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        Controller controller = handler.getServer().getController();
        controller.notifyUsedCharacterCard(1);

        controller.addStudentToIsland(island, color);

        controller.refillCard(1, 1);
        controller.resumeTurn();
    }
}

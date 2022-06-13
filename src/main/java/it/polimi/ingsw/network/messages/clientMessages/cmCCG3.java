package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Controller;

public class cmCCG3 extends ClientMessage {

    private int movements;

    public cmCCG3() {
    }

    public cmCCG3(int movements) {
        this.movements = movements;
        setType("CCG3");
    }

    /**
     * call the methods needed to apply the effect of the character card 4
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        Controller controller = handler.getServer().getController();
        controller.notifyUsedCharacterCard(4);

        controller.additionalMotherMovements(movements);
    }
}

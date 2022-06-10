package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Controller;
import it.polimi.ingsw.network.server.model.StudentColor;

public class cmCCG4 extends ClientMessage {

    private int cardId;
    private StudentColor color;

    public cmCCG4() {
    }

    public cmCCG4(int cardId, StudentColor color) {
        this.cardId = cardId;
        this.color = color;
        setType("CCG4");
    }

    /**
     * call the methods needed to apply the effect of the character card 9, 11 or 12
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        Controller controller = handler.getServer().getController();
        controller.notifyUsedCharacterCard(cardId);

        if (cardId == 9)
            controller.ignoreColor(color);

        else if (cardId == 11) {
            controller.addStudentToD(color);
            controller.refillCard(cardId, 1);
            controller.resumeTurn();
        }

        else
            controller.removeStudents(color);
    }
}

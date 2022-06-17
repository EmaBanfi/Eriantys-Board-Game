package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Controller;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class cmCCG6 extends ClientMessage{

    private int cardId;
    private ArrayList<StudentColor> toHall;
    private ArrayList<StudentColor> fromHall;

    public cmCCG6() {
    }

    public cmCCG6(int cardId, ArrayList<StudentColor> toHall, ArrayList<StudentColor> fromHall) {
        this.cardId = cardId;
        this.toHall = toHall;
        this.fromHall = fromHall;
        setType("CCG6");
    }

    /**
     * call the methods needed to apply the effect of the character card 7 or 10
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        Controller controller = handler.getServer().getController();
        controller.notifyUsedCharacterCard(cardId);

        if (cardId == 7)
            controller.switchCC7(toHall, fromHall);

        else {
            controller.moveStudentsDToH(toHall);
            controller.moveStudentsHToD(fromHall, false);
        }
    }
}

package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Controller;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class CCG6 extends ClientMessage{

    private int cardId;
    private ArrayList<StudentColor> exchangeStudents1;
    private ArrayList<StudentColor> exchangeStudents2;

    public CCG6() {
    }

    public CCG6(int cardId, ArrayList<StudentColor> exchangeStudents1, ArrayList<StudentColor> exchangeStudents2) {
        this.cardId = cardId;
        this.exchangeStudents1 = exchangeStudents1;
        this.exchangeStudents2 = exchangeStudents2;
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
            // per l'aggiunta dello studente alla carta se ne occupa la view
            // exchangeStudents1 va nella hall, exchangeStudents2 va sulla carta
            controller.switchCC7(exchangeStudents1, exchangeStudents2);

        else {
            controller.moveStudentsHToD(exchangeStudents1);
            controller.moveStudentsDToH(exchangeStudents2);
        }
    }
}

package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class cmStudentsMovementsHToD extends ClientMessage {

    private ArrayList<StudentColor> students;

    public cmStudentsMovementsHToD() {
    }

    public cmStudentsMovementsHToD(ArrayList<StudentColor> students) {
        this.students = students;
        setType("H to D");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        if(students==null)
            handler.getServer().getController().notifyNothingToDiningHall();
        else
            handler.getServer().getController().moveStudentsHToD(students);
    }
}

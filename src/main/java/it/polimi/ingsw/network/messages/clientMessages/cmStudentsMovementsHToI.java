package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;
import java.util.HashMap;

public class cmStudentsMovementsHToI extends ClientMessage {

    private HashMap<Integer, ArrayList<StudentColor>> students;

    public cmStudentsMovementsHToI() {
    }

    public cmStudentsMovementsHToI(HashMap<Integer, ArrayList<StudentColor>> students) {
        this.students = students;
        setType("H to I");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        for (int i = 0; i < 12; i++)
            handler.getServer().getController().moveStudentsHtoI(i, students.get(i));
    }
}

package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("FieldCanBeLocal")
public class cmStudentsMovementsHToI extends ClientMessage {

    private HashMap<Integer, ArrayList<StudentColor>> students;

    public cmStudentsMovementsHToI() {
    }

    public cmStudentsMovementsHToI(HashMap<Integer, ArrayList<StudentColor>> students) {
        this.students = students;
        setType("students movements H to I");
    }

}

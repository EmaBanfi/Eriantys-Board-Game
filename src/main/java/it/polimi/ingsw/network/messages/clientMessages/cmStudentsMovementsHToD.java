package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class cmStudentsMovementsHToD extends ClientMessage {

    private ArrayList<StudentColor> students;

    public cmStudentsMovementsHToD() {
    }

    public cmStudentsMovementsHToD(ArrayList<StudentColor> students) {
        this.students = students;
        setType("students movements H to D");
    }

}

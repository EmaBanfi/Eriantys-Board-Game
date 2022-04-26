package it.polimi.ingsw.network.message.clientMessages;



import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class cmStudentsMovementsHTol extends ClientMessage{
    ArrayList<StudentColor> students;

    public cmStudentsMovementsHTol(ArrayList<StudentColor> students) {
        this.students = students;
    }
}

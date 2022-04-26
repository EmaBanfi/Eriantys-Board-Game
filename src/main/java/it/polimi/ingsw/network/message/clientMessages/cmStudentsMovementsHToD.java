package it.polimi.ingsw.network.message.clientMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class cmStudentsMovementsHToD extends ClientMessage{
    ArrayList<StudentColor> students;

    public cmStudentsMovementsHToD(ArrayList<StudentColor> students) {
        this.students = students;
    }
}

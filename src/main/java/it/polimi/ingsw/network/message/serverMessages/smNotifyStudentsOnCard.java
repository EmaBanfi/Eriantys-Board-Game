package it.polimi.ingsw.network.message.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyStudentsOnCard extends ServerMessage{
    int cardId;
    ArrayList<StudentColor> students;

    public smNotifyStudentsOnCard(int cardId, ArrayList<StudentColor> students) {
        this.cardId = cardId;
        this.students = students;
    }
}

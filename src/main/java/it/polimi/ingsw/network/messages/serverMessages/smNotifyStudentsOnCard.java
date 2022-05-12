package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyStudentsOnCard extends ServerMessage{

    private int cardId;
    private ArrayList<StudentColor> students;

    public smNotifyStudentsOnCard() {
    }

    public smNotifyStudentsOnCard(String message, int cardId, ArrayList<StudentColor> students) {
        super(message);
        this.cardId = cardId;
        this.students = students;
        setType("notify students on card");
    }

}

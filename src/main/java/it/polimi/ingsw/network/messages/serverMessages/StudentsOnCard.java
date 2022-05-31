package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class StudentsOnCard extends ServerMessage{

    private int cardId;
    private ArrayList<StudentColor> students;
    private boolean added;

    public StudentsOnCard() {
    }


    public StudentsOnCard(String message, int cardId, ArrayList<StudentColor> students, boolean added) {
        super(message);
        this.cardId = cardId;
        this.students = students;
        this.added=added;
        setType("students on card");
    }

    public StudentsOnCard( int cardId, ArrayList<StudentColor> students, boolean added) {
        this.cardId = cardId;
        this.students = students;
        this.added=added;
        setType("students on card");
    }

    /**
     * print the message and call the method updateStudentsOnCard() of the view
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updateStudentsOnCard(cardId, students);
    }
}

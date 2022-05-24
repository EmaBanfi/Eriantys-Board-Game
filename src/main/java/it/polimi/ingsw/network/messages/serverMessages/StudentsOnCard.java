package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class StudentsOnCard extends ServerMessage{

    private int cardId;
    private ArrayList<StudentColor> students;

    public StudentsOnCard() {
    }


    public StudentsOnCard(String message, int cardId, ArrayList<StudentColor> students) {
        super(message);
        this.cardId = cardId;
        this.students = students;
        setType("students on card");
    }

    /**
     * print the message and call the method updateStudentsOnCard() of the view
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updatesStudentsOnCard(cardId, students);
    }
}

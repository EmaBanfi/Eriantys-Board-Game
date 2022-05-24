package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class StudentsInHall extends ServerMessage {

    private ArrayList<StudentColor> students;
    private boolean added;

    public StudentsInHall() {
    }

    public StudentsInHall(String message, ArrayList<StudentColor> students, boolean added) {
        super(message);
        this.students = students;
        this.added = added;
        setType("students in hall");
    }

    /**
     * print the message and call the method of the view that add or remove students from the hall
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        if (added)
            client.getView().getPlayer().addToHall(students);
        else
            client.getView().getPlayer().removeFromHall(students);
    }
}

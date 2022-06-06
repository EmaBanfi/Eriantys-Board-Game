package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class smStudentsInHall extends ServerMessage {

    private ArrayList<StudentColor> students;
    private boolean added;

    public smStudentsInHall() {
    }

    public smStudentsInHall(String message, ArrayList<StudentColor> students, boolean added) {
        super(message);
        this.students = students;
        this.added = added;
        setType("students in hall");
    }

    public smStudentsInHall(ArrayList<StudentColor> students, boolean added) {
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
        if(getMessage()!=null)
            super.processMessage(client);

        if (added)
            client.getView().addStudentsToHall(students);
        else
            client.getView().removeStudentsFromHall(students);
    }
}

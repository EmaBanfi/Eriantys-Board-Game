package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class smStudentsInHall extends ServerMessage {

    private ArrayList<StudentColor> students;
    private boolean added;
    private String nick = null;

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

    public smStudentsInHall(String message, String nick, ArrayList<StudentColor> students, boolean added) {
        super(message);
        this.nick = nick;
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
        else {
            if (nick != null)
                client.getView().removeFromPlayerHall(nick, students);
            else
                client.getView().removeStudentsFromHall(students);
        }
    }
}

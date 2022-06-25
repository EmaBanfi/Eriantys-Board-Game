package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class smStudentsInDiningHall extends ServerMessage {

    private String playerNick;
    private ArrayList<StudentColor> students;
    private boolean added;

    public smStudentsInDiningHall() {
    }

    public smStudentsInDiningHall(String message, String playerNick, ArrayList<StudentColor> students, boolean added) {
        super(message);
        this.playerNick = playerNick;
        this.students = students;
        this.added = added;
        setType("students in dining hall");
    }

    /**
     * print the message and call the method of the view that add or remove students from the dining hall
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        if (added)
            client.getView().getViewController().addStudentToPlayerD(playerNick, students);
        else {
            client.getView().getViewController().removeStudentsFromPlayerD(playerNick, students);
        }
    }
}

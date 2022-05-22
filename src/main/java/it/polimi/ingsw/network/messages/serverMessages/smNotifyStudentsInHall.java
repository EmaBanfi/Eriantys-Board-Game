package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyStudentsInHall extends ServerMessage {

    private String playerNick;
    private ArrayList<StudentColor> students;
    private boolean added;

    public smNotifyStudentsInHall() {
    }

    public smNotifyStudentsInHall(String message, String playerNick, ArrayList<StudentColor> students, boolean added) {
        super(message);
        this.playerNick = playerNick;
        this.students = students;
        this.added=added;
        setType("notify students in hall");
    }
}

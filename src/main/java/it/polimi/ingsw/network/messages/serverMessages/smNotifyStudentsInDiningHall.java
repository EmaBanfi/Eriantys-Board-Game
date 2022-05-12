package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyStudentsInDiningHall extends ServerMessage {

    private String playerNick;
    private ArrayList<StudentColor> students;

    public smNotifyStudentsInDiningHall() {
    }

    public smNotifyStudentsInDiningHall(String message, String playerNick, ArrayList<StudentColor> students) {
        super(message);
        this.playerNick = playerNick;
        this.students = students;
        setType("notify students in dining hall");
    }

}

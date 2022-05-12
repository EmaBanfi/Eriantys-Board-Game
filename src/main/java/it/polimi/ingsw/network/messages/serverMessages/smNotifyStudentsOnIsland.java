package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyStudentsOnIsland extends ServerMessage {

    private int island;
    ArrayList<StudentColor> students;

    public smNotifyStudentsOnIsland() {
    }

    public smNotifyStudentsOnIsland(String message, int island, ArrayList<StudentColor> students) {
        super(message);
        this.island = island;
        this.students = students;
        setType("notify students on island");
    }

}

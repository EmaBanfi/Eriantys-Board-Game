package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyStudentsOnCloud extends ServerMessage{

    private int cloud;
    private ArrayList<StudentColor> students;

    public smNotifyStudentsOnCloud(String message, int cloud, ArrayList<StudentColor> students) {
        super(message);
        this.cloud = cloud;
        this.students = students;
        setType("notify students on color");
    }

}

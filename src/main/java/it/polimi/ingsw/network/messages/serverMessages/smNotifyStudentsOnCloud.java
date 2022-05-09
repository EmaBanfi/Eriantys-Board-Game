package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyStudentsOnCloud extends ServerMessage{
    int cloud;
    ArrayList<StudentColor> students;

    public smNotifyStudentsOnCloud(int cloud, ArrayList<StudentColor> students) {
        this.cloud = cloud;
        this.students = students;
    }
}

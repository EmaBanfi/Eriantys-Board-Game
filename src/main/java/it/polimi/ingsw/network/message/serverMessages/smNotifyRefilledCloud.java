package it.polimi.ingsw.network.message.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyRefilledCloud extends ServerMessage{
    int cloud;
    ArrayList<StudentColor> students;

    public smNotifyRefilledCloud(int cloud, ArrayList<StudentColor> students) {
        this.cloud = cloud;
        this.students = students;
    }
}

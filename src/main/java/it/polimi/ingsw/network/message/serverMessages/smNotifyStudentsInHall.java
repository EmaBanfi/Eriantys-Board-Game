package it.polimi.ingsw.network.message.serverMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyStudentsInHall extends ServerMessage{
    String player;
    ArrayList<StudentColor> students;

    public smNotifyStudentsInHall(String player, ArrayList<StudentColor> students) {
        this.player = player;
        this.students = students;
    }
}

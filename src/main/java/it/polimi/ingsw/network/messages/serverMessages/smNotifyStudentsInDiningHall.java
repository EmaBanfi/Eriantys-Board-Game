package it.polimi.ingsw.network.messages.serverMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyStudentsInDiningHall extends ServerMessage{
    String player;
    ArrayList<StudentColor> students;

    public smNotifyStudentsInDiningHall(String player, ArrayList<StudentColor> students) {
        this.player = player;
        this.students = students;
    }
}

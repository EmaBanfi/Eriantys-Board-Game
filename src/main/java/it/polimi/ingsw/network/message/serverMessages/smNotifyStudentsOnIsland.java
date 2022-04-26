package it.polimi.ingsw.network.message.serverMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyStudentsOnIsland extends ServerMessage{
    int island;
     ArrayList<StudentColor> students;

    public smNotifyStudentsOnIsland(int island, ArrayList<StudentColor> students) {
        this.island = island;
        this.students = students;
    }
}

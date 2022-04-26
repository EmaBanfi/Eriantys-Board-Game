package it.polimi.ingsw.network.message.serverMessages;



import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyPlayerStudents extends ServerMessage{
    String player;
    ArrayList<StudentColor> hall;
    ArrayList<StudentColor> diningHall;

    public smNotifyPlayerStudents(String player, ArrayList<StudentColor> hall, ArrayList<StudentColor> diningHall) {
        this.player = player;
        this.hall = hall;
        this.diningHall = diningHall;
    }
}

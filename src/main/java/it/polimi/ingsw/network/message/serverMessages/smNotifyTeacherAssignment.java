package it.polimi.ingsw.network.message.serverMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

public class smNotifyTeacherAssignment extends ServerMessage{
    String player;
    StudentColor teacher;

    public smNotifyTeacherAssignment(String player, StudentColor teacher) {
        this.player = player;
        this.teacher = teacher;
    }
}

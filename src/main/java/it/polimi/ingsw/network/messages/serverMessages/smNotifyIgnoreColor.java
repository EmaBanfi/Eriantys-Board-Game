package it.polimi.ingsw.network.messages.serverMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

public class smNotifyIgnoreColor extends ServerMessage{
    StudentColor color;
    boolean ignored;

    public smNotifyIgnoreColor(StudentColor color, boolean ignored) {
        this.color = color;
        this.ignored = ignored;
    }
}

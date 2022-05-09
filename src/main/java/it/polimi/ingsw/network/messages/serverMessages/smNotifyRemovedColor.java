package it.polimi.ingsw.network.messages.serverMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

public class smNotifyRemovedColor extends ServerMessage{
    StudentColor color;

    public smNotifyRemovedColor(StudentColor color) {
        this.color = color;
    }
}

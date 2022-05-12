package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyRemovedColor extends ServerMessage{

    private StudentColor color;

    public smNotifyRemovedColor() {
    }

    public smNotifyRemovedColor(String message, StudentColor color) {
        super(message);
        this.color = color;
        setType("notify removed color");
    }

}

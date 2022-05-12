package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyIgnoreColor extends ServerMessage{

    private StudentColor color;
    private boolean ignored;

    public smNotifyIgnoreColor(String message, StudentColor color, boolean ignored) {
        super(message);
        this.color = color;
        this.ignored = ignored;
        setType("notify ignore color");
    }

}

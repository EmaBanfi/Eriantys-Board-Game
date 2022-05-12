package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.HashMap;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyTeacherAssignment extends ServerMessage {

    private HashMap<StudentColor, String> roles;

    public smNotifyTeacherAssignment() {
    }

    public smNotifyTeacherAssignment(String message, HashMap<StudentColor, String> roles) {
        super(message);
        this.roles = roles;
        setType("notify teacher assignment");
    }

}

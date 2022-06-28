package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.HashMap;

public class smTeacherAssignment extends ServerMessage {

    private HashMap<StudentColor, String> roles;

    public smTeacherAssignment() {
    }

    public smTeacherAssignment(String message, HashMap<StudentColor, String> roles) {
        super(message);
        this.roles = roles;
        setType("teacher assignment");
    }

    /**
     * print the message and call the method updateTeacher() of the views
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updateTeacher(roles);
    }
}

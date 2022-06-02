package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class smStudentsOnCloud extends ServerMessage{

    private int cloud;
    private ArrayList<StudentColor> students;

    public smStudentsOnCloud(String message, int cloud, ArrayList<StudentColor> students) {
        super(message);
        this.cloud = cloud;
        this.students = students;
        setType("students on cloud");
    }

    /**
     * print the message and call the method updatePlayerCoins() of the view sending it the coins taken
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().addStudentsOnCloud(cloud, students);
    }
}

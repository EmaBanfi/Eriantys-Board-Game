package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class smStudentsOnIsland extends ServerMessage {

    private int island;
    ArrayList<StudentColor> students;

    public smStudentsOnIsland() {
    }

    public smStudentsOnIsland(String message, int island, ArrayList<StudentColor> students) {
        super(message);
        this.island = island;
        this.students = students;
        setType("students on island");
    }

    public smStudentsOnIsland(int island, ArrayList<StudentColor> students) {
        this.island = island;
        this.students = students;
        setType("students on island");
    }


    /**
     * print the message and call the method of the view that add students to a specific island
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        if(getMessage()!=null)
            super.processMessage(client);

        client.getView().getViewController().addStudentsOnIsland(island , students);
    }
}

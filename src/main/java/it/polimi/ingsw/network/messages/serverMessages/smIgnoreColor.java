package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.StudentColor;

public class smIgnoreColor extends ServerMessage{

    private StudentColor color;
    private boolean ignored;

    public smIgnoreColor(String message, StudentColor color, boolean ignored) {
        super(message);
        this.color = color;
        this.ignored = ignored;
        setType("ignore color");
    }

    /**
     * print the message and call the method updateIgnoredColor(color) of the view sending it the color to ignore
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        if (ignored)
            client.getView().ignoreColor(color);
        else
            client.getView().notIgnoreColor(color);
    }
}

package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class LoginFailedMessage extends ServerMessage{
    
    public LoginFailedMessage(String message) {
        super(message);
        setType("login failed");
    }

    /**
     * print the message and call the method closeConnection() of the client
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.closeConnection();
    }
}

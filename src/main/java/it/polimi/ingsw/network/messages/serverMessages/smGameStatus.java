package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smGameStatus extends ServerMessage {

    private int numOfPlayers;
    private String mode;

    public smGameStatus() {
    }

    public smGameStatus( int numOfPlayers, String mode) {
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
    }

    /**
     * print the message and call the methods that sets the number of player and the mode
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {

        client.getView().updateGameStatus(numOfPlayers, mode);
    }
}

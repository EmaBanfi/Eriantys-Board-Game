package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class GameStatus extends ServerMessage {

    private int numOfPlayers;
    private String mode;

    public GameStatus() {
    }

    public GameStatus(String message, int numOfPlayers, String mode) {
        super(message);
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
    }

    /**
     * print the message and call the methods that sets the number of player and the mode
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().setNumOfPlayers(numOfPlayers);
        client.getView().setMode(mode);
    }
}

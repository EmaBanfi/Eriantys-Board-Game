package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmSetGameStatus extends ClientMessage {

    private int numOfPlayers;
    private String mode;

    public cmSetGameStatus() {
        super();
    }

    public cmSetGameStatus(int numOfPlayers, String mode) {
        super();
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
        setType("set status");
    }

    @Override
    public void processMessage(ClientHandler handler) {
        //handler.getServer().getController().setGameStatus(mode, numOfPlayers);
    }
}

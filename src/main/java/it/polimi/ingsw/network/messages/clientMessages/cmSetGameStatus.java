package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class cmSetGameStatus extends ClientMessage {

    private int numOfPlayers;
    private String mode;

    public cmSetGameStatus() {
    }

    public cmSetGameStatus(int numOfPlayers, String mode) {
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
        setType("set status");
    }

    /**
     *
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        handler.getServer().getController().setGameStatus(mode, numOfPlayers);

        System.out.println("DELETE LINES 27 -> 33 IN cmSetGameStatus");
        handler.getServer().getController().notifyPlayers();
        handler.getServer().getController().notifyStudentsOnIslands();
        if(handler.getServer().getController().getGame().isExpertMode())
            handler.getServer().getController().notifyAvailableCC();
        //game start
        handler.getServer().getController().notifyCurrentPlayer();
    }
}

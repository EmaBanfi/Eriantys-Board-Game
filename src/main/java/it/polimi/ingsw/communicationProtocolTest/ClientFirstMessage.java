package it.polimi.ingsw.communicationProtocolTest;

public class ClientFirstMessage {

    private String message;

    public ClientFirstMessage() {
    }

    public ClientFirstMessage(String message) {
        this.message = message;
    }

    public void processMessage(ClientHandler handler) {
        System.out.println(message);

        handler.addToGame(message);
    }

}

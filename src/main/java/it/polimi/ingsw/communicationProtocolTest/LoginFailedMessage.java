package it.polimi.ingsw.communicationProtocolTest;

public class LoginFailedMessage {

    String message;

    public LoginFailedMessage() {
    }

    public LoginFailedMessage(String message) {
        this.message = message;
    }

    public void processMessage(Client client) {
        System.out.println(message);

        client.closeConnection();
    }
}

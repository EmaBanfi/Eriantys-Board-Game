package it.polimi.ingsw.communicationProtocolTest;

public class LoginFailedMessage extends ServerMessage{



    public LoginFailedMessage() {
        super();
    }

    public LoginFailedMessage(String message) {
        super(message);
        setType("login failed");
    }

    @Override
    public void processMessage(Client client) {
        System.out.println(getMessage());

        client.closeConnection();
    }
}

package it.polimi.ingsw.communicationProtocolTest;

public class Message extends ServerMessage {


    public Message() {
        super();
    }

    public Message(String message) {
        super(message);
        setType("message");
    }

    @Override
    public void processMessage(Client client) {
        System.out.println(getMessage());

        client.send();
    }

}

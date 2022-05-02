package it.polimi.ingsw.communicationProtocolTest;

public class Message {

    private String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public void processMessage(Client client) {
        System.out.println(message);

        client.send();
    }

}

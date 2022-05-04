package it.polimi.ingsw.communicationProtocolTest;

public  class ServerMessage {
    private String type;
    private String message;

    public ServerMessage() {
    }

    public void setType(String type) {
        this.type=type;
    }

    public ServerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void processMessage(Client client){}
}

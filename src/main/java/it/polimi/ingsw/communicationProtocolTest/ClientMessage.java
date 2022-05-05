package it.polimi.ingsw.communicationProtocolTest;

public  class ClientMessage {
    private String type;

    public ClientMessage() {
    }

    public void setType(String type) {
        this.type=type;
    }


    public  void processMessage(Server server){}
}

package it.polimi.ingsw.network.messages;

public abstract class Message {

    private String type;

    public Message() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

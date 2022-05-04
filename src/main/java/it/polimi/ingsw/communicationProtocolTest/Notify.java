package it.polimi.ingsw.communicationProtocolTest;

public class Notify {

    private String notifyMessage;

    public Notify() {
    }

    public Notify(String message) {
        this.notifyMessage = message;
    }

    public void processMessage() {
        System.out.println(notifyMessage);
    }

}

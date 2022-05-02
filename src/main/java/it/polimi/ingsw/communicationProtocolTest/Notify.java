package it.polimi.ingsw.communicationProtocolTest;

public class Notify {

    private String message;

    public Notify() {
    }

    public Notify(String message) {
        this.message = message;
    }

    public void processMessage() {
        System.out.println(message);
    }

}

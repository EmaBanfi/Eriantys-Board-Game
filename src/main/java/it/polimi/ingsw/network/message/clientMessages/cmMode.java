package it.polimi.ingsw.network.message.clientMessages;

public class cmMode extends ClientMessage{
    String mode;

    public cmMode(String mode) {
        this.mode = mode;
    }
}

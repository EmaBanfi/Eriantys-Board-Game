package it.polimi.ingsw.network.messages.clientMessages;

public class cmTeammate extends ClientMessage{
    String mante;

    public cmTeammate(String mante) {
        this.mante = mante;
    }
}

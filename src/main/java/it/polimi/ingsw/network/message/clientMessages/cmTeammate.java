package it.polimi.ingsw.network.message.clientMessages;

public class cmTeammate extends ClientMessage{
    String mante;

    public cmTeammate(String mante) {
        this.mante = mante;
    }
}

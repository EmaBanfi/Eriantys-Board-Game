package it.polimi.ingsw.network.message.clientMessages;

public class cmNumOfPlayers extends ClientMessage{
    int num;

    public cmNumOfPlayers(int num) {
        this.num = num;
    }
}

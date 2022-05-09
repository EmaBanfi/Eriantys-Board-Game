package it.polimi.ingsw.network.messages.clientMessages;

public class cmTower extends ClientMessage{
    String tower;

    public cmTower(String tower) {
        this.tower = tower;
    }
}

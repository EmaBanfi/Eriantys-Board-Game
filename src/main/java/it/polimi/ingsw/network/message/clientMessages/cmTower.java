package it.polimi.ingsw.network.message.clientMessages;

public class cmTower extends ClientMessage{
    String tower;

    public cmTower(String tower) {
        this.tower = tower;
    }
}

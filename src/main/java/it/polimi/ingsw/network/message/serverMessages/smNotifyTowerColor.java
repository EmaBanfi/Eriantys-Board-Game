package it.polimi.ingsw.network.message.serverMessages;

public class smNotifyTowerColor extends ServerMessage{
    int island;
    String color;

    public smNotifyTowerColor(int island, String color) {
        this.island = island;
        this.color = color;
    }
}

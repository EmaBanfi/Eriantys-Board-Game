package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyTowerColor extends ServerMessage {

    private String color;
    private int island;

    public smNotifyTowerColor() {
    }

    public smNotifyTowerColor(String message, int island, String color) {
        super(message);
        this.color = color;
        this.island = island;
        setType("notify tower color");
    }

}

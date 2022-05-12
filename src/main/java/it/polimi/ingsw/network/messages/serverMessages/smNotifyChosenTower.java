package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyChosenTower extends ServerMessage{

    private String tower;

    public smNotifyChosenTower() {
    }

    public smNotifyChosenTower(String message, String tower) {
        super(message);
        this.tower = tower;
        setType("notify chosen tower");
    }

}

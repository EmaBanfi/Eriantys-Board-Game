package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyChosenTower extends ServerMessage{

    private String tower;
    private String player;

    public smNotifyChosenTower() {
    }

    public smNotifyChosenTower(String message, String tower,String player) {
        super(message);
        this.tower = tower;
        this.player=player;
        setType("notify chosen tower");
    }

}

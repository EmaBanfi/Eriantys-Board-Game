package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyChosenTower extends ServerMessage{

    public smNotifyChosenTower() {
        super();
    }

    public smNotifyChosenTower(String tower) {
        super(tower);
        setType("notify chosen tower");
    }


}

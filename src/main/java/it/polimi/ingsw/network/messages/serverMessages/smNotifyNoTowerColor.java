package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyNoTowerColor extends ServerMessage {

    public smNotifyNoTowerColor(String message) {
        super(message);
        setType("notify no tower color");
    }

}

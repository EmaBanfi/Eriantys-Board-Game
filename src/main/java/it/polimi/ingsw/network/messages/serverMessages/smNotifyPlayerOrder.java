package it.polimi.ingsw.network.messages.serverMessages;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyPlayerOrder extends ServerMessage{

    private ArrayList<String> playerOrder;

    public smNotifyPlayerOrder() {
    }

    public smNotifyPlayerOrder(String message, ArrayList<String> playerOrder) {
        super(message);
        this.playerOrder = playerOrder;
        setType("notify player order");
    }

}

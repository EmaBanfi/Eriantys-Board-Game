package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyNoMerge extends ServerMessage {

    public smNotifyNoMerge(String message) {
        super(message);
        setType("notify no merge");
    }

}

package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyNextRound extends ServerMessage {

    private Boolean nextRound;

    public smNotifyNextRound() {
    }

    public smNotifyNextRound(String message, Boolean nextRound) {
        super(message);
        this.nextRound = nextRound;
        setType("notify next round");
    }

}

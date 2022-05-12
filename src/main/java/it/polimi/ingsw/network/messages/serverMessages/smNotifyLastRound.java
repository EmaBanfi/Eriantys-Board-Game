package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyLastRound extends ServerMessage {

    private String cause;

    public smNotifyLastRound() {
    }

    public smNotifyLastRound(String message, String cause) {
        super(message);
        this.cause = cause;
        setType("notify last round");
    }

}

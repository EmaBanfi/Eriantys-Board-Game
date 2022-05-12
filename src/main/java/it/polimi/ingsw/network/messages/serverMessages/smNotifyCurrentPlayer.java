package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyCurrentPlayer extends ServerMessage{

    private String nickname;

    public smNotifyCurrentPlayer() {
    }

    public smNotifyCurrentPlayer(String message, String nickname) {
        super(message);
        this.nickname = nickname;
        setType("notify current player");
    }

}

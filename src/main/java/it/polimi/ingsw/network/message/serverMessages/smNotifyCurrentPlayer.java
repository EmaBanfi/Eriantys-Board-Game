package it.polimi.ingsw.network.message.serverMessages;

public class smNotifyCurrentPlayer extends ServerMessage{
    String nickname;

    public smNotifyCurrentPlayer(String nickname) {
        this.nickname = nickname;
    }
}

package it.polimi.ingsw.network.message.serverMessages;

public class smNotifyPlayerOrder extends ServerMessage{
    String nickname;

    public smNotifyPlayerOrder(String nickname) {
        this.nickname = nickname;
    }
}

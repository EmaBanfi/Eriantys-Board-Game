package it.polimi.ingsw.network.message.clientMessages;

public class cmNickname extends ClientMessage{
    String nickname;

    public cmNickname(String nickname) {
        this.nickname = nickname;
    }
}

package it.polimi.ingsw.network.message.serverMessages;

public class smNotifyUsedSupportCards extends ServerMessage{
    String playerNick;
    int cardId;

    public smNotifyUsedSupportCards(String playerNick, int cardId) {
        this.playerNick = playerNick;
        this.cardId = cardId;
    }
}

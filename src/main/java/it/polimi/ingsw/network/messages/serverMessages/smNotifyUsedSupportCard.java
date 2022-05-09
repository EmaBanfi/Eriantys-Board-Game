package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyUsedSupportCard extends ServerMessage{
    String playerNick;
    int cardId;

    public smNotifyUsedSupportCard(String playerNick, int cardId) {
        this.playerNick = playerNick;
        this.cardId = cardId;
    }
}

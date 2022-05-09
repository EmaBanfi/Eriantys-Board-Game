package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyUsedCHaracterCard extends ServerMessage{
    String Player;
    int cardId;
    int price;

    public smNotifyUsedCHaracterCard(String player, int cardId, int price) {
        Player = player;
        this.cardId = cardId;
        this.price = price;
    }
}

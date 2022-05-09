package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyBlocksOnCard extends ServerMessage{
    int cardId;
    int blocks;

    public smNotifyBlocksOnCard(int cardId, int blocks) {
        this.cardId = cardId;
        this.blocks = blocks;
    }
}

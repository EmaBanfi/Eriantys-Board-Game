package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyBlocksOnCard extends ServerMessage{

    private int cardId;
    private int blocks;

    public smNotifyBlocksOnCard() {
    }

    public smNotifyBlocksOnCard(String message, int cardId, int blocks) {
        super(message);
        this.cardId = cardId;
        this.blocks = blocks;
        setType("notify blocks on card");
    }

}

package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyBlocksOnCard extends ServerMessage{

    boolean decreasedBlocks;

    public smNotifyBlocksOnCard() {
    }

    public smNotifyBlocksOnCard( boolean decreasedBlocks) {
        this.decreasedBlocks = decreasedBlocks;
        setType("notify blocks on card");
    }

}

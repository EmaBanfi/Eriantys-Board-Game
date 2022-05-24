package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class BlockOnCard extends ServerMessage{

    private boolean decreasedBlocks;

    public BlockOnCard() {
    }

    public BlockOnCard(boolean decreasedBlocks) {
        this.decreasedBlocks = decreasedBlocks;
        setType("blocks on card");
    }

    /**
     * check if the player have to add o remove a block card from the character card 5
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        if(decreasedBlocks)
            client.getView().removeBlockOnCard();
        else
            client.getView().addBlockOnCard();
    }
}

package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smBlockOnCard extends ServerMessage{

    private boolean addBlock;

    public smBlockOnCard() {
    }

    public smBlockOnCard(boolean addBlock) {
        this.addBlock = addBlock;
        setType("blocks on card");
    }

    /**
     * check if the player have to add o remove a block card from the character card 5
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        client.getView().updateBlockOnCard(addBlock);
    }
}

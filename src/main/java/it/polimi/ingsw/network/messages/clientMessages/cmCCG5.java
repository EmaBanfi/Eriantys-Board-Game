package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Controller;

public class cmCCG5 extends ClientMessage{

    private int cardId;
    private int island;

    public cmCCG5() {
    }

    public cmCCG5(int cardId, int island) {
        this.cardId = cardId;
        this.island = island;
        setType("CCG5");
    }

    /**
     * call the methods needed to apply the effect of the character card 3, 5 or 6
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        Controller controller = handler.getServer().getController();
        controller.notifyUsedCharacterCard(cardId);

        if (cardId == 3)
            controller.additionalMajority(island);

        else if (cardId == 5)
            controller.setBlockOnIsland(island);

        else
            controller.setIgnoreTower(island);
    }
}

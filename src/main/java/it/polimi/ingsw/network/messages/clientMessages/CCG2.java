package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Controller;

public class CCG2 extends ClientMessage{

    private int cardId;

    public CCG2() {
    }

    public CCG2(int cardId) {
        this.cardId = cardId;
        setType("CCG2");
    }

    /**
     * call the methods needed to apply the effect of the character card 2 or 8
     * @param handler handler to which the message is sent
     */
    @Override
    public void processMessage(ClientHandler handler) {
        Controller controller = handler.getServer().getController();
        controller.notifyUsedCharacterCard(cardId);

        if (cardId == 2)
            controller.setBonusToPromotion();

        else
            controller.setAdditionalInfluencePoints();
    }
}

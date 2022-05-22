package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyUsedCharacterCard extends ServerMessage {

    private String playerNick;
    private int cardId;
    private boolean increasedPrice;

    public smNotifyUsedCharacterCard() {
        super();
    }

    public smNotifyUsedCharacterCard(String message, String playerNick, int cardId, boolean increasedPrice) {
        super(message);
        this.playerNick = playerNick;
        this.cardId = cardId;
        this.increasedPrice = increasedPrice;
        setType("notify used character card");
    }

}

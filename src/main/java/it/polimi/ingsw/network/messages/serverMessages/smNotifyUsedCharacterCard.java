package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyUsedCharacterCard extends ServerMessage {

    private String playerNick;
    private int cardId;
    private int price;

    public smNotifyUsedCharacterCard() {
        super();
    }

    public smNotifyUsedCharacterCard(String message, String playerNick, int cardId, int price) {
        super(message);
        this.playerNick = playerNick;
        this.cardId = cardId;
        this.price = price;
        setType("notify used character card");
    }

}

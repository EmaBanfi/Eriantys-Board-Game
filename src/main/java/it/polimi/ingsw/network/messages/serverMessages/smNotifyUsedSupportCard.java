package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyUsedSupportCard extends ServerMessage{

    private String playerNick;
    private int supportCardId;
    boolean additionalTurnOrder;

    public smNotifyUsedSupportCard() {
    }

    public smNotifyUsedSupportCard(String message, String playerNick, int supportCardId, boolean additionalTurnOrder) {
        super(message);
        this.additionalTurnOrder = additionalTurnOrder;
        this.playerNick = playerNick;
        this.supportCardId = supportCardId;
        setType("notify used support card");
    }

}

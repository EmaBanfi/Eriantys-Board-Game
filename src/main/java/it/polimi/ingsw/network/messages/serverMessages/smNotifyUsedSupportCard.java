package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyUsedSupportCard extends ServerMessage{

    private String playerNick;
    private int supportCardId;

    public smNotifyUsedSupportCard() {
    }

    public smNotifyUsedSupportCard(String message, String playerNick, int supportCardId) {
        super(message);
        this.playerNick = playerNick;
        this.supportCardId = supportCardId;
        setType("notify used support card");
    }

}

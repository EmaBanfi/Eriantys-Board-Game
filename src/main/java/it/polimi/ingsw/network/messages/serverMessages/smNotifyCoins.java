package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyCoins extends ServerMessage{

    private String playerNick;
    int coins;

    public smNotifyCoins() {
    }

    public smNotifyCoins(String message, String playerNick, int coins) {
        super(message);
        this.playerNick = playerNick;
        this.coins = coins;
        setType("notify coins");
    }

}

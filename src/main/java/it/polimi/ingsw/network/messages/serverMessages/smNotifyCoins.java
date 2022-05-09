package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyCoins extends ServerMessage{
    String player;
    int coins;

    public smNotifyCoins(String player, int coins) {
        this.player = player;
        this.coins = coins;
    }
}

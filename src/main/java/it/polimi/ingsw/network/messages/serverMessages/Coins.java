package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class Coins extends ServerMessage {

    int coins;

    public Coins() {
    }

    public Coins(String message, int coins) {
        super(message);
        this.coins = coins;
        setType("coins");
    }

    /**
     * print the message and call the method updatePlayerCoins() of the view sending it the coins taken
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updatePlayerCoins(coins);
    }
}

package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class CurrentPlayer extends ServerMessage{

    private String nickname;

    public CurrentPlayer() {
    }

    public CurrentPlayer(String message, String nickname) {
        super(message);
        this.nickname = nickname;
        setType("current player");
    }

    /**
     * print the message and call the method updateCurrentPlayer() of the view sending it the nickname of the current player
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().updateCurrentPlayer(nickname);
        if (client.getView().getPlayer().getNickname().equals(nickname))
            client.getView().resumeFrom();
    }
}

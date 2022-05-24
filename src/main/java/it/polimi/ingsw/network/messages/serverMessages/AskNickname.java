package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class AskNickname extends ServerMessage {

    public AskNickname() {
    }

    public AskNickname(String message) {
        super(message);
        setType("nickname");
    }

    /**
     * print the message and call the method askNickName() of the view
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {
        super.processMessage(client);

        client.getView().askNickName();
    }

}

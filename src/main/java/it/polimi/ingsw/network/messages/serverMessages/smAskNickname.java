package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smAskNickname extends ServerMessage {

    public smAskNickname() {
    }

    public smAskNickname(String message) {
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

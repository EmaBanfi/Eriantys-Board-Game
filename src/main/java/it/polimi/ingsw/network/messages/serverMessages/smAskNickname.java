package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smAskNickname extends ServerMessage {

    public smAskNickname() {
    }

    public smAskNickname(String message) {
        super(message);
        setType("ask nickname");
    }

    @Override
    public void processMessage(Client client) {
        System.out.println(getMessage());

        client.askNickname();
    }

}

package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smNotifyResumeTurn extends ServerMessage{

    public smNotifyResumeTurn() {
        setType("notify resume turn");
    }

    @Override
    public  void processMessage(Client client){}
}

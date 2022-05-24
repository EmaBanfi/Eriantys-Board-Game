package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class ResumeTurn extends ServerMessage {

    public ResumeTurn() {
        setType("resume turn");
    }

    @Override
    public void processMessage(Client client) {
        client.getView().resumeFrom();
    }
}

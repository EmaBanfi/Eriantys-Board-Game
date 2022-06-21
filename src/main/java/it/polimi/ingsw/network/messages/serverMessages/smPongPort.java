package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

public class smPongPort extends  ServerMessage{

    private  int port;


    public smPongPort() {
    }


    public smPongPort(int port) {
        this.port = port;
        setType("pong");
    }

    @Override
    public  void processMessage(Client client){
        client.startPing(port);
    }
}

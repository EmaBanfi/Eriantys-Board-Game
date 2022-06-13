package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.cmTestConnection;

public class ClientTimer extends Thread {

    Client client;

    public ClientTimer(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                System.out.println("An Excetion occured: " + e);
            }

            Gson gson = new Gson();
            cmTestConnection message = new cmTestConnection();
            String text = gson.toJson(message, cmTestConnection.class);
            client.send(text);
        }
    }
}

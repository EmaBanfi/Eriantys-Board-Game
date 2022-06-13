package it.polimi.ingsw.network.server;

public class ServerTimer extends Thread {

    private final ClientHandler handler;
    private int waitingTime = 20000;

    public ServerTimer(ClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
            System.out.println("An Excetion occured: " + e);
        }

        handler.manageDisconnection();
    }

    public void resetWaitingTime() {
        waitingTime = 20000;
    }
}

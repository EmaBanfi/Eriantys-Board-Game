package it.polimi.ingsw.network.server;

public class DummyServer extends Server{
    public DummyServer() {
        super();
    }

    @Override
    public void sendMessage(String nick, String message){
        System.out.println("To " + nick + ": " + message);
    }

    @Override
    public void sendAll( String message){
        System.out.println("To all: " + message);
    }

    @Override
    public void sendAllExceptPlayer(String nick, String message){
        System.out.println("To all except player " + nick + ": " + message);
    }
}

package it.polimi.ingsw.communicationProtocolTest;

public class Notify extends ServerMessage{



    public Notify() {
        super();
    }

    public Notify(String message) {
        super(message);
        setType("notify");
    }

    @Override
    public void processMessage(Client client) {
        System.out.println(getMessage());
    }


}

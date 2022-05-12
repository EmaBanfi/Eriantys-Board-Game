package it.polimi.ingsw.network.messages.clientMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

@SuppressWarnings("FieldCanBeLocal")
public class cmCCG1 extends ClientMessage{

    private int island;
    private StudentColor color;

    public cmCCG1() {
    }

    public cmCCG1(int island, StudentColor color) {
        this.island = island;
        this.color = color;
        setType("CCG1");
    }
}

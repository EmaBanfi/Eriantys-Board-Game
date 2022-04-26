package it.polimi.ingsw.network.message.clientMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

public class cmCCG1 extends ClientMessage{
    int island;
    StudentColor color;

    public cmCCG1(int island, StudentColor color) {
        this.island = island;
        this.color = color;
    }
}

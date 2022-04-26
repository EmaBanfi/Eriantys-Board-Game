package it.polimi.ingsw.network.message.clientMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class cmCCG6 extends ClientMessage{
    int cardId;
    ArrayList<StudentColor> exchangeStudents1;
    ArrayList<StudentColor> exchangeStudents2;

    public cmCCG6(int cardId, ArrayList<StudentColor> exchangeStudents1, ArrayList<StudentColor> exchangeStudents2) {
        this.cardId = cardId;
        this.exchangeStudents1 = exchangeStudents1;
        this.exchangeStudents2 = exchangeStudents2;
    }
}

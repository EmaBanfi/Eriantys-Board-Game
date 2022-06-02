package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG2;

public class CharacterCardGroup2 extends CharacterCard{

    public CharacterCardGroup2(int id, View view) {
        super(id, view);
        setPrice(2);
        if(id == 2)
            setText("During this turn, you can take control of a teacher even if you have the same amounts of students of the player that controls him in your Dining Hall");
        else
            setText("You have 2 more additional influence points during the majority count in this turn");
    }


    @Override
    public void activate(){
        cmCCG2 message;
        if(getCardId() == 2) {
            message = new cmCCG2(2);
        }
        else{
            message = new cmCCG2(8);
        }
        getView().getClient().send(new Gson().toJson(message, cmCCG2.class));
    }
}

package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.CCG2;

public class CharacterCardGroup2 extends CharacterCard{


    public CharacterCardGroup2(int id, int price, View view) {
        super(id, price, view);
        if(id == 2)
            setText("During this turn, you can take control of a teacher even if you have the same amounts of students of the player that controls him in your Dining Hall");
        else
            setText("You have 2 more additional influence points during the majority count in this turn");
    }


    @Override
    public void activate(){
        if(cardId == 2) {
            CCG2 message = new CCG2(2);
            getView().getClient().send(new Gson().toJson(message, CCG2.class));
        }
        else{
            CCG2 message = new CCG2(8);
            getView().getClient().send(new Gson().toJson(message, CCG2.class));
        }
    }
}

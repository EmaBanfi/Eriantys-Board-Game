package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG5;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CharacterCardGroup5 extends CharacterCard {
    private int availableBlockCards;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * create the CharacterCard 3, 5 or 6 and put on it 4 students
     */
    public CharacterCardGroup5(int id, View view) {
        super(id, view);
        if(getCardId() == 3){
            setText("You can choose an island and apply the majority count as if Mother Nature landed on that island." +
                    "Mother Nature will continue her normal movements after this effect");
            setPrice(3);
        }
        else if (getCardId() == 5) {
            this.availableBlockCards = 4;
            setText("At the start of the game, put 4 Block Card on top of this card; \n" +
                    "You can place a Block Card on an island: on the first time Mother Nature lands on that island," +
                    "put back the Block Card on top of this card and skip the majority count for that island");
            setPrice(2);
        }
        else{
            setText("The Towers won't affect the majority count on an island during this turn");
            setPrice(3);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 5 and the CharacterCard 6; at the end increase the price of the CharacterCard
     */
    public void activate() {
        System.out.println(getText());
        int islandChoice = getView().askIsland(true);
        ClientMessage message ;
        if(getCardId() == 3){
            message = new cmCCG5(3, islandChoice);
            getView().getClient().send(new Gson().toJson(message, cmCCG5.class));
        }
        else if(getCardId() == 5){
            message = new cmCCG5(5, getView().askIsland(true));
            getView().getClient().send(new Gson().toJson(message, cmCCG5.class));
        }
        else{
            message = new cmCCG5(6, islandChoice);
            getView().getClient().send(new Gson().toJson(message, cmCCG5.class));
        }
    }

    /**
     * used to add an block card on the CharacterCard n°5
     */
    @Override
    public void updateAvailableBlockCards(boolean update){
        if(update)
            availableBlockCards++;
        else
            availableBlockCards--;
    }

    public int getAvailableBlockCards() {
        return availableBlockCards;
    }
}

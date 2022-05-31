package it.polimi.ingsw.network.client;

import java.util.ArrayList;

public class CharacterCardDeck {
    private ArrayList<CharacterCard> allCards;
    private ArrayList<CharacterCard> availableCards;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public CharacterCardDeck(View view) {
        CharacterCardCreator creator = new CharacterCardCreator();
        int price = 1;
        for(int i = 1; i<13; i++){
            creator.createCard(i, price, view);
            if(price == 3){
                price = 1;
            }
            else
                price++;
        }
    }

    public ArrayList<CharacterCard> getAvailableCards(){
        return availableCards;
    }

    public CharacterCard getCardById(int id){
        for(CharacterCard c : availableCards){
            if(c.getCardId() == id)
                return c;
        }
        return null;
    }

    public void setAvailableCards(ArrayList<CharacterCard> availableCards) {
        this.availableCards = availableCards;
    }
}

package it.polimi.ingsw.network.server.model.CharacterCards;
import java.util.ArrayList;
import java.util.Random;

public class CharacterCardBoard {
    private final ArrayList<CharacterCard> deck;
    private final ArrayList<CharacterCard> availableCards;


    public CharacterCardBoard(){
        availableCards= new ArrayList<>();
        deck = new ArrayList<>();
        int price=1;
        for(int cardId = 1; cardId < 13; cardId ++){
            deck.add(new CharacterCard(cardId, price));
            if(price<3)
                price++;
            else
                price=1;
        }
    }


    public void initializeAvailableCC(){
        CharacterCard card;
        /*
        for(int i=1;i<4;i++){
            Random random= new Random();
            card = deck.get( random.nextInt(deck.size()));
            availableCards.add(card);
            deck.remove(card);
        }

         */

        card = deck.get(0);
        availableCards.add(card);
        card = deck.get(1);
        availableCards.add(card);
        card = deck.get(2);
        availableCards.add(card);
    }

    public ArrayList<CharacterCard> getAvailableCards(){
        return availableCards;
    }

    public CharacterCard getCharacterCard(int cardId){
        CharacterCard card=null;
        for(CharacterCard c: availableCards){
            if(c.cardId==cardId)
                card=c;
        }
        return card;
    }

    public ArrayList<CharacterCard> getAllCard(){
        return deck;
    }
}

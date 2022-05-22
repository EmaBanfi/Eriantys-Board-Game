package it.polimi.ingsw.network.server.model.CharacterCards;
import java.util.ArrayList;
import java.util.Random;

public class CharacterCardBoard {
    private ArrayList<CharacterCard> deck;
    private ArrayList<CharacterCard> availableCards;


    public CharacterCardBoard(){
        availableCards= new ArrayList<>();
        deck = new ArrayList<>();
        for(int cardId = 1; cardId < 13; cardId ++){
            for(int price = 1; price < 4; price ++)
                deck.add(new CharacterCard(cardId, price));
        }
        initializeAvailableCC();
    }


    public void initializeAvailableCC(){
        CharacterCard card;
        for(int i=1;i<4;i++){
            Random random= new Random();
            card = deck.get( random.nextInt(11));
            availableCards.add(card);
            deck.remove(card);
        }
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
}

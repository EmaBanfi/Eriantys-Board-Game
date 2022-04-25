package it.polimi.ingsw.network.server.model.CharacterCards;

import it.polimi.ingsw.network.server.model.GameBoard;

import java.util.ArrayList;
import java.util.Random;

public class CharacterCardDeck {
    private final ArrayList<CharacterCard> allCards =new ArrayList<>(12);
    private final ArrayList<CharacterCard> availableCards = new ArrayList<>();
    private GameBoard gameBoard;

    public CharacterCardDeck(){

    }
    public void initializeAvailableCC(){
        for(int i=1;i<4;i++){
            Random random= new Random();
            int card=0;
            if(allCards.size()>1){
                card= random.nextInt(allCards.size()-1);
            }
            availableCards.add(allCards.get(card));
            allCards.remove(allCards.get(card));
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

package it.polimi.ingsw.model.CharacterCards;


import it.polimi.ingsw.model.GameBoard;

public class CharacterCardGroup2 extends CharacterCard{


    @Override
    public  void activate(){
        if(cardId==2)
            currentPlayer.setAdditionalInfluencePoints(true);
        else
            currentPlayer.setBonusToPromotion(true);
        increasePrice();
    }
}

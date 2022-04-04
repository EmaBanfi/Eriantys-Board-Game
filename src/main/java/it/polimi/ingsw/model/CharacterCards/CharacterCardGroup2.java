package it.polimi.ingsw.model.CharacterCards;


public class CharacterCardGroup2 extends CharacterCard{

    public CharacterCardGroup2(){
        super();
    }


    @Override
    public  void activate(){
        if(cardId==2)
            currentPlayer.setAdditionalInfluencePoints(true);
        else
            currentPlayer.setBonusToPromotion(true);
        increasePrice();
    }
}

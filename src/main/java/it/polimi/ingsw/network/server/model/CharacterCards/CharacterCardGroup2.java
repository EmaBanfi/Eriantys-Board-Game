package it.polimi.ingsw.network.server.model.CharacterCards;


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

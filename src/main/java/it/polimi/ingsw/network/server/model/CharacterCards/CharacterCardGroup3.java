package it.polimi.ingsw.network.server.model.CharacterCards;

import it.polimi.ingsw.Exceptions.EndGameException;

public class CharacterCardGroup3 extends CharacterCard{

    private int chosenIsland;
    private RequiredInfo requiredInfo;

    public void activate(int movements) throws EndGameException {
        if(cardId == 3){
            gameBoard.assignInfluencePoints(gameBoard.getMotherNature().getCurrentIsland()+movements);
        }
        else
            gameBoard.moveMotherNature(movements);
    }
}

package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.GameBoard;

public class CharacterCardGroup5 extends CharacterCard {
    private int availableBlockCards;

    /**
     * create the CharacterCard 5 or 6 and put on it 4 students
     */
    public CharacterCardGroup5() {
        this.availableBlockCards = 4;
    }

    /**
     * implementation of the effect of the CharacterCard 5 and the CharacterCard 6; at the end increase the price of the CharacterCard
     * @param island: indicate an island and it is used in different ways, depend on the effect of each CharacterCard
     */
    public void activate(int island) {
        // effect of characterCard n°5
        if (cardId == 5) {
            gameBoard.getIsland(island).addBlockCard();
            this.availableBlockCards--;
        }

        // effect of characterCard n°6
        else if (cardId == 6)
            gameBoard.getIsland(island).setIgnoreTower(true);

        increasePrice();
    }

    /**
     * used to add an block card on the specified CharacterCard
     */
    public void addAvailableBlockCards() {
        this.availableBlockCards++;
    }

}

package it.polimi.ingsw.network.client.gui;
public class CharacterCardGroup5GUI extends CharacterCardGUI {
    private int availableBlockCards;

    /**
     * create the CharacterCard 3, 5 or 6 and put on it 4 students
     */
    public CharacterCardGroup5GUI(int id, GUI gui) {
        super(id, gui);
        if(getCardId() == 3){
            setText("You can choose an island and apply the majority count as if Mother Nature landed on that island." +
                    "Mother Nature will continue her normal movements after this effect");
            setPrice(3);
        }
        else if (getCardId() == 5) {
            this.availableBlockCards = 4;
            setText("You can place a Block token on an island: on the first time Mother Nature lands on that island," +
                    "the block token will come back on top of this card and skip the majority count for that island");
            setPrice(2);
        }
        else if (getCardId() == 6){
            setText("The Towers won't affect the majority count on an island during this turn");
            setPrice(3);
        }
    }

    /**
     * used to add a block token on the CharacterCard n°5
     */
    @Override
    public void updateAvailableBlockCards(boolean add){
        if(add)
            availableBlockCards++;
        else
            availableBlockCards--;
    }

    @Override
    public boolean checkCCPrecondition() {

        String text = "Card "+getCardId()+ " can't be activated because";

        if (getGUI().getViewController().getMainPlayer().getCoins() < getPrice()) {
            System.out.println(text + " you don't have enough coins\n");

            return  false;
        }

        if (getCardId() == 5 && availableBlockCards == 0){
            System.out.println(text + " there are 0 block tokens on card\n");

            return  false;
        }

        return true;
    }
}

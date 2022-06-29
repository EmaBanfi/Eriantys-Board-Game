package it.polimi.ingsw.network.client.gui;
public class CharacterCardGroup3GUI extends CharacterCardGUI {

    public CharacterCardGroup3GUI(int id, GUI gui) {
        super(id, gui);
        setPrice(1);
        setText("You can move up to 2 more islands in this turn");
    }

    @Override
    public boolean checkCCPrecondition() {

        if (getGUI().getViewController().getMainPlayer().getCoins() < getPrice()) {
            System.out.println("Card " + getCardId() + " can't be activated because you don't have enough coins\n");

            return  false;
        }

        return true;
    }
}

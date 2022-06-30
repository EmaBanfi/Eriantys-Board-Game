package it.polimi.ingsw.network.client.gui;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class CharacterCardGroup4GUI extends CharacterCardGUI {
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();

    public CharacterCardGroup4GUI(int id, GUI gui) {
        super(id, gui);
        if (getCardId() == 9) {
            setText("Choose a student color: the students of that color wont affect the majority count during this turn");
            setPrice(3);
        }
        else if (getCardId() == 11) {
            setText("You can choose one student from this card and place it in your dining hall");
            setPrice(2);
        }
        else if (getCardId() == 12){
            setText("Choose a student color: every player has to put back in the bag 3 students of the same color from the dining hall. " +
                    "Whoever has less than 3 students of the specified color, will put all the remaining students of that color ");
            setPrice(3);
        }
    }

    /**
     * updates the students on card
     * @param students students to manage
     * @param add add the block card if it's true, else remove
     */
    @Override
    public void updateStudentsOnCard(ArrayList<StudentColor> students, boolean add){
        if(add)
            studentsOnCard.addAll(students);
        else
            studentsOnCard.removeAll(students);
    }

    @Override
    public boolean checkCCPrecondition() {

        String text = "Card "+getCardId()+ " can't be activated because";

        if (getGUI().getViewController().getMainPlayer().getCoins() < getPrice()) {
            text = text + " you don't have enough coins.";
            setCause(text);

            return  false;
        }

        if (getCardId() == 11 && studentsOnCard.isEmpty()) {
            text = text + " there no students on card.";
            setCause(text);

            return  false;
        }

        return true;
    }
}

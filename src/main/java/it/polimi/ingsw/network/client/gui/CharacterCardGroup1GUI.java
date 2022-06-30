package it.polimi.ingsw.network.client.gui;
import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class CharacterCardGroup1GUI extends CharacterCardGUI {
    private final ArrayList<StudentColor> studentsOnCard;
    private final ArrayList<IslandView> islands;

    /**
     * create a new CharacterCard n°1 and put on it 4 students; at the end increase the price of the CharacterCard
     */
    public CharacterCardGroup1GUI(int id, GUI gui) {
        super(id, gui);
        setPrice(1);
        this.studentsOnCard = new ArrayList<>();
        islands = getGUI().getViewController().getAvailableIslands();
        setText("You can take one student from this card and place it on an island at your choice.");
    }

    public ArrayList<StudentColor> getStudentsOnCard(){
        return studentsOnCard;
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

            return false;
        }

        if (studentsOnCard.isEmpty()) {
            text = text + " there are not enough students on card.";
            setCause(text);

            return false;
        }

        return true;
    }
}

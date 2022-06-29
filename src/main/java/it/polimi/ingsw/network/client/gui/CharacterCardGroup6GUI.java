package it.polimi.ingsw.network.client.gui;
import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;

public class CharacterCardGroup6GUI extends CharacterCardGUI {
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();
    private final PlayerView player = getGUI().getViewController().getMainPlayer();

    public CharacterCardGroup6GUI(int id, GUI gui) {
        super(id, gui);
        if(getCardId() == 7) {
            setText("You can take up to 3 students from this card and change them with the same amount of students in your Hall");
            setPrice(1);
        }
        else if (getCardId() == 10){
            setText("You can swap up to 2 students from your Hall to your Dining Hall");
            setPrice(1);
        }
    }

    public int countStudentColor(StudentColor color) {
        return (int) studentsOnCard.stream().filter(x -> x.equals(color)).count();
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

    /**
     * used to check if a card can be activated
     * @return true if the card can be activated
     */
    @Override
    public boolean checkCCPrecondition() {

        String text = "Card "+getCardId()+ " can't be activated because";

        if (getGUI().getViewController().getMainPlayer().getCoins() < getPrice()) {
            System.out.println(text + " you don't have enough coins\n");

            return  false;
        }

        if (getCardId() == 7)
            if (studentsOnCard.isEmpty()) {
                System.out.println(text + " there are no students on card\n");

                return  false;
            }

        if (getCardId()==10 && player.getDiningHall().isEmpty())
                if (studentsOnCard.isEmpty()) {
                    System.out.println(text + " there are no students in your dining hall\n");

                    return  false;
                }

        if(player.getHall().isEmpty()){
            System.out.println(text + " there are no students in your hall\n");

            return  false;
        }

        return true;
    }
}

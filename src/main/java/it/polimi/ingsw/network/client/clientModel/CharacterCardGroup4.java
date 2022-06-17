package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG4;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterCardGroup4 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();

    public CharacterCardGroup4(int id, CLI cli) {
        super(id, cli);
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
     * implementation of the effect of the CharacterCard 9, the CharacterCard 11 and the CharacterCard 12; at the end increase the price of the CharacterCard
     *
     * @return true if the card has been activated
     */
    public boolean activate(){
        if (getCardId() == 9) {
            getCLI().showIslands(getCLI().getMainPlayer().getUsedSupportCard().getMovement());
            if (getCLI().getResumeFrom().equals(Phase.CHOOSE_CLOUDS))
                System.out.println("Majority has already been calculated");
            if (confirmActivation())
                return false;

            System.out.println("\nSelect ignored student color");

            cmCCG4 message = new cmCCG4(9, askStudent());
            getCLI().getClient().send(new Gson().toJson(message, cmCCG4.class));
        }

        else if (getCardId() == 11) {
            System.out.println("Students on card: " + studentsOnCard);
            System.out.println("Students in hall: " + getCLI().getMainPlayer().getHall());

            if (confirmActivation())
                return false;

            System.out.println("Please select a student to move to your hall");
            StudentColor color;
            do {
                color = askStudent();
                if (!studentsOnCard.contains(color))
                    System.out.println("There is no " + color + " student on card");
            } while (!studentsOnCard.contains(color));

            getCLI().getMainPlayer().addToHall(color);

            cmCCG4 message = new cmCCG4(11, color);
            getCLI().getClient().send(new Gson().toJson(message, cmCCG4.class));
        }

        else if (getCardId() == 12) {
            getCLI().showAllDH();

            if (confirmActivation())
                return false;

            System.out.println("Choose which student color you want to remove");

            cmCCG4 message = new cmCCG4(12, askStudent());
            getCLI().getClient().send(new Gson().toJson(message, cmCCG4.class));
        }

        return true;
    }

    private StudentColor askStudent() {
        String colorChoice = "";
        StudentColor color;
        do {
            colorChoice = getInput().nextLine();

            color = StudentColor.getStudentFromString(colorChoice);

            if (color == null)
                System.out.println("Not a student color");
        } while (color == null);

        return color;
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

        if (getCLI().getMainPlayer().getCoins() < getPrice()) {
            System.out.println(text + " you don't have enough coins\n");

            return  false;
        }

        if (getCardId() == 11 && studentsOnCard.isEmpty()) {
            System.out.println(text + " there no students on card\n");

            return  false;
        }

        return true;
    }
}

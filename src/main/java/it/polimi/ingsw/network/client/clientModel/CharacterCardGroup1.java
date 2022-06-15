package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG1;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharacterCardGroup1 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard;
    private final ArrayList<IslandView> islands;

    /**
     * create a new CharacterCard n°1 and put on it 4 students; at the end increase the price of the CharacterCard
     */
    public CharacterCardGroup1(int id, CLI cli) {
        super(id, cli);
        setPrice(1);
        this.studentsOnCard = new ArrayList<>();
        islands = getCLI().getAvailableIslands();
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

    /**
     * implement the effect of the characterCard n°1
     *
     * @return true if the card has been activated
     */
    @Override
    public boolean activate() {
        getCLI().showIslands(null);
        System.out.println("Students on card: " + studentsOnCard);

        if (confirmActivation())
            return false;

        StudentColor color;

        System.out.println("Choose the student to move to an island");
        String studentChoice = "";
        boolean isPresent;
        do {
            isPresent = false;

            try {
                studentChoice = getBr().readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            color = StudentColor.getStudentFromString(studentChoice);

            if (color != null) {
                if (studentsOnCard.contains(color))
                    isPresent = true;

                if (!isPresent)
                    System.out.println("There is no " + color.toString().toLowerCase() + " student on card");
            }
            else {
                System.out.println("Not a color");
            }

        } while (!isPresent);
        System.out.println("Chosen student: " + studentChoice.toLowerCase());

        System.out.println("\nChoose an island\n");

        Integer islandChoice;
        String str = "";
        boolean validChoice;
        do {
            validChoice = false;

            try {
                str = getBr().readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            islandChoice = getCLI().stringToInteger(str);

            if(islandChoice != null) {
                islandChoice--;
                if (islandChoice >= 0 && islandChoice <= islands.size()) {
                    validChoice = true;
                }
                else {
                    System.out.println("The value must be between 1 and " + islands.size() + " included");
                }
            }
            else {
                System.out.println("Not an int");
            }
        } while (!validChoice);

        studentsOnCard.remove(color);
        islands.get(islandChoice).addStudent(color);

        cmCCG1 message = new cmCCG1(islandChoice, color);
        getCLI().getClient().send(new Gson().toJson(message, cmCCG1.class));

        return true;
    }

    @Override
    public boolean checkCCPrecondition() {
        String text = "Card "+getCardId()+ " can't be activated because ";
        if (getCLI().getMainPlayer().getCoins() < getPrice()) {
            System.out.println(text + " you don't have enough coins\n");
            return false;
        }
        if (studentsOnCard.isEmpty()) {
            System.out.println(text + " there are not enough students on card\n");
            return false;
        }
        return true;

    }
}

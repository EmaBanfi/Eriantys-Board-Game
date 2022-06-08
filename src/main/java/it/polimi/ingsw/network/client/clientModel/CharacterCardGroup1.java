package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG1;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharacterCardGroup1 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<IslandView> islands;

    /**
     * create a new CharacterCard n°1 and put on it 4 students; at the end increase the price of the CharacterCard
     */
    public CharacterCardGroup1(int id, CLI cli) {
        super(id, cli);
        setPrice(1);
        this.studentsOnCard = new ArrayList<>();
        islands = getCLI().getAvailableIslands();
        setText("At the start of the game, take 4 students and place them on top of this card;\n You can take one student from this card and place it on an island at your choice. Then, draw one student and place it on top of this card.");
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
     * @return
     */
    public boolean activate() {
        if (getCLI().getPlayer().getCoins() >= getPrice() && studentsOnCard.size() > 0) {

            StudentColor color;

            System.out.println("Choose the student to move to an island");
            String studentChoice = "";
            boolean isPresent;
            do {
                isPresent = false;
                System.out.println("Please select student among the following:");
                for (StudentColor student : studentsOnCard) {
                    System.out.println(student);
                }

                try {
                    studentChoice = br.readLine();
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
            for (IslandView island : islands) {
                String text = "Students on island " + (islands.indexOf(island) + 1);
                text = text + island.getStudents();
                System.out.println(text);
            }
            Integer islandChoice;
            String str = "";
            boolean validChoice;
            do {
                validChoice = false;

                try {
                    str = br.readLine();
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
        else if (getCLI().getPlayer().getCoins() < getPrice()) {
            System.out.println("Not enough coins");
        }
        else if (studentsOnCard.size() == 0) {
            System.out.println("Not enough students on card");
        }

        return false;
    }
}

package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG4;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharacterCardGroup4 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public CharacterCardGroup4(int id, CLI cli) {
        super(id, cli);
        if (getCardId() == 9) {
            setText("Choose a student color: the students of that color wont affect the majority count during this turn");
            setPrice(3);
        }
        else if (getCardId() == 11) {
            setText("At the start of the game, draw 4 students and place them on top of this card; \n" +
                    "You can choose one student from this card and place it in your Hall, then draw a new student and place it on top of this card");
            setPrice(2);
        }
        else if (getCardId() == 12){
            setText("Choose a student color: every player has to put back in the bag 3 students of the same color. Whoever has less than 3 students of the specified color, will put all the remaining students of that color ");
            setPrice(3);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 9, the CharacterCard 11 and the CharacterCard 12; at the end increase the price of the CharacterCard
     *
     * @return
     */
    public boolean activate(){
        if (getCLI().getPlayer().getCoins() >= getPrice()) {
            if (getCardId() == 9) {

                System.out.println("\nSelect ignored student color");

                cmCCG4 message = new cmCCG4(9, askStudent());
                getCLI().getClient().send(new Gson().toJson(message, cmCCG4.class));
            }

            else if (getCardId() == 11) {
                System.out.println("Students on card: ");
                for (StudentColor student : studentsOnCard) {
                    System.out.println(student);
                }

                System.out.println("Please select a student to move to your hall");
                StudentColor color;
                do {
                    color = askStudent();
                    if (!studentsOnCard.contains(color))
                        System.out.println("There is no " + color + " student on card");
                } while (!studentsOnCard.contains(color));

                cmCCG4 message = new cmCCG4(11, color);
                getCLI().getClient().send(new Gson().toJson(message, cmCCG4.class));
            }

            else if (getCardId() == 12) {
                System.out.println("Choose which student color you want to remove");

                cmCCG4 message = new cmCCG4(12, askStudent());
                getCLI().getClient().send(new Gson().toJson(message, cmCCG4.class));
            }

            return true;
        }
        else {
            System.out.println("Not enough coins");
        }

        return false;
    }

    private StudentColor askStudent() {
        String colorChoice = "";
        StudentColor color;
        do {
            try {
                colorChoice = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}

package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG6;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharacterCardGroup6 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();
    private final PlayerView player = getCLI().getMainPlayer();

    public CharacterCardGroup6(int id, CLI cli) {
        super(id, cli);
        if(getCardId() == 7) {
            setText("You can take up to 3 students from this card and change them with the same amount of students in your Hall");
            setPrice(1);
        }
        else if (getCardId() == 10){
            setText("You can swap up to 2 students from your Hall to your Dining Hall");
            setPrice(1);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 7 and the CharacterCard 10; at the end increase the price of the CharacterCard
     *
     * @return true if the card is activated
     */
    public boolean activate(){
        if (getCardId() == 7) {
            System.out.println("Students on card: " + studentsOnCard);
            System.out.println("Students in hall: " + player.getHall());

            if (confirmActivation())
                return false;
            int numberChoice;

            int studentsOnC= studentsOnCard.size();
            int studentsInH = player.getHall().size();
            int min = Math.min(studentsInH, studentsOnC);

            if(min<3)
                numberChoice = askNumOfStudents(studentsOnC);
            else
                numberChoice= askNumOfStudents(3);

            System.out.println("Choose " + numberChoice + " students to move from the card to the hall");
            String studentChoice = null;
            StudentColor color;
            int numOfStudents = 0;
            ArrayList<StudentColor> chosenStudentsFromCard = new ArrayList<>();
            do {
                System.out.println("Please select a student (" + (numOfStudents + 1) + ")");
                try {
                    studentChoice = getBr().readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
                if (color != null) {
                    if (studentsOnCard.contains(color)) {
                        numOfStudents++;
                        studentsOnCard.remove(color);
                        chosenStudentsFromCard.add(color);
                    }
                    else {
                        System.out.println("There is no " + color.toString().toLowerCase() + " student on card");
                    }
                }
                else {
                    System.out.println("Not a student color");
                }
            } while (numOfStudents < numberChoice);

            player.addToHall(chosenStudentsFromCard);
            System.out.println("Choose " + numberChoice + " students to move from the hall to the card");
            ArrayList<StudentColor> studentsFromHall = getCLI().askStudentsFromHall(numberChoice, false);

            studentsOnCard.addAll(studentsFromHall);

            cmCCG6 message = new cmCCG6(7, chosenStudentsFromCard, studentsFromHall);
            getCLI().getClient().send(new Gson().toJson(message, cmCCG6.class));
        }

        else if (getCardId() == 10) {
            System.out.println("Students in hall: " + player.getHall());
            System.out.println("Students in dining hall: " + player.getDiningHall());

            if (confirmActivation())
                return false;

            int numberChoice;

            int studentsInDH = player.getDiningHall().size();
            int studentsInH = player.getHall().size();


            if (studentsInDH< 2 || studentsInH<2)
                numberChoice = 1;
            else
                numberChoice = askNumOfStudents(2);

            System.out.println("Choose " + numberChoice + " students to move from the hall to the dining hall");
            ArrayList<StudentColor> studentsFromHall = new ArrayList<>(getCLI().askStudentsFromHall(numberChoice, false));

            System.out.println("Choose " + numberChoice + " students to move from the dining hall to the hall");
            int numOfStudents = 0;
            StudentColor color;
            String studentChoice = null;
            ArrayList<StudentColor> studentsToHall = new ArrayList<>();
            do {
                try {
                    studentChoice = getBr().readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
                if (player.getDiningHall().contains(color)) {
                    numOfStudents++;
                    studentsToHall.add(color);
                    player.getDiningHall().remove(color);
                }
            } while (numOfStudents < numberChoice);

            player.addToHall(studentsToHall);
            player.addToDiningHall(studentsFromHall);

            cmCCG6 message = new cmCCG6(10, studentsToHall, studentsFromHall);
            getCLI().getClient().send(new Gson().toJson(message, cmCCG6.class));
        }

        return true;
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
        System.out.println("\n\nCHARACTER CARD ACTIVATION\n");

        String text = "Card "+getCardId()+ " can't be activated because";

        if (getCLI().getMainPlayer().getCoins() < getPrice()) {
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

    /**
     * used to ask the number of students to move
     * @param max max number selectable
     * @return number selected
     */
    private int askNumOfStudents(int max) {
        System.out.println("How many students you want to move? (max " + max + ")");
        int numberChoice = 0;
        String str = "";
        boolean notValidChoice;
        do {
            try {
                str = getBr().readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (getCLI().stringToInteger(str) == null) {
                System.out.println("Not an int");
                notValidChoice = true;
            }
            else {
                numberChoice = getCLI().stringToInteger(str);
                notValidChoice = (numberChoice < 1 || numberChoice > 3);
                if (notValidChoice)
                    System.out.println("Not valid, please choose a value between 0 and 3");
            }
        } while (notValidChoice);

        return numberChoice;
    }
}

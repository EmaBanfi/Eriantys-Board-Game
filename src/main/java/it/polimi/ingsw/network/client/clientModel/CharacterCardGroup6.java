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
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private PlayerView player = getCLI().getPlayer();

    public CharacterCardGroup6(int id, CLI cli) {
        super(id, cli);
        if(getCardId() == 7) {
            setText("At the start of the game, draw 6 students and place them on top of this card;\n" +
                    "You can take up to 3 students from this card and change them with the same amount of students in your Hall");
            setPrice(1);
        }
        else if (getCardId() == 10){
            setText("You can swap 2 students from your Hall to your Dining Hall");
            setPrice(1);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 7 and the CharacterCard 10; at the end increase the price of the CharacterCard
     *
     * @return
     */
    public boolean activate(){
        if (getCLI().getPlayer().getCoins() >= getPrice()) {
            if (getCardId() == 7) {
                if (!studentsOnCard.isEmpty()) {
                    System.out.println("Students on card: ");
                    for (StudentColor student : studentsOnCard) {
                        System.out.println(student);
                    }
                    System.out.println("Students in your Hall: ");
                    for (StudentColor student : player.getHall()) {
                        System.out.println(student);
                    }

                    int numberChoice = askNumOfStudents(3);

                    System.out.println("Choose " + numberChoice + " students to move from the card to the hall");
                    String studentChoice = null;
                    StudentColor color;
                    int numOfStudents = 0;
                    ArrayList<StudentColor> chosenStudentsFromCard = new ArrayList<>();
                    do {
                        try {
                            studentChoice = br.readLine();
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

                    player.getHall().addAll(chosenStudentsFromCard);
                    System.out.println("Choose " + numberChoice + " students to move from the hall to the card");
                    ArrayList<StudentColor> studentsFromHall = getCLI().askStudentsFromHall(numberChoice, false);

                    studentsOnCard.addAll(studentsFromHall);

                    cmCCG6 message = new cmCCG6(7, chosenStudentsFromCard, studentsFromHall);
                    getCLI().getClient().send(new Gson().toJson(message, cmCCG6.class));

                    return true;
                }
                else {
                    System.out.println("It wasn't possible to activate the character card effect because you don't have any students on card");
                }
            }

            else if (getCardId() == 10) {
                System.out.println("Students in your Hall: ");
                for (StudentColor student : player.getHall()) {
                    System.out.println(student);
                }
                System.out.println("Students in your Dining Hall: ");
                for (StudentColor student : player.getDiningHall()) {
                    System.out.println(student);
                }

                int numberChoice = 1;
                int max = 2;

                if (player.getDiningHall().size() < 2)
                    max = player.getDiningHall().size();

                if (max > 1) {
                    if (max == 2)
                        numberChoice = askNumOfStudents(max);

                    System.out.println("Choose " + numberChoice + " students to move from the hall to the dining hall");
                    ArrayList<StudentColor> studentsFromHall = new ArrayList<>();
                    studentsFromHall.addAll(getCLI().askStudentsFromHall(numberChoice, false));

                    System.out.println("Choose " + numberChoice + " students to move from the dining hall to the hall");
                    int numOfStudents = 0;
                    StudentColor color;
                    String studentChoice = null;
                    ArrayList<StudentColor> studentsToHall = new ArrayList<>();
                    do {
                        try {
                            studentChoice = br.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        color = StudentColor.getStudentFromString(studentChoice);
                        if (player.getDiningHall().contains(color)) {
                            numOfStudents++;
                            studentsToHall.add(color);
                            player.getDiningHall().remove(color);
                        }
                    } while (numOfStudents < 2);

                    player.getHall().addAll(studentsToHall);
                    player.getDiningHall().addAll(studentsFromHall);

                    cmCCG6 message = new cmCCG6(10, studentsToHall, studentsFromHall);
                    getCLI().getClient().send(new Gson().toJson(message, cmCCG6.class));

                    return true;
                }
                else {
                    System.out.println("It wasn't possible to activate the character card effect because you don't have any students in dining hall");
                }
            }
        }
        else {
            System.out.println("Not enough coins");
        }

        return false;
    }

    public ArrayList<StudentColor> getStudentsOnCard() {
        return studentsOnCard;
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

    private int askNumOfStudents(int max) {
        System.out.println("How many students you want to move? (max " + max + ")");
        int numberChoice = 0;
        String str = "";
        boolean notValidChoice;
        do {
            try {
                str = br.readLine();
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

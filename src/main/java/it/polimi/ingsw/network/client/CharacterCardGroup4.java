package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.messages.clientMessages.CCG4;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterCardGroup4 extends CharacterCard {
    private StudentColor chosenColor;
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();
    private BufferedReader br;

    public CharacterCardGroup4(int id, View view) {
        super(id, view);
        if(id == 9) {
            setText("Choose a student color: the students of that color wont affect the majority count during this turn");
            setPrice(3);
        }
        else if(id == 11) {
            setText("At the start of the game, draw 4 students and place them on top of this card; \n" +
                    "You can choose one student from this card and place it in your Hall, then draw a new student and place it on top of this card");
            setPrice(2);
        }
        else {
            setText("Choose a student color: every player has to put back in the bag 3 students of the same color. Whoever has less than 3 students of the specified color, will put all the remaining students of that color ");
            setPrice(3);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 9, the CharacterCard 11 and the CharacterCard 12; at the end increase the price of the CharacterCard
     */
    public void activate(){
        if(cardId == 9){
            String colorChoice = null;
            StudentColor color = null;
            do{
                System.out.println("Choose a student color: ");
                try {
                    colorChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(colorChoice);
            }while(!color.equals(StudentColor.BLUE) || !color.equals(StudentColor.BLUE) || !color.equals(StudentColor.PURPLE) || !color.equals(StudentColor.YELLOW) || !color.equals(StudentColor.RED) || !color.equals(StudentColor.GREEN));
            CCG4 message = new CCG4(9, color);
            getView().getClient().send(new Gson().toJson(message, CCG4.class));
        }
        else if(cardId == 11){
            System.out.println("Students on card: ");
            for(StudentColor student : studentsOnCard){
                System.out.println(student);
            }
            String studentChoice = null;
            StudentColor color = null;
            do{
                System.out.println("Please select a student: ");
                try {
                    studentChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
            }while(!studentsOnCard.contains(color));
            CCG4 message = new CCG4(11, color);
            getView().getClient().send(new Gson().toJson(message, CCG4.class));
        }
        else{
            String colorChoice = null;
            StudentColor color = null;
            do{
                System.out.println("Choose a student color: ");
                try {
                    colorChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(colorChoice);
            }while(!color.equals(StudentColor.BLUE) || !color.equals(StudentColor.BLUE) || !color.equals(StudentColor.PURPLE) || !color.equals(StudentColor.YELLOW) || !color.equals(StudentColor.RED) || !color.equals(StudentColor.GREEN));
            CCG4 message = new CCG4(12, color);
            getView().getClient().send(new Gson().toJson(message, CCG4.class));
        }
    }

    public ArrayList<StudentColor> getStudentsOnCard() {
        return studentsOnCard;
    }

    /**
     * used to put 4 students on the CharacterCard n°11
     */
    public void putStudentsOnCard(ArrayList<StudentColor> students){
        studentsOnCard.addAll(students);
    }
}

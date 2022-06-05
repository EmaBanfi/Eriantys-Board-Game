package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG6;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharacterCardGroup6 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String currentPlayer = getView().getCurrentPlayer();

    public CharacterCardGroup6(int id, View view) {
        super(id, view);
        if(getCardId() == 7) {
            setText("At the start of the game, draw 6 students and place them on top of this card;\n" +
                    "You can take up to 3 students from this card and change them with the same amount of students in your Hall");
            setPrice(1);
        }
        else {
            setText("You can swap 2 students from your Hall to your Dining Hall");
            setPrice(1);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 7 and the CharacterCard 10; at the end increase the price of the CharacterCard
    */
    public void activate(){
        if(getCardId() == 7){
            int numberChoice = 0;
            do{
                System.out.println("Select the number of students that you want to change: ");
                try {
                    numberChoice = Integer.parseInt(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while(numberChoice < 1 || numberChoice > 3);
            String studentChoice = null;
            StudentColor color = null;
            int numOfStudents = 0;
            ArrayList<StudentColor> chosenStudentsFromCard = new ArrayList<>();
            do{
                System.out.println("Students on card: ");
                for(StudentColor student : studentsOnCard){
                    System.out.println(student);
                }
                System.out.println("Please select a student: ");
                try {
                    studentChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
                if(studentsOnCard.contains(color)){
                    numOfStudents++;
                    studentsOnCard.remove(color);
                    chosenStudentsFromCard.add(color);
                }
            }while(numOfStudents < numberChoice);

            studentChoice = null;
            color = null;
            numOfStudents = 0;
            ArrayList<StudentColor> chosenStudentsFromHall = new ArrayList<>();
            do{
                System.out.println("Students in your Hall: ");
                for(StudentColor student: getView().getPlayerByNick(currentPlayer).getHall()){
                    System.out.println(student);
                }
                try {
                    studentChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
                if(getView().getPlayerByNick(currentPlayer).getHall().contains(color)){
                    numOfStudents++;
                    getView().getPlayerByNick(currentPlayer).getHall().remove(color);
                    chosenStudentsFromHall.add(color);
                }
            }while(numOfStudents < numberChoice);
            cmCCG6 message = new cmCCG6(7, chosenStudentsFromCard, chosenStudentsFromHall);
            getView().getClient().send(new Gson().toJson(message, cmCCG6.class));
        }
        else{
            System.out.println("Choose 2 students from your Hall to swap with other 2 students from your Dining Hall: ");
            System.out.println("Students in your Hall: ");
            for(StudentColor student: getView().getPlayerByNick(currentPlayer).getHall()){
                System.out.println(student);
            }
            System.out.println("Students in your Dining Hall: ");
            for(StudentColor student: getView().getPlayerByNick(currentPlayer).getDiningHall()){
                System.out.println(student);
            }
            String studentChoice = null;
            StudentColor color = null;
            int numOfStudents = 0;
            ArrayList<StudentColor> studentsInHall = new ArrayList<>();
            do{
                System.out.println("Students in your Hall: ");
                for(StudentColor student: getView().getPlayerByNick(currentPlayer).getHall()){
                    System.out.println(student);
                }
                try {
                    studentChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
                if(getView().getPlayerByNick(currentPlayer).getHall().contains(color)){
                    numOfStudents++;
                    studentsInHall.add(color);
                    getView().getPlayerByNick(currentPlayer).getHall().remove(color);
                }
            }while(numOfStudents < 2);
            numOfStudents = 0;
            color = null;
            ArrayList<StudentColor> studentsInDiningHall = new ArrayList<>();
            do{
                System.out.println("Students in your Dining Hall: ");
                for(StudentColor student: getView().getPlayerByNick(currentPlayer).getDiningHall()){
                    System.out.println(student);
                }
                try {
                    studentChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
                if(getView().getPlayerByNick(currentPlayer).getDiningHall().contains(color)){
                    numOfStudents++;
                    studentsInDiningHall.add(color);
                    getView().getPlayerByNick(currentPlayer).getDiningHall().remove(color);
                }
            }while(numOfStudents < 2);
            cmCCG6 message = new cmCCG6(10, studentsInHall, studentsInDiningHall);
            getView().getClient().send(new Gson().toJson(message, cmCCG6.class));
        }
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
}

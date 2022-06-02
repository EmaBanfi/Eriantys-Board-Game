package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.messages.clientMessages.CCG1;
import it.polimi.ingsw.network.server.model.Island;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharacterCardGroup1 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<Island> islands;

    /**
     * create a new CharacterCard n°1 and put on it 4 students; at the end increase the price of the CharacterCard
     */
    public CharacterCardGroup1(int id, View view) {
        super(id, view);
        setPrice(1);
        this.studentsOnCard = new ArrayList<>();
        islands = getView().getAvailableIslands();
        setText("At the start of the game, take 4 students and place them on top of this card;\n You can take one student from this card and place it on an island at your choice. Then, draw one student and place it on top of this card.");
    }

    public ArrayList<StudentColor> getStudentsOnCard(){
        return studentsOnCard;
    }

    /**
     * puts 4 students on the CharacterCard
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    public void putStudentsOnCard(ArrayList<StudentColor> students) {
        studentsOnCard.addAll(students);
    }

    public void removeStudentsOnCard(ArrayList<StudentColor> students){
        studentsOnCard.removeAll(students);
    }

    /**
     * implement the effect of the characterCard n°1
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    public void activate() {
        System.out.println("Choose the student to move to an island");
        String studentChoice = null;
        boolean isPresent = false;
        do{
            if(!isPresent) {
                System.out.println("Please select student among the following:");
                for(StudentColor student : studentsOnCard){
                    System.out.println(student);
                }
            }
            try {
                studentChoice = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(StudentColor student : studentsOnCard){
                if(student.toString() == studentChoice.toUpperCase())
                    isPresent = true;
            }
        }while(!isPresent);
        System.out.println("Chosen student: " + studentChoice);
        for(int i = 0; i < islands.size(); i++){
            String text = "Students on island " + (i+1) ;
            for(StudentColor student : islands.get(i).getStudents()){
                if(i > 0) {
                    text =text + ", ";
                }
                text = text + student.toString();
            }
            System.out.println(text);
        }
        System.out.println("Choose an island: ");
        int islandChoice = -1;
        do{
            if(islandChoice < 0 || islandChoice > islands.size()){
                System.out.println("Enter value between 1 and 12 included");
            }
            try {
                islandChoice = Integer.parseInt(br.readLine()) -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(islandChoice < 0 || islandChoice > islands.size());
        StudentColor color = StudentColor.getStudentFromString(studentChoice);
        if(color != null) {
            studentsOnCard.remove(color);
            islands.get(islandChoice).addStudent(color);
            CCG1 message = new CCG1(islandChoice, color);
            getView().getClient().send(new Gson().toJson(message, CCG1.class));
        }
    }
}

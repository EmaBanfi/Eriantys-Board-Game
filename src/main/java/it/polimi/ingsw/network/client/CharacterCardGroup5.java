package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.CCG5;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharacterCardGroup5 extends CharacterCard {
    private int availableBlockCards;
    private ArrayList<IslandView> islands;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * create the CharacterCard 3, 5 or 6 and put on it 4 students
     */
    public CharacterCardGroup5(int id, View view) {
        super(id, view);
        if(cardId == 3){
            setText("You can choose an island and apply the majority count as if Mother Nature landed on that island." +
                    "Mother Nature will continue her normal movements after this effect");
            setPrice(3);
        }
        else if (cardId == 5) {
            this.availableBlockCards = 4;
            setText("At the start of the game, put 4 Block Card on top of this card; \n" +
                    "You can place a Block Card on an island: on the first time Mother Nature lands on that island," +
                    "put back the Block Card on top of this card and skip the majority count for that island");
            setPrice(2);
        }
        else{
            setText("The Towers won't affect the majority count on an island during this turn");
            setPrice(3);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 5 and the CharacterCard 6; at the end increase the price of the CharacterCard
     */
    public void activate() {
        if(cardId == 3){
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
            CCG5 message = new CCG5(3, islandChoice);
            getView().getClient().send(new Gson().toJson(message, CCG5.class));
        }
        else if(cardId == 5){
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
            CCG5 message = new CCG5(5, islandChoice);
            getView().getClient().send(new Gson().toJson(message, CCG5.class));
        }
        else{
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
            CCG5 message = new CCG5(6, islandChoice);
            getView().getClient().send(new Gson().toJson(message, CCG5.class));
        }
    }

    /**
     * used to add an block card on the CharacterCard n°5
     */
    public void addAvailableBlockCards() {
        this.availableBlockCards++;
    }

    public int getAvailableBlockCards() {
        return availableBlockCards;
    }
}

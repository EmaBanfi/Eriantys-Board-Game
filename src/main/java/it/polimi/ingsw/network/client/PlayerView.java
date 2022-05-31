package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class PlayerView {

    private ArrayList<StudentColor> hall;
    private ArrayList<StudentColor> diningHall;
    private String nickname;
    private int usedSupportCard;
    private int coins;
    private int availableSupportCard;
    private String tower;
    private  String deck;

    public PlayerView(String nickname) {
        this.nickname = nickname;
        availableSupportCard = 10;
        coins = 0;
    }

    public String getTower() {
        return tower;
    }

    public void setTower(String tower) {
        this.tower = tower;
    }

    public int getAvailableSupportCard(){return availableSupportCard;}

    public void decreaseSupportCards(){
        availableSupportCard--;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<StudentColor> getHall() {
        return hall;
    }

    public ArrayList<StudentColor> getDiningHall() {
        return diningHall;
    }

    public int getUsedSupportCard() {
        return usedSupportCard;
    }

    public int getCoins() {
        return coins;
    }

    public void setUsedSupportCard(int usedSupportCard) {
        this.usedSupportCard = usedSupportCard;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addToDiningHall(ArrayList<StudentColor> students){
        diningHall.addAll(students);
    }

    public void removeFromDiningHall(ArrayList<StudentColor> students){
        diningHall.removeAll(students);
    }

    public void addToHall(ArrayList<StudentColor> students){
        hall.addAll(students);
    }

    public void removeFromHall(ArrayList<StudentColor> students){
        hall.removeAll(students);
    }

    public void setDeck(String deck){
        this.deck = deck;
    }

    public void decreaseCoins() {
    }
}

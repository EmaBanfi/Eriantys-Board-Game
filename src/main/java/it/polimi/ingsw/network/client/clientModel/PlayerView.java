package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.server.model.DiningHall;
import it.polimi.ingsw.network.server.model.StudentColor;
import it.polimi.ingsw.network.server.model.SupportCard;

import java.util.ArrayList;

public class PlayerView {

    private final ArrayList<StudentColor> hall;
    private final ArrayList<StudentColor> diningHall;
    private final String nickname;
    private SupportCard usedSupportCard;
    private int coins;
    private int availableSupportCard;
    private String tower;
    private  String deck;
    private ArrayList<SupportCard> supportCards;

    public PlayerView(String nickname) {
        this.nickname = nickname;
        availableSupportCard = 10;
        coins = 1;
        initSupportDeck();
        hall = new ArrayList<>();
        diningHall = new ArrayList<>();
    }

    private void initSupportDeck(){
        supportCards = new ArrayList<>();

        for(int i=1; i<11; i++){
            supportCards.add(new SupportCard(i,i/2+i%2,i));
        }
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
        ArrayList<StudentColor> studentColor = new ArrayList<>(hall);
        return studentColor;
    }

    public ArrayList<StudentColor> getDiningHall() {
        ArrayList<StudentColor> studentColor = new ArrayList<>(diningHall);
        return studentColor;
    }

    public SupportCard getUsedSupportCard() {
        return usedSupportCard;
    }

    public int getCoins() {
        return coins;
    }

    public void setUsedSupportCard(int usedSupportCard) {
        SupportCard usedCard;
        for(SupportCard card: supportCards)
            if(card.getId()==usedSupportCard){
                usedCard= new SupportCard((int)card.getTurnOrder(),card.getMovement(),card.getId());
                this.usedSupportCard=usedCard;
                supportCards.remove(card);
                break;
            }
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

    public void removeFromHall(StudentColor student){
        hall.remove(student);
    }

    public void removeFromHall(ArrayList<StudentColor> students){
        hall.removeAll(students);
    }

    public void setDeck(String deck){
        this.deck = deck;
    }

    public ArrayList<SupportCard> getSupportCards() {
        return supportCards;
    }

    public void decreaseCoins(int price) {
        coins = coins - price;
    }

    public void resetSupportCard(){usedSupportCard=null;}
}

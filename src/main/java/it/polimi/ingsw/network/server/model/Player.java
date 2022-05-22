package it.polimi.ingsw.network.server.model;


import it.polimi.ingsw.Exceptions.LastSupportCardUsedException;

import java.util.ArrayList;

public class Player {
    private PlayerBoard board;
    private ArrayList<SupportCard> deck;
    private ArrayList<StudentColor> roles;
    private boolean characterCardUsed;
    private Tower tower;
    private int influencePoints;
    private SupportCard usedSupportCard;
    /** This attribute is used to implement the effect of the CharacterCard n° 2
     * If it's value is true then the player will became the teacher of a certain color
     * even if he has the some number of students of that color in his diningHal as another player
     */
    private boolean bonusToPromotion;
    /** This attribute is used to implement the effect of the CharacterCard n° 8
     * If it's value is true then the player will receive two additional influence points
     */
    private boolean additionalInfluencePoints;
    private final String nickName;

    /**
     * Constructor
     * @param nickName it must be unique.
     */
    public Player(String nickName) {
        roles=new ArrayList<>();
        this.nickName = nickName;
        initSupportDeck();
        board=new PlayerBoard();
        bonusToPromotion=false;
        additionalInfluencePoints=false;
        characterCardUsed=false;
    }

    /**
     * This method is used to initialise the deck of supportCards
     */
    private void initSupportDeck(){
        deck= new ArrayList<>();

        for(int i=1; i<11; i++){
            deck.add(new SupportCard(i,i/2+i%2,i));
        }
    }

    public  void setCharacterCardUsed(boolean b){
        characterCardUsed=b;
    }

    public boolean getCharacterCardUsed(){
        return characterCardUsed;
    }
    public int getInfluencePoints() {
        return influencePoints;
    }

    public void setInfluencePoints(int influencePoints) {
        this.influencePoints = influencePoints;
    }

    public void addInfluencePoints(int points) {
        influencePoints = influencePoints+points;
    }

    public PlayerBoard getBoard() {
        return board;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public ArrayList<SupportCard> getDeck() {
        return deck;
    }

    public ArrayList<StudentColor> getRoles(){
        return  roles;
    }

    /**
     * This method is used to get the supportCard chosen by the player in the planning phase
     * @return the card chosen by the player in the planning phase
     */
    public SupportCard getUsedCard() {
        return usedSupportCard;
    }

    /**
     * This method is used to memorise the supportCard chosen by the player in the planning phase and remove it from the deck
     * @param cardId supportCard chosen by the player in the planning phase
     * @throws LastSupportCardUsedException when the last support card is used
     */
    public void setUsedSupportCard(int cardId) throws LastSupportCardUsedException{
        for(SupportCard card: deck){
            if(card.getId()==cardId){
                deck.remove(card);
                usedSupportCard=card;
            }
        }
        if(deck.size()==0) throw new LastSupportCardUsedException();
    }

    public int remainingSupportCards(){
        return deck.size();
    }

    public SupportCard getUsedSupportCard(){
        return usedSupportCard;
    }

    public boolean hasBonusToPromotion() {
        return bonusToPromotion;
    }

    public boolean hasAdditionalInfluencePoints() {
        return additionalInfluencePoints;
    }

    public String getNickName() {
        return nickName;
    }

    public void setBoard(PlayerBoard board) {
        this.board = board;
    }

    public void setBonusToPromotion(boolean bonusToPromotion) {
        this.bonusToPromotion = bonusToPromotion;
    }

    public void setAdditionalInfluencePoints(boolean additionalInfluencePoints) {
        this.additionalInfluencePoints = additionalInfluencePoints;
    }

    public void clearUsedSupportCard(){
        usedSupportCard=null;
    }

    public SupportCard getCardById(int id){
        SupportCard foundCard=null;
        for (SupportCard card : deck){
            if(card.getId()==id){
                foundCard=card;
            }
        }
        return foundCard;
    }

    public void addRole (StudentColor color){
        if(!roles.contains(color))
            roles.add(color);
    }

    public void removeRole(StudentColor color){
        roles.remove(color);
    }
}

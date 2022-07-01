package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.clientModel.*;
import it.polimi.ingsw.network.server.model.StudentColor;
import it.polimi.ingsw.network.server.model.SupportCard;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewController {
    private final HashMap<StudentColor, String> teachers;
    private final MotherNatureView motherNature;
    private final ArrayList<CloudView> availableClouds;
    private final ArrayList<IslandView> availableIslands;
    private PlayerView mainPlayer;
    private final ArrayList<PlayerView> players;
    private ArrayList<String> availableDecks;
    private ArrayList<String> availableTowers;
    private String currentPlayer;
    private String mode;
    private int numOfPlayers;
    private Integer islandsShowRange;
    private final View view;
    private Phase resumeFrom = null;
    private int availableStudentsMovements;
    private String viewMode;
    private boolean alreadyUpdated = false;


    public ViewController(View view, String viewMode){
        this.viewMode = viewMode;
        this.view=view;
        availableClouds = new ArrayList<>();
        availableIslands = new ArrayList<>();
        teachers = new HashMap<>();
        players = new ArrayList<>();
        motherNature = new MotherNatureView();
        initAvailableDecks();
        initAvailableIslands();
    }

    /**
     * initialize the islands
     */
    private void initAvailableIslands(){
        for(int i = 0; i < 12; i++){
            availableIslands.add(new IslandView());
        }
    }

    /**
     * initialize the available decks
     */
    private void initAvailableDecks() {
        availableDecks = new ArrayList<>();
        availableDecks.add("KING");
        availableDecks.add("MAGE");
        availableDecks.add("WITCH");
        availableDecks.add("SAGE");
    }

    public void addPlayer(String nick){
        players.add(new PlayerView(nick));
    }

    /**
     * initialize the available towers
     */
    private void initAvailableTowers() {
        availableTowers = new ArrayList<>();
        availableTowers.add("WHITE");
        availableTowers.add("BLACK");
        if (numOfPlayers == 3) {
            availableTowers.add("GRAY");
        } else if (numOfPlayers == 4) {
            availableTowers.add("WHITE");
            availableTowers.add("BLACK");
        }
    }

    public void setMainPlayer(String nick){
        mainPlayer= new PlayerView(nick);
    }

    /**
     * updates to the other players (not the first one) the status of the game
     * @param numOfPlayers the number of players for that match
     * @param mode the mode of the match
     */
    public void updateGameStatus(int numOfPlayers, String mode){
        this.mode = mode;
        this.numOfPlayers = numOfPlayers;
        resetAvailableStudentsMovements();
        initAvailableTowers();
    }

    /**
     * get the player
     * @return the player
     */
    public PlayerView getMainPlayer() {
        return mainPlayer;
    }


    public void resetSupportCards(){
        for(PlayerView playerView: players)
            playerView.resetSupportCard();
    }

    public SupportCard getSupportCardByID(int id){
        SupportCard card = null;
        for(SupportCard c : getPlayerByNick(currentPlayer).getSupportCards()){
            if(c.getId()== id){
                card = c;
            }
        }
        return card;
    }

    /**
     * keep track of the status of the turn
     */
    public void resumeFrom(){
        switch (resumeFrom) {
            case CHOOSE_SUPPORT_CARD -> view.askSupportCard();
            case CHOOSE_MOTHER_MOVEMENTS -> view.askMotherNatureMovements();
            case CHOOSE_CLOUDS -> view.askCloud();
            case CHOOSE_TOWER -> view.askTower();
            case CHOOSE_DECK -> view.askDeck();
            case CHOOSE_STUDENTS_TO_DINING_HALL -> view.askMoveStudentsHToD();
            case CHOOSE_STUDENTS_TO_ISLAND -> view.askMoveStudentsHToI();
        }

    }

    public void setResumeFrom(Phase phase){
        resumeFrom = phase;
    }

    public Phase getResumeFrom(){
        return resumeFrom;
    }

    /**
     * set additional turn order for a used support card.
     * @param id id of the support card
     * @param additionalTurnOrder additionalTurnOrder of the support card.
     */
    public void setAdditionalTurnOrder(int id, double additionalTurnOrder){
        getSupportCardByID(id).setAdditionalTurnOrder(additionalTurnOrder);
    }

    /**
     * get the player by nickname
     * @return the player
     */
    public PlayerView getPlayerByNick(String nick){
        if (mainPlayer.getNickname().equals(nick))
            return mainPlayer;
        for(PlayerView player: players){
            if(player.getNickname().equals(nick))
                return player;
        }
        return null;
    }


    /**
     * updates the new position of mother nature (when other players changes it). Called by message
     * @param island island where mother nature is
     */
    public void updateMotherPosition(int island) {
        motherNature.setCurrentIsland(island);
    }

    /**
     * update the Tower color chosen by the current player. Called by message
     * @param tower color of the chosen tower
     */
    public void updateTowerColor(String tower) {
        getPlayerByNick(currentPlayer).setTower(tower);
        availableTowers.remove(tower.toUpperCase());
    }

    /**
     * place a block on the specified island. Called by message
     * @param island: island to lock
     */
    public void blockIsland(int island) {
        availableIslands.get(island).addBlockCard();
    }

    /**
     * removes the block on the specified island
     * @param island island to unblock
     */
    public void unlockIsland(int island){
        availableIslands.get(island).removeBlockCard();
    }

    /**
     * merge two islands. Called by message
     * @param toBeMerged island that will be merged
     * @param mergeTo island in to which the other island will be merged
     */
    public void mergeIslands(int toBeMerged, int mergeTo) {
        availableIslands.get(mergeTo).addTowers(availableIslands.get(toBeMerged).getNumOfTowers());
        availableIslands.get(mergeTo).addStudents(availableIslands.get(toBeMerged).studentsOnIsland());
        availableIslands.remove(toBeMerged);
    }

    /**
     * updates the added students on a specific island. Called by message
     * @param island specified island
     * @param students students to add
     */
    public void addStudentsOnIsland(int island, ArrayList<StudentColor> students) {
        if(!alreadyUpdated){
            if(viewMode.equals("cli"))
                availableIslands.get(island).addStudents(students);
            else if(viewMode.equals("gui"))
                availableIslands.get(island-1).addStudents(students);
        }
        alreadyUpdated = false;
    }

    /**
     * updates the added students in the dining hall of the specified player. Called by message
     * @param nick nick of the player
     * @param students students to add
     */
    public void addStudentToPlayerD(String nick, ArrayList<StudentColor> students) {
        getPlayerByNick(nick).addToDiningHall(students);
    }

    /**
     * remove the students from the dining hall of a specified player. Called by message
     * @param nick nick of the player
     * @param students students to remove
     */
    public void removeStudentsFromPlayerD(String nick, ArrayList<StudentColor> students) {
        getPlayerByNick(nick).removeFromDiningHall(students);
    }


    /**
     * update the hall of a specified player. Called by message
     * @param students students to add
     */
    public void addStudentsToHall(ArrayList<StudentColor> students) {
        getPlayerByNick(currentPlayer).addToHall(students);
    }

    /**
     * remove students to the player hall
     * @param students students to remove
     */
    public void removeStudentsFromHall(ArrayList<StudentColor> students){
        getPlayerByNick(currentPlayer).removeFromHall(students);
    }


    /**
     * update the amount of coins that the player has. Called by message
     * @param coin amount of coins
     */
    public void updatePlayerCoins(int coin) {
        getPlayerByNick(currentPlayer).setCoins(coin);
    }


    /**
     * update the current player
     * @param currentPlayer nickname of the current player
     */
    public void updateCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * to get the current player nickname
     * @return current player nickname
     */
    public String getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * to get the available Island
     * @return ArrayList of Island
     */
    public ArrayList<IslandView> getAvailableIslands() {
        return availableIslands;
    }


    /**
     * updates the clouds already chose, removing it from the available ones
     * @param cloud cloud to empty
     */
    public void updateEmptyCloud(int cloud) {
        availableClouds.get(cloud).removeStudents();
    }

    /**
     * updates the tower on an island
     * @param island index of the island (first index is 0)
     * @param tower color of the tower
     */
    public void updateTowerOnIsland(int island, String tower) {
        availableIslands.get(island).setTower(tower);
    }

    /**
     * add students on cloud
     * if the cloud doesn't exit, it will be created
     * @param cloud index of the cloud (first index is 0)
     * @param students students to add
     */
    public void addStudentsOnCloud(int cloud, ArrayList<StudentColor> students) {
        if(availableClouds.size()-1<cloud)
            availableClouds.add(new CloudView());
        availableClouds.get(cloud).addStudents(students);
    }


    /**
     * updates which player is a teacher
     * @param roles specifies of each teacher color which player is the the teacher;
     */
    public void updateTeacher(HashMap<StudentColor, String> roles) {
        teachers.putAll(roles);
    }

    /**
     * show the deck chosen by the current player
     * @param deck chosen deck
     */
    public void setPlayerDeck(String deck) {
        getPlayerByNick(currentPlayer).setDeckColor(deck.toUpperCase());
        availableDecks.remove(deck.toUpperCase());
    }

    /**
     * updates the support card used by the current player
     * @param id id of the support card.
     */
    public void setSupportCard(int id){
        getPlayerByNick(currentPlayer).setUsedSupportCard(id);
    }


    /**
     * this method is used to initialise the players arraylist
     * @param players nick of all the players in the match
     */
    public void addPlayers(ArrayList<String> players){
        for(String nick: players){
            if(!mainPlayer.getNickname().equals(nick))
                this.players.add(new PlayerView(nick));
        }
    }

    /**
     * remove students to a specific player's hall
     * @param nick nickname of the player
     * @param students students to remove
     */
    public void removeFromPlayerHall(String nick, ArrayList<StudentColor> students) {
        getPlayerByNick(nick).removeFromHall(students);
    }

    public int getMotherPosition() {
        return motherNature.getCurrentIsland();
    }


    public HashMap<StudentColor, String> getTeachers() {
        return teachers;
    }

    public MotherNatureView getMotherNature() {
        return motherNature;
    }

    public ArrayList<CloudView> getAvailableClouds() {
        return availableClouds;
    }

    public ArrayList<PlayerView> getPlayers() {
        return players;
    }

    public ArrayList<String> getAvailableDecks() {
        return availableDecks;
    }

    public ArrayList<String> getAvailableTowers() {
        return availableTowers;
    }

    public String getMode() {
        return mode;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public Integer getIslandsShowRange() {
        return islandsShowRange;
    }

    public void setIslandsShowRange(Integer islandsShowRange) {
        this.islandsShowRange = islandsShowRange;
    }

    public String getTeacherOfColor(StudentColor color){
        return teachers.getOrDefault(color, "yet to be decided");
    }

    public IslandView getIsland(int island){
        return availableIslands.get(island);
    }

    public int getNumOfAvailableIslands(){
        return availableIslands.size();
    }

    public CloudView getCloud(int cloud){
        return availableClouds.get(cloud);
    }

    public int getAvailableStudentsMovements() {
        return availableStudentsMovements;
    }

    public void setAvailableStudentsMovements(int availableStudentsMovements) {
        this.availableStudentsMovements = availableStudentsMovements;
    }

    public void resetAvailableStudentsMovements(){
        if(numOfPlayers==3)
            availableStudentsMovements=4;
        else
            availableStudentsMovements=3;
    }

    public boolean isTeacherOfColor(String player, StudentColor color){
        String teacher = getTeacherOfColor(color);
        return teacher != null && teacher.equals(player);
    }

    public String getNickByIndex(int index){
        return players.get(index-2).getNickname();
    }

    public  PlayerView getPlayerByIndex(int index){
        return players.get(index-2);
    }

    public void setAlreadyUpdated(){
        alreadyUpdated=true;
    }

}

package it.polimi.ingsw.network.client;

//come sono gestite le torri? (per il metodo updateTowerColor)
//cosa fa la getTowerByColor?

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import it.polimi.ingsw.network.server.model.SupportCard;

//aggiorna le activate
//nella addStudentsOnCard chiamare putStudentsOnCard

public class CLI implements View, Runnable {

    HashMap<StudentColor, String> teachers;
    MotherNatureView motherNature;
    ArrayList<CloudView> availableClouds;
    ArrayList<IslandView> availableIslands;
    Phase resumeFrom = Phase.CHOOSE_DECK;
    PlayerView player;
    ArrayList<PlayerView> players;
    ArrayList<String> availableDecks;
    ArrayList<String> availableTowers;
    ArrayList<SupportCard> supportCards;
    ArrayList<String> playerOrder;
    boolean usedCharacterCard;
    Client client;
    String currentPlayer;
    String mode;
    int numOfPlayers;
    HashMap<String, String> teams;
    BufferedReader br;
    Gson gson;
    int availableStudentsMovements = 3;
    StudentColor ignoredColor;
    CharacterCardDeck characterCardDeck;

    public CLI(Client client) {
        this.client = client;
        gson = new Gson();
        initAvailableDecks();
        characterCardDeck = new CharacterCardDeck(this);
    }

    private void initAvailableDecks() {
        availableDecks = new ArrayList<>();
        availableDecks.add("KING");
        availableDecks.add("MAGE");
        availableDecks.add("WITCH");
        availableDecks.add("SAGE");
    }

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

    @Override
    public void run() {
    }

    @Override
    public PlayerView getPlayer() {
        return player;
    }

    /**
     * communicate to the client to insert the nickname. Called by message
     */
    @Override
    public void askNickName(){
        resumeFrom = Phase.CHOOSE_GAME_STATUS;

        String str = null;
        try {
            str = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Nickname message = new Nickname(str);
        String text = gson.toJson(message, Nickname.class);
        client.send(text);
    }

    /**
     * ask to the client if he want to choose a character card. Called by method
     */
    @Override
    public void askActivateCharacterCard(){
        resumeFrom = Phase.CHOOSE_STUDENTS_TO_DINING_HALL;
        boolean result = false;
        try {
            if (br.readLine() == "yes") {
                usedCharacterCard = true;
                askCharacterCard();
            } else {
                resumeFrom();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CharacterCard getCharacterCard(int cardId){
        CharacterCard card = null;
        for(CharacterCard c: characterCardDeck.getAvailableCards()){
            if(c.cardId==cardId)
                card=c;
        }
        return card;
    }

    /**
     * communicate to the client to choose a character card. Called by method
     */
    @Override
    public void askCharacterCard(){
        String availableCards = "";
        for(CharacterCard c: characterCardDeck.getAvailableCards()){
            availableCards = availableCards + c.getCardId() + "\n";
        }
        System.out.println("Choose one of the followings character cards: ");
        System.out.println(availableCards);
        int choice = 0;
        CharacterCard card = null;
        do{
            try {
                choice = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            card = characterCardDeck.getCardById(choice);
        }while(card.equals(null));
        card.activate();
    }

    /**
     * choose the numOfPlayers and mode. (only to the first player connected). Called by message
     */
    @Override
    public void askSetGameStatus() {
        resumeFrom = Phase.CHOOSE_DECK;
        System.out.println("Choose number of players (NELLA BETA PUOI SETTARLO A 1): ");
        int numOfPlayers = 0;
        String mode = null;
        try {
            do {
                numOfPlayers = Integer.parseInt(br.readLine());
                if (numOfPlayers < 1 || numOfPlayers > 4)
                    System.out.println("Invalid num of players, please choose a new value (from 2 to 4): ");
            } while (numOfPlayers < 1 || numOfPlayers > 4);
            System.out.println("Choose mode: ");
            do {
                mode = br.readLine().toLowerCase();
                if (!mode.equals("expert") && !mode.equals("normal"))
                    System.out.println("Invalid game mode, please choose a valid one (between normal and expert): ");
            } while (!mode.equals("expert") && !mode.equals("normal"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of players selected: " + numOfPlayers + "\n" + "Mode selected: " + mode);
        Gson gson = new Gson();
        SetGameStatus message = new SetGameStatus(numOfPlayers, mode);
        String text = gson.toJson(message, SetGameStatus.class);
        client.send(text + "\n");
    }

    /**
     * ask to set a color for the tower (the controller will notify if is correctly set). Called by method
     */
    @Override
    public void askTower(){
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        String colorChoice = null;
        if (numOfPlayers == 3) {
            try {
                do {
                    colorChoice = br.readLine();
                    if (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("GREY") || !colorChoice.toUpperCase().equals("BLACK"))
                        System.out.println("Invalid Tower color, please select a valid one: ");
                } while (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("GREY") || !colorChoice.toUpperCase().equals("BLACK"));
            }catch(IOException e)
            {e.printStackTrace();}
            System.out.println("Chosen Tower color: " + colorChoice);
            Gson gson = new Gson();
            Tower message = new Tower(colorChoice);
            String text = gson.toJson(message, Tower.class);
            client.send(text + "\n");
            availableTowers.remove(colorChoice.toUpperCase());
        } else {
            try {
                do {
                    colorChoice = br.readLine();
                    if (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("BLACK"))
                        System.out.println("Invalid Tower color, please select a valid one: ");
                } while (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("BLACK"));
            }catch(IOException e){e.printStackTrace();}
            System.out.println("Chosen tower color: " + colorChoice);
            Tower message = new Tower(colorChoice);
            String text = gson.toJson(message, SetGameStatus.class);
            client.send(text + "\n");
            availableTowers.remove(colorChoice.toUpperCase());
        }
    }

    /**
     * ask to choose a deck (the controller will do a check). Called by method
     */
    @Override
    public void askDeck(){
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        for (String deck : availableDecks) {
            System.out.println(deck);
        }
        String userChoice = null;
        boolean deckAvailable = false;
        try {
            do {
                userChoice = br.readLine();
                for (String deck : availableDecks) {
                    if (userChoice.toUpperCase().equals(deck))
                        deckAvailable = true;
                }
                if (!deckAvailable)
                    System.out.println("Invalid deck, please choose a valid one: ");
            } while (!deckAvailable);
        }catch(IOException e)
        {e.printStackTrace();}
        System.out.println("Chosen deck: " + userChoice);
        Deck message = new Deck(userChoice);
        String text = gson.toJson(message, Deck.class);
        client.send(text + "\n");
        availableDecks.remove(userChoice.toUpperCase());
    }

    /**
     * ask to choose a support card. Called by method
     */
    @Override
    public void askSupportCard(){
        usedCharacterCard = false;
        for (SupportCard supportCard : supportCards) {
            System.out.println(supportCard);
        }
        boolean supportCardAvailable = false;
        int supportCardChoice = 0;
        try {
            do {
                supportCardChoice = Integer.parseInt(br.readLine());
                for (SupportCard supportCard : supportCards){
                    if (supportCardChoice == supportCard.getId())
                        supportCardAvailable = true;
                }
                if (!supportCardAvailable)
                    System.out.println("Invalid support card, please choose a valid one: ");
            } while (!supportCardAvailable);
        }catch(IOException e){e.printStackTrace();}
        System.out.println("Chosen support card: " + supportCardChoice);
        cmSupportCard message = new cmSupportCard(supportCardChoice);
        String text = gson.toJson(message, cmSupportCard.class);
        client.send(text + "\n");
        supportCards.remove(supportCardChoice);
    }

    public SupportCard getSupportCardByID(int id){
        SupportCard card = null;
        for(SupportCard c : supportCards){
            if(c.getId()== id){
                card = c;
            }
        }
        return card;
    }

    /**
     * ask to the client which students wants to move from H to D. Called by method
     */
    @Override
    public void askMoveStudentsHToD(){
        resumeFrom = Phase.CHOOSE_MOTHER_MOVEMENTS;
        int numStudents = 0;
        boolean validMovements = false;
        try {
            do {
                numStudents = Integer.parseInt(br.readLine());
                if (numStudents > 3 || numStudents < 0)
                    System.out.println("Not valid, please choose a value between 0 and 3: ");
            } while (numStudents > 3 || numStudents < 0);
        }catch(IOException e){e.printStackTrace();}
        if (numStudents > 0) {
            System.out.println("Choose the student that you want to move: ");
            ArrayList<StudentColor> studentsToMove = null;
            do {
                for (StudentColor student : player.getHall()) {
                    System.out.println(student + " ");
                }
                String chosenStudent = null;
                for (int i = 1; i <= numStudents; i++) {
                    do {
                        try {
                            chosenStudent = br.readLine().toUpperCase();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Choose student " + i);
                    } while (!validColor(chosenStudent));
                    studentsToMove.add(StudentColor.valueOf(chosenStudent));
                }
            } while (!player.getHall().containsAll(studentsToMove));
            player.removeFromHall(studentsToMove);
            player.addToDiningHall(studentsToMove);
            StudentsMovementsHToD message = new StudentsMovementsHToD(studentsToMove);
            client.send(gson.toJson(message, StudentsMovementsHToD.class));
            availableStudentsMovements = -numStudents;
        }
    }

    public boolean validColor(String color) {
        for (StudentColor C : StudentColor.values()) {
            if (color.toString().equals(color))
                return true;
        }
        return false;
    }

    /**
     * ask to the client which students wants to move from H to I (single or multiple). Called by method
     */
    @Override
    public void askMoveStudentsHToI(){
        resumeFrom = Phase.CHOOSE_STUDENTS_TO_DINING_HALL;
        if (availableStudentsMovements > 0) {
            System.out.println("Choose the island in which you want to move the student/s: ");
            int chosenIsland = 0;
            for (int i = 0; i < availableIslands.size(); i++) {
                System.out.println("Island: " + i + "  " + "Students on island " + i + ": " + availableIslands.get(i).getStudents() + ";");
            }
            do {
                try {
                    chosenIsland = Integer.parseInt(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (chosenIsland < 0 || chosenIsland > availableIslands.size())
                    System.out.println("Invalid island, please choose a valid one: ");
            } while (chosenIsland < 0 || chosenIsland > availableIslands.size());

            System.out.println("Choose the student/s that you want to move (you can move " + availableStudentsMovements + " students): ");
            ArrayList<StudentColor> studentsToMove = null;
            do {
                for (StudentColor student : player.getHall()) {
                    System.out.println(student + " ");
                }
                String chosenStudent = null;
                for (int i = 1; i <= availableStudentsMovements; i++) {
                    do {
                        try {
                            chosenStudent = br.readLine().toUpperCase();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Choose student " + i);
                    } while (!validColor(chosenStudent));
                    studentsToMove.add(StudentColor.valueOf(chosenStudent));
                }
            } while (!player.getHall().containsAll(studentsToMove));
            System.out.println("Chosen students: " + studentsToMove);
            HashMap<Integer, ArrayList<StudentColor>> movementsHToI = null;
            movementsHToI.put(chosenIsland, studentsToMove);
            StudentsMovementsHToI message = new StudentsMovementsHToI(movementsHToI);
            client.send(gson.toJson(message, StudentsMovementsHToI.class));
            player.getHall().removeAll(studentsToMove);
        }
    }

    /**
     * ask to the client from which C wants to take the students. Called by method
     */
    @Override
    public void askCloud(){
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        int chosenIsland = 0;
        for (int i = 0; i < availableClouds.size(); i++) {
            System.out.println("Cloud: " + i + "  " + "Students on Cloud " + i + ": " + availableClouds.get(i).getStudents() + ";");
        }
        int chosenCloud = 0;
        do {
            try {
                chosenCloud = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (chosenCloud < 0 || chosenCloud > availableClouds.size())
                System.out.println("Invalid cloud, please choose a valid one: ");
        } while (chosenCloud < 0 || chosenCloud > availableClouds.size());
        System.out.println("Chosen cloud:  " + chosenCloud);
        Cloud message = new Cloud(chosenCloud);
        client.send(gson.toJson(message, Cloud.class));
        player.addToHall(availableClouds.get(chosenCloud).getStudents());
    }

    /**
     * ask where he wants to put mother nature (the controller will do a check). Called by method
     */
    @Override
    public void askMotherNatureMovements(){
        for (int i = 0; i < availableIslands.size(); i++) {
            System.out.println("Island: " + i);
        }
        int chosenIsland = 0;
        do {
            try {
                chosenIsland = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (chosenIsland < 0 || chosenIsland > availableIslands.size())
                System.out.println("Invalid island, please choose a valid one: ");
        } while (chosenIsland < 0 || chosenIsland > availableIslands.size());
        System.out.println("Chosen island: " + chosenIsland);
        MoveMother message = new MoveMother(chosenIsland);
        client.send(gson.toJson(message, MoveMother.class));
    }

    /**
     * show a message (string) on the client screen. Called by message
     * @param message
     */
    @Override
    public void showString(String message) {
        System.out.println(message);
    }

    /**
     * show the character card used by a player and his name. Called by message
     * @param id
     */
    @Override
    public void showCharacterCard(int id) {
        System.out.println("Character card: " + id + "\n" + "Character card price: " + characterCardDeck.getAvailableCards().get(id).getPrice());
        System.out.println("\n");
    }

    public void setAdditionalTurnOrder(int id, double additionalTurnOrder){
        getSupportCardByID(id).setAdditionalTurnOrder(additionalTurnOrder);
    }

    /**
     * show the support card used by a player and his name. Called by message
     * @param id
     */
    @Override
    public void showSupportCard(int id) {
        System.out.println("Support card: " + id + "\n" +
                "Support card movements: " + getSupportCardByID(id).getMovement() + "\n" +
                "Support card turn order: " + getSupportCardByID(id).getTurnOrder() + "\n");
    }

    /**
     * show on the client screen which support card is using during that turn. Called by method
     */
    @Override
    public void updateUsedSupportCard(int id) {
        getPlayerByNick(currentPlayer).setUsedSupportCard(id);
    }

    @Override
    public PlayerView getPlayerByNick(String nick){
        for(PlayerView player: players){
            if(player.getNickname() == nick)
                return player;
        }
        return null;
    }

    /**
     * decrease the number of the remaining SupportCards of a player. Called by method
     */
    @Override
    public void updateAvailableSupportCards() {
        getPlayerByNick(currentPlayer).decreaseSupportCards();
    }

    /**
     * updates the price of a specific character card. Called by message
     * @param id
     */
    @Override
    public void updateCharacterCardPrice(int id) {
        characterCardDeck.getAvailableCards().get(id).increasePrice();
    }

    /**
     * updates the new position of mother nature (when other players changes it). Called by message
     * @param island
     */
    @Override
    public void updateMotherPosition(int island) {
        motherNature.setCurrentIsland(island);
    }

    /**
     * update the Tower color chosen by the current player. Called by message
     * @param tower
     */
    @Override
    public void updateTowerColor(String tower) {
        getPlayerByNick(currentPlayer).setTower(tower);
    }

    /**
     * updates the status of the island. Called by message
     * @param island
     */
    @Override
    public void blockIsland(int island) {
        availableIslands.get(island).setBlockCard(true);
    }

    @Override
    public void unlockIsland(int island){
        availableIslands.get(island).setBlockCard(false);
    }

    @Override
    public void ignoreTower(int island) {
        availableIslands.get(island).setIgnoreTower(true);
    }

    /**
     * merge two islands. Called by message
     * @param toBeMerged
     * @param mergeTo
     */
    @Override
    public void mergeIslands(int toBeMerged, int mergeTo) {

    }

    /**
     * updates the added students on a specific island. Called by message
     * @param island
     * @param students
     */
    @Override
    public void addStudentsOnIsland(int island, ArrayList<StudentColor> students) {
        availableIslands.get(island).getStudents().addAll(students);
    }

    /**
     * updates the added students in the dining hall of the specified player. Called by message
     * @param students
     */
    @Override
    public void addStudentToPlayerD(String nick, ArrayList<StudentColor> students) {
        getPlayerByNick(nick).getDiningHall().addAll(students);
    }

    /**
     * show the winners and the losers. Called by message
     * @param winners
     * @param losers
     */
    @Override
    public void showGameResults(ArrayList<String> winners, ArrayList<String> losers) {
        if (numOfPlayers == 2)
            System.out.println("Winner: " + winners + "\n" + "Loser: " + losers);
        else if (numOfPlayers == 3)
            System.out.println("Winner: " + winners + "\n" + "Losers: " + losers);
        else if (numOfPlayers == 4)
            System.out.println("Winners: " + winners + "\n" + "Losers: " + losers);
    }

    /**
     * remove the students from the dining hall of a specified player. Called by message
     * @param students
     */
    @Override
    public void removeStudentsFromPlayerD(String nick, ArrayList<StudentColor> students) {
        getPlayerByNick(nick).removeFromDiningHall(students);
    }

    /**
     * update the hall of a specified player. Called by message
     * @param students
     */
    @Override
    public void addStudentsToHall(ArrayList<StudentColor> students) {
        getPlayerByNick(currentPlayer).addToHall(students);
    }

    @Override
    public void removeStudentsFromHall(ArrayList<StudentColor> students){
        getPlayerByNick(currentPlayer).removeFromHall(students);
    }

    @Override
    public void addStudentsOnCard(int cardId, ArrayList<StudentColor> students) {

    }

    @Override
    public void removeStudentsFromCard(int cardId, ArrayList<StudentColor> students) {

    }

    /**
     * update the amount of coins that the player has. Called by message
     * @param coin
     */
    @Override
    public void updatePlayerCoins(int coin) {
        getPlayerByNick(currentPlayer).setCoins(coin);
    }

    public void updateGameStatus(int numOfPlayers, String mode) {
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
        initAvailableTowers();
    }

    public void updateCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }

    @Override
    public ArrayList<IslandView> getAvailableIsland() {
        return null;
    }

    public void updatePlayerOrder(ArrayList<String> playerOrder) {
        this.playerOrder = playerOrder;
    }

    /**
     * keep track of the status of the turn
     */
    public void resumeFrom(){
        switch (resumeFrom){
            case CHOOSE_GAME_STATUS:
                askSetGameStatus();
            case CHOOSE_SUPPORT_CARD :
                askSupportCard();
            case CHOOSE_MOTHER_MOVEMENTS:
                askMotherNatureMovements();
            case CHOOSE_CLOUDS:
                askCloud();
            case CHOOSE_TOWER:
                askTower();
            case CHOOSE_DECK:
                askDeck();
            case CHOOSE_STUDENTS_TO_DINING_HALL:
                askMoveStudentsHToD();
            case CHOOSE_STUDENTS_TO_ISLAND:
                askMoveStudentsHToI();
        }

    }

    /**
     * add a block-sign for the character card 5
     */
    public void addBlockOnCard(){
    }

    /**
     * updates the blockCard effect of the character card
     */
    public void removeBlockOnCard() {
        for(CharacterCard characterCard: characterCardDeck.getAvailableCards())
            if(characterCard.getCardId()==5)
                characterCard.getCardId();

    }

    /**
     * updates the clouds already chose, removing it from the available ones
     * @param cloud
     */
    public void updateEmptyCloud(int cloud) {
        availableClouds.get(cloud).removeStudents();
    }



    /**
     * updates the ignored color given by the effect of the character card
     * @param color
     */
    public void ignoreColor(StudentColor color) {
        ignoredColor = color;
    }

    public void notIgnoreColor(StudentColor color){
        ignoredColor = null;
    }

    /**
     * updates the ignored Tower given by the effect of the character card
     * @param island
     */
    public void ignoredTower(int island) {
        availableIslands.get(island).setIgnoreTower(true);
    }

    public void notIgnoreTower(int island){availableIslands.get(island).setIgnoreTower(false);}


    /**
     * updates the tower on an island (chiedi ad adriano come cazzo fare sta roba)
     * @param island
     * @param tower
     */
    public void updateTowerOnIsland(int island, String tower) {
    }

    public void getTowerByColor(String color){

    }

    /**
     * updates the student on the character card
     * @param cardId
     * @param students
     */
    public void updateStudentsOnCard(int cardId, ArrayList<StudentColor> students) {

    }

    /**
     * add students on cloud
     * @param students
     */
    @Override
    public void addStudentsOnCloud(int cloud, ArrayList<StudentColor> students) {
        availableClouds.get(cloud).addStudents(students);
    }

    /**
     * remove students from cloud
     * @param students
     */
    @Override
    public void removeStudentsFromCloud(int cloud, ArrayList<StudentColor> students) {
        availableClouds.get(cloud).removeStudents();
    }

    /**
     * updates which player is a teacher
     * @param roles
     */
    public void updateTeacher(HashMap<StudentColor, String> roles) {
        teachers.putAll(roles);
    }

    /**
     * show the deck chosen by the current player
     * @param deck
     */
    public void setPlayerDeck(String deck) {
        getPlayerByNick(currentPlayer).setDeck(deck);
    }

    /**
     * updates the support card used by the current player
     */
    @Override
    public void setSupportCard(int id){
        getPlayerByNick(currentPlayer).setUsedSupportCard(id);
    }

    /**
     * update the available character cards
     * @param availableCharacterCards list of the available character cards
     */
    @Override
    public void updateCharacterCards(ArrayList<CharacterCard> availableCharacterCards){
        characterCardDeck.setAvailableCards(availableCharacterCards);
    }

    public Client getClient(){
        return client;
    }

    public ArrayList<IslandView> getAvailableIslands(){
        return availableIslands;
    }


    public CharacterCard getCardById(int id){
        return characterCardDeck.getCardById(id);
    }
}

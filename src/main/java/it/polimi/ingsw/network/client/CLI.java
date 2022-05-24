package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CLI extends View {

    MotherNatureView motherNature;
    ArrayList<CloudView> availableClouds;
    ArrayList<IslandView> availableIslands;
    Phase resumeFrom = Phase.CHOOSE_DECK;
    PlayerView player;
    ArrayList<PlayerView> players;
    ArrayList<String> availableDecks;
    ArrayList<String> availableTowers;
    ArrayList<Integer> supportCards;
    ArrayList<CharacterCardView> availableCharacterCards;
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

    public CLI(Client client, String playerNickname, String mode) {
        this.mode = mode;
        this.client = client;
        gson = new Gson();
        initAvailableDecks();
    }

    private void initAvailableDecks() {
        availableDecks = new ArrayList<>();
        availableDecks.add("KING");
        availableDecks.add("MAGE");
        availableDecks.add("WTICH");
        availableDecks.add("SAGE");
    }

    public void initAvailableTowers() {
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
        return null;
    }

    @Override
    public PlayerView getPlayerByName(String nick) {
        return null;
    }

    /**
     * communicate to the client to insert the nickname. Called by message
     */
    public void askNickName(){
        resumeFrom = Phase.CHOOSE_GAME_STATUS;
        System.out.println("Inserit Nickname: ");
        client.askNickname();
    }

    /**
     * ask to the client if he want to choose a character card. Called by method
     */
    public void askActivateCharacterCard(){
        resumeFrom = Phase.CHOOSE_STUDENTS_TO_DINING_HALL;
        boolean result = false;
        System.out.println("Do you want to use a character card?");
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

    /**
     * communicate to the client to choose a character card. Called by method
     */
    public void askCharacterCard(){
        usedCharacterCard = true;
        int userChoice;
        CharacterCardView chosenCard = null;
        System.out.println("Choose Character Card: ");
        for (CharacterCardView characterCard : availableCharacterCards) {
            System.out.println("Card: " + characterCard.getCardId() + "\n" + "Cost: " + characterCard.getCost() + "\n");
        }
        boolean isPresent = false;
        try {
            do {
                userChoice = Integer.parseInt(br.readLine());
                for (CharacterCardView characterCard : availableCharacterCards) {
                    if (userChoice == characterCard.getCardId())
                        isPresent = true;
                }
                if (!isPresent)
                    System.out.println("Invalid id, please choose a new one: ");
            } while (!isPresent);
        }catch(IOException e)
        {e.printStackTrace();}
        Gson gson = new Gson();
        if (chosenCard.getCardId() == 1 || chosenCard.getCardId() == 11) {
            CCG1 message = new CCG1();
            String text = gson.toJson(message, CCG1.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 2 || chosenCard.getCardId() == 8) {
            CCG2 message = new CCG2();
            String text = gson.toJson(message, CCG2.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 3 || chosenCard.getCardId() == 4) {
            CCG3 message = new CCG3();
            String text = gson.toJson(message, CCG3.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 9 || chosenCard.getCardId() == 12) {
            CCG4 message = new CCG4();
            String text = gson.toJson(message, CCG4.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 5 || chosenCard.getCardId() == 6) {
            CCG5 message = new CCG5();
            String text = gson.toJson(message, CCG5.class);
            client.send(text + "\n");
        } else {
            CCG6 message = new CCG6();
            String text = gson.toJson(message, CCG6.class);
            client.send(text + "\n");
        }
    }

    /**
     * choose the numOfPlayers and mode. (only to the first player connected). Called by message
     */
    public void askSetGameStatus() {
        resumeFrom = Phase.CHOOSE_DECK;
        System.out.println("Choose number of players: ");
        int numOfPlayers = 0;
        String mode = null;
        try {
            do {
                numOfPlayers = Integer.parseInt(br.readLine());
                if (numOfPlayers < 2 || numOfPlayers > 4)
                    System.out.println("Invalid num of players, please choose a new value (from 2 to 4): ");
            } while (numOfPlayers < 2 || numOfPlayers > 4);
            System.out.println("Choose mode: ");
            do {
                mode = br.readLine();
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
     * ask to set a color for the tower (the controller will notify if is correctly setted). Called by method
     */
    public void askTower(){
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        String colorChoice = null;
        if (numOfPlayers == 3) {
            System.out.println("Choose your tower color (White, Grey or Black): ");
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
            Tower message = new Tower();
            String text = gson.toJson(message, Tower.class);
            client.send(text + "\n");
            availableTowers.remove(colorChoice.toUpperCase());
        } else {
            System.out.println("Choose your tower color (White or Black): ");
            try {
                do {
                    colorChoice = br.readLine();
                    if (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("BLACK"))
                        System.out.println("Invalid Tower color, please select a valid one: ");
                } while (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("BLACK"));
            }catch(IOException e){e.printStackTrace();}
            System.out.println("Chosen tower color: " + colorChoice);
            Tower message = new Tower();
            String text = gson.toJson(message, SetGameStatus.class);
            client.send(text + "\n");
            availableTowers.remove(colorChoice.toUpperCase());
        }
    }

    /**
     * ask to choose a deck (the controller will do a check). Called by method
     */
    public void askDeck(){
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        System.out.println("Choose a deck: ");
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
        Deck message = new Deck();
        String text = gson.toJson(message, Deck.class);
        client.send(text + "\n");
        availableDecks.remove(userChoice.toUpperCase());
    }

    /**
     * ask to choose a support card (or nothing). Called by method
     */
    public void askSupportCard(){
        usedCharacterCard = false;
        System.out.println("Choose a support card: ");
        for (Integer supportCard : supportCards) {
            System.out.println(supportCard);
        }
        boolean supportCardAvailable = false;
        int supportCardChoice = 0;
        try {
            do {
                supportCardChoice = Integer.parseInt(br.readLine());
                for (Integer supportCard : supportCards
                ) {
                    if (supportCardChoice == supportCard)
                        supportCardAvailable = true;
                }
                if (!supportCardAvailable)
                    System.out.println("Invalid support card, please choose a valid one: ");
            } while (!supportCardAvailable);
        }catch(IOException e){e.printStackTrace();}
        System.out.println("Chosen support card: " + supportCardChoice);
        SupportCard message = new SupportCard();
        String text = gson.toJson(message, SupportCard.class);
        client.send(text + "\n");
        supportCards.remove(supportCardChoice);
    }

    /**
     * ask to the client which students wants to move from H to D. Called by method
     */
    public void askMoveStudentsHToD(){
        resumeFrom = Phase.CHOOSE_MOTHER_MOVEMENTS;
        System.out.println("How many students do you want to move from Hall to Dining Hall? (from 0 up to " + availableStudentsMovements + ")");
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
    public void askCloud(){
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        System.out.println("Choose a cloud island to take the students from: ");
        System.out.println("Choose the island in which you want to move the student/s: ");
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
    public void askMotherNatureMovements(){
        System.out.println("Choose on which island you want to move Mother Nature: ");
        System.out.println("Current Mother Nature position: " + motherNature.getCurrentIsland());
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
     *
     * @param message
     */
    public void showString(String message) {
        System.out.println(message);
    }

    /**
     * show the character card used by a player and his name. Called by message
     * @param id
     */
    public void showCharacterCard(int id) {
        System.out.println("Character card: " + id);
    }

    /**
     * show the support card used by a player and his name. Called by message
     * @param id
     */
    public void showSupportCard(int id) {
        System.out.println("Support Card: " + id);
    }

    @Override
    public void updateUsedSupportCard() {

    }

    /**
     * show on the client screen which support card is using during that turn. Called by method
     */
    public void updateUsedSupportCard(int id) {
        getCurrentPLayer().setUsedSupportCard(id);
    }

    public PlayerView getCurrentPLayer(){
        for(PlayerView player: players){
            if(player.getNickname() == currentPlayer)
                return player;

        }
        return null;
    }

    /**
     * show the available support card to the client. Called by method
     */
    public void updateAvailableSupportCards() {
        
    }

    /**
     * show on screen the price of a specific character card. Called by message
     * @param id
     */
    public void updateCharacterCardPrice(int id) {
    }

    /**
     * show the new position of mother nature (when other players changes it). Called by message
     * @param island
     */
    public void updateMotherPosition(int island) {
        System.out.println("New Mother Nature position: " + island);
    }

    @Override
    public void updateTowerColor(String tower) {

    }

    /**
     * show the new color of the island for a tower. Called by message
     * @param nick
     * @param tower
     */
    public void updateTowerColor(String tower, String nick) {
        System.out.println(nick + "Chose the color " + tower + " for his tower");
    }

    /**
     * show the island blocked. Called by message
     * @param island
     */
    public void blockIsland(int island) {

    }

    public void unlockIsland(int island){}

    @Override
    public void ignoreTower(int island) {

    }

    /**
     *
     * @param island
     */
    public void unblockIsland(int island){}

    /**
     * merge two islands. Called by message
     * @param toBeMerged
     * @param mergeTo
     */
    public void mergeIslands(int toBeMerged, int mergeTo) {
    }

    /**
     * show the added students on a specific island. Called by message
     * @param island
     * @param students
     */
    public void addStudentsOnIsland(int island, ArrayList<StudentColor> students) {
        System.out.println("Studenti aggiunti all'isola " + island + ": " + students);
    }

    /**
     * show the added students in the dining hall of the specified player. Called by message
     * @param nick
     * @param students
     */
    public void addStudentToPlayerD(String nick, ArrayList<StudentColor> students) {
        System.out.println("Studenti aggiunti alla mensa di " + nick + ": " + students);
    }

    @Override
    public void showGameResults(ArrayList<String> winners, ArrayList<String> losers) {

    }

    /**
     * show the winners and the losers. Called by message
     * @param winners
     * @param losers
     */
    public void showGameResults(ArrayList<String> winners, ArrayList<String> losers, int numOfPlayers) {
        if (numOfPlayers == 2)
            System.out.println("Vincitore: " + winners + "\n" + "Perdente: " + losers);
        else if (numOfPlayers == 3)
            System.out.println("Vincitore: " + winners + "\n" + "Perdenti: " + losers);
        else if (numOfPlayers == 4)
            System.out.println("Vincitori: " + winners + "\n" + "Perdenti: " + losers);
    }

    /**
     * remove the students from the dining hall of a specified player. Called by message
     * @param nick
     * @param students
     */
    public void removeStudentsFromPlayerD(String nick, ArrayList<StudentColor> students) {

    }

    /**
     * update the hall of a specified player. Called by message
     * @param nick
     * @param students
     */
    public void updatePlayerHall(String nick, ArrayList<StudentColor> students) {
    }

    @Override
    public void updatePlayerCoins(int coin) {

    }

    /**
     * update the amount of coins that the player has. Called by message
     * @param nick
     * @param coin
     */
    public void updatePlayerCoins(String nick, int coin) {
    }

    public void updateGameStatus(int numOfPlayers, String mode) {
        this.numOfPlayers = numOfPlayers;
        this.mode = mode;
        initAvailableTowers();
    }

    public void updateCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public void addBlockOnCard(){}

    /**
     * updates the blockCard effect of the character card
     */
    public void removeBlockOnCard() {
    }

    /**
     * updates the clouds already chosen
     * @param cloud
     */
    public void updateEmptyCloud(int cloud) {
    }

    @Override
    public void updateIgnoredColor(StudentColor color) {

    }

    /**
     * updates the ignored color given by the effect of the character card
     * @param color
     */
    public void ignoreColor(StudentColor color) {
    }

    public void notIgnoreColor(StudentColor color){}

    @Override
    public void updateCharacterCards(ArrayList<CharacterCard> availableCharacterCards) {

    }

    /**
     * updates the ignored Tower given by the effecto of the character card
     * @param island
     */
    public void ignoredTower(int island) {

    }

    public void notIgnoreTower(int island){}


    /**
     * updates the tower on an island
     * @param island
     * @param tower
     */
    public void updateTowerOnIsland(int island, String tower) {
    }

    /**
     * updates the student on the character card
     * @param cardId
     * @param students
     */
    public void updatesStudentsOnCard(int cardId, ArrayList<StudentColor> students) {
    }

    @Override
    public void addStudentsOnCloud(int cloud, ArrayList<StudentColor> students) {

    }

    /**
     * add students on cloud
     * @param students
     */
    public void addStudentsOnCloud(ArrayList<StudentColor> students) {
    }

    /**
     * remove students from cloud
     * @param students
     */
    public void removeStudentsFromCloud(ArrayList<StudentColor> students) {
    }

    /**
     * updates which player is a teacher
     * @param roles
     */
    public void updateTeacher(HashMap<StudentColor, String> roles) {
    }

    /**
     * show the deck chosen by the current player
     * @param deck
     */
    public void setPlayerDeck(String deck) {
    }

    /**
     * show the support card used by the current player
     */
    public void setSupportCard(){}

    public void updateCharacterCard(ArrayList<CharacterCardView> availableCharacterCards){
        this.availableCharacterCards = availableCharacterCards;
    }

    public void setNumOfPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
    }

    public void setMode(String mode){
        this.mode = mode;
    }
}

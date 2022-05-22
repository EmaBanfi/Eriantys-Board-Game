package it.polimi.ingsw.network.client;

//come viene tenuto conto dell'incremento di costo delle character card? servono delle sottoclassi che implementano i vari gruppi con i metodi che incrementano i loro prezzi all'occorrenza?

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.server.model.Island;
import it.polimi.ingsw.network.server.model.Player;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements Runnable {

    MotherNatureView motherNature;
    ArrayList<CloudView> availableClouds;
    ArrayList<IslandView> availableIslands;
    Phase resumeFrom = Phase.CHOOSE_DECK;
    PlayerView player;
    ArrayList<PlayerView> players;
    ArrayList<String> availableDecks;
    ArrayList<String> availableTowers;
    ArrayList<Integer> supportCards;
    ArrayList<characterCardView> availableCharacterCards;
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

    /**
     * communicate to the client to insert the nickname. Called by message
     */
    public void askNickName() throws IOException {
        resumeFrom = Phase.CHOOSE_GAME_STATUS;
        System.out.println("Inserit Nickname: ");
        client.askNickname();
    }

    /**
     * ask to the client if he want to choose a character card. Called by method
     */
    public void askActivateCharacterCard() throws IOException {
        resumeFrom = Phase.CHOOSE_STUDENTS_TO_DINING_HALL;
        boolean result = false;
        System.out.println("Do you want to use a character card?");
        if (br.readLine() == "yes") {
            usedCharacterCard = true;
            askCharacterCard();
        } else {
            resumeFrom();
        }
    }

    /**
     * communicate to the client to choose a character card. Called by method
     */
    public void askCharacterCard() throws IOException {
        usedCharacterCard = true;
        int userChoice;
        characterCardView chosenCard = null;
        System.out.println("Choose Character Card: ");
        for (characterCardView characterCard : availableCharacterCards) {
            System.out.println("Card: " + characterCard.getCardId() + "\n" + "Cost: " + characterCard.getCost() + "\n");
        }
        boolean isPresent = false;
        do {
            userChoice = Integer.parseInt(br.readLine());
            for (characterCardView characterCard : availableCharacterCards) {
                if (userChoice == characterCard.getCardId())
                    isPresent = true;
            }
            if (!isPresent)
                System.out.println("Invalid id, please choose a new one: ");
        } while (!isPresent);
        Gson gson = new Gson();
        if (chosenCard.getCardId() == 1 || chosenCard.getCardId() == 11) {
            cmCCG1 message = new cmCCG1();
            String text = gson.toJson(message, cmCCG1.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 2 || chosenCard.getCardId() == 8) {
            cmCCG2 message = new cmCCG2();
            String text = gson.toJson(message, cmCCG2.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 3 || chosenCard.getCardId() == 4) {
            cmCCG3 message = new cmCCG3();
            String text = gson.toJson(message, cmCCG3.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 9 || chosenCard.getCardId() == 12) {
            cmCCG4 message = new cmCCG4();
            String text = gson.toJson(message, cmCCG4.class);
            client.send(text + "\n");
        } else if (chosenCard.getCardId() == 5 || chosenCard.getCardId() == 6) {
            cmCCG5 message = new cmCCG5();
            String text = gson.toJson(message, cmCCG5.class);
            client.send(text + "\n");
        } else {
            cmCCG6 message = new cmCCG6();
            String text = gson.toJson(message, cmCCG6.class);
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
        cmSetGameStatus message = new cmSetGameStatus(numOfPlayers, mode);
        String text = gson.toJson(message, cmSetGameStatus.class);
        client.send(text + "\n");
    }

    /**
     * ask to set a color for the tower (the controller will notify if is correctly setted). Called by method
     */
    public void askTower() throws IOException {
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        String colorChoice = null;
        if (numOfPlayers == 3) {
            System.out.println("Choose your tower color (White, Grey or Black): ");
            do {
                colorChoice = br.readLine();
                if (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("GREY") || !colorChoice.toUpperCase().equals("BLACK"))
                    System.out.println("Invalid Tower color, please select a valid one: ");
            } while (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("GREY") || !colorChoice.toUpperCase().equals("BLACK"));
            System.out.println("Chosen Tower color: " + colorChoice);
            Gson gson = new Gson();
            cmTower message = new cmTower();
            String text = gson.toJson(message, cmTower.class);
            client.send(text + "\n");
            availableTowers.remove(colorChoice.toUpperCase());
        } else {
            System.out.println("Choose your tower color (White or Black): ");
            do {
                colorChoice = br.readLine();
                if (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("BLACK"))
                    System.out.println("Invalid Tower color, please select a valid one: ");
            } while (!colorChoice.toUpperCase().equals("WHITE") || !colorChoice.toUpperCase().equals("BLACK"));
            System.out.println("Chosen tower color: " + colorChoice);
            cmTower message = new cmTower();
            String text = gson.toJson(message, cmSetGameStatus.class);
            client.send(text + "\n");
            availableTowers.remove(colorChoice.toUpperCase());
        }
    }

    /**
     * ask to choose a deck (the controller will do a check). Called by method
     */
    public void askDeck() throws IOException {
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        System.out.println("Choose a deck: ");
        for (String deck : availableDecks) {
            System.out.println(deck);
        }
        String userChoice;
        boolean deckAvailable = false;
        do {
            userChoice = br.readLine();
            for (String deck : availableDecks) {
                if (userChoice.toUpperCase().equals(deck))
                    deckAvailable = true;
            }
            if (!deckAvailable)
                System.out.println("Invalid deck, please choose a valid one: ");
        } while (!deckAvailable);
        System.out.println("Chosen deck: " + userChoice);
        cmDeck message = new cmDeck();
        String text = gson.toJson(message, cmDeck.class);
        client.send(text + "\n");
        availableDecks.remove(userChoice.toUpperCase());
    }

    /**
     * ask to choose a support card (or nothing). Called by method
     */
    public void askSupportCard() throws IOException {
        usedCharacterCard = false;
        System.out.println("Choose a support card: ");
        for (Integer supportCard : supportCards) {
            System.out.println(supportCard);
        }
        boolean supportCardAvailable = false;
        int supportCardChoice = 0;
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
        System.out.println("Chosen support card: " + supportCardChoice);
        cmSupportCard message = new cmSupportCard();
        String text = gson.toJson(message, cmSupportCard.class);
        client.send(text + "\n");
        supportCards.remove(supportCardChoice);
    }

    /**
     * ask to the client which students wants to move from H to D. Called by method
     */
    public void askMoveStudentsHToD() throws IOException {
        resumeFrom = Phase.CHOOSE_MOTHER_MOVEMENTS;
        System.out.println("How many students do you want to move from Hall to Dining Hall? (from 0 up to " + availableStudentsMovements + ")");
        int numStudents;
        boolean validMovements = false;
        do {
            numStudents = Integer.parseInt(br.readLine());
            if (numStudents > 3 || numStudents < 0)
                System.out.println("Not valid, please choose a value between 0 and 3: ");
        } while (numStudents > 3 || numStudents < 0);
        if (numStudents > 0) {
            System.out.println("Choose the student that you want to move: ");
            ArrayList<StudentColor> studentsToMove = null;
            do {
                for (StudentColor student : player.getHall()) {
                    System.out.println(student + " ");
                }
                String chosenStudent;
                for (int i = 1; i <= numStudents; i++) {
                    do {
                        chosenStudent = br.readLine().toUpperCase();
                        System.out.println("Choose student " + i);
                    } while (!validColor(chosenStudent));
                    studentsToMove.add(StudentColor.valueOf(chosenStudent));
                }
            } while (!player.getHall().containsAll(studentsToMove));
            player.removeFromHall(studentsToMove);
            player.addToDiningHall(studentsToMove);
            cmStudentsMovementsHToD message = new cmStudentsMovementsHToD(studentsToMove);
            client.send(gson.toJson(message, cmStudentsMovementsHToD.class));
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
    public void askMoveStudentsHToI() throws IOException {
        resumeFrom = Phase.CHOOSE_STUDENTS_TO_DINING_HALL;
        if (availableStudentsMovements > 0) {
            System.out.println("Choose the island in which you want to move the student/s: ");
            int chosenIsland = 0;
            for (int i = 0; i < availableIslands.size(); i++) {
                System.out.println("Island: " + i + "  " + "Students on island " + i + ": " + availableIslands.get(i).getStudents() + ";");
            }
            do {
                chosenIsland = Integer.parseInt(br.readLine());
                if (chosenIsland < 0 || chosenIsland > availableIslands.size())
                    System.out.println("Invalid island, please choose a valid one: ");
            } while (chosenIsland < 0 || chosenIsland > availableIslands.size());

            System.out.println("Choose the student/s that you want to move (you can move " + availableStudentsMovements + " students): ");
            ArrayList<StudentColor> studentsToMove = null;
            do {
                for (StudentColor student : player.getHall()) {
                    System.out.println(student + " ");
                }
                String chosenStudent;
                for (int i = 1; i <= availableStudentsMovements; i++) {
                    do {
                        chosenStudent = br.readLine().toUpperCase();
                        System.out.println("Choose student " + i);
                    } while (!validColor(chosenStudent));
                    studentsToMove.add(StudentColor.valueOf(chosenStudent));
                }
            } while (!player.getHall().containsAll(studentsToMove));
            System.out.println("Chosen students: " + studentsToMove);
            HashMap<Integer, ArrayList<StudentColor>> movementsHToI = null;
            movementsHToI.put(chosenIsland, studentsToMove);
            cmStudentsMovementsHToI message = new cmStudentsMovementsHToI(movementsHToI);
            client.send(gson.toJson(message, cmStudentsMovementsHToI.class));
            player.getHall().removeAll(studentsToMove);
        }
    }

    /**
     * ask to the client from which C wants to take the students. Called by method
     */
    public void askCloud() throws IOException {
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        System.out.println("Choose a cloud island to take the students from: ");
        System.out.println("Choose the island in which you want to move the student/s: ");
        int chosenIsland = 0;
        for (int i = 0; i < availableClouds.size(); i++) {
            System.out.println("Cloud: " + i + "  " + "Students on Cloud " + i + ": " + availableClouds.get(i).getStudents() + ";");
        }
        int chosenCloud = 0;
        do {
            chosenCloud = Integer.parseInt(br.readLine());
            if (chosenCloud < 0 || chosenCloud > availableClouds.size())
                System.out.println("Invalid cloud, please choose a valid one: ");
        } while (chosenCloud < 0 || chosenCloud > availableClouds.size());
        System.out.println("Chosen cloud:  " + chosenCloud);
        cmCloud message = new cmCloud(chosenCloud);
        client.send(gson.toJson(message, cmCloud.class));
        player.addToHall(availableClouds.get(chosenCloud).getStudents());
    }

    /**
     * ask where he wants to put mother nature (the controller will do a check). Called by method
     */
    public void askMotherNatureMovements() throws IOException {
        System.out.println("Choose on which island you want to move Mother Nature: ");
        System.out.println("Current Mother Nature position: " + motherNature.getCurrentIsland());
        for (int i = 0; i < availableIslands.size(); i++) {
            System.out.println("Island: " + i);
        }
        int chosenIsland = 0;
        do {
            chosenIsland = Integer.parseInt(br.readLine());
            if (chosenIsland < 0 || chosenIsland > availableIslands.size())
                System.out.println("Invalid island, please choose a valid one: ");
        } while (chosenIsland < 0 || chosenIsland > availableIslands.size());
        System.out.println("Chosen island: " + chosenIsland);
        cmMoveMother message = new cmMoveMother(chosenIsland);
        client.send(gson.toJson(message, cmMoveMother.class));
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

    /**
     * show on the client screen which support card is using during that turn. Called by method
     */
    public void updateUsedSupportCard() {
        System.out.println("Used Support Card: " + supportCards);
    }

    /**
     * show the available support card to the client. Called by method
     */
    public void updateAvailableSupportCards() {
        System.out.println("Available Character Cards: " + availableCharacterCards);
    }

    /**
     * show on screen the price of a specific character card. Called by message
     *
     * @param id
     */
    public void updateCharacterCardPrice(int id) {
        //idea: inizializzarle con json nel controller e tenere traccia del costro tramite controller
    }

    /**
     * show the new position of mother nature (when other players changes it). Called by message
     * @param island
     */
    public void updateMotherPosition(int island) {
        System.out.println("New Mother Nature position: " + island);
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
     * @param blocked
     */
    public void updateBlock(int island, boolean blocked) {
        if (blocked == true)
            System.out.println("The island " + island + "is blocked");
    }

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
     * @throws IOException
     */
    public void resumeFrom() throws IOException {
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
     * updates the blockCard effect of the character card
     * @param block
     */
    public void updateBlockOnCard(int block) {

    }

    /**
     * updates the clouds already chosen
     * @param cloud
     */
    public void updateEmptyCloud(int cloud) {
    }

    /**
     * updates the ignored color given by the effect of the character card
     * @param color
     */
    public void updateIgnoredColor(StudentColor color) {
    }

    /**
     * updates the ignored Tower given by the effecto of the character card
     * @param island
     * @param ignored
     */
    public void updatetIgnoredTower(int island, boolean ignored) {
    }

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
}
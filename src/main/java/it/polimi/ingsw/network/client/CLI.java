package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.clientModel.*;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import it.polimi.ingsw.network.server.model.SupportCard;

public class CLI implements View, Runnable {

    private final ArrayList<CharacterCard> availableCC;

    private final ArrayList<Integer> usableCC;
    private final CharacterCardCreator ccc;
    private boolean usedCharacterCard;
    private final Client client;
    private final Scanner input;
    private final ViewController viewController;
    private final Gson gson;


    public CLI() {
        input = new Scanner(System.in);
        availableCC = new ArrayList<>();
        ccc = new CharacterCardCreator();
        usableCC = new ArrayList<>();
        viewController = new ViewController(this);
        gson = new Gson();
        client = new Client(this, setIpAddress());
        client.start();
    }

    @Override
    public ViewController getViewController(){
        return viewController;
    }

    private String setIpAddress() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nInsert the server IP address");

        return scanner.nextLine();
    }

    public static void main(String[] args) {
        CLI cli = new CLI();
    }



    @Override
    public void run() {
    }


    public Phase getResumeFrom() {
        return viewController.getResumeFrom();
    }

    /**
     * communicate to the client to insert the nickname. Called by message
     */
    @Override
    public void askNickName(){
        viewController.setResumeFrom(Phase.CHOOSE_TOWER);

        String str;
        str = input.nextLine();

        viewController.setMainPlayer(str);

        cmNickname message = new cmNickname(str);
        String text = gson.toJson(message, cmNickname.class);
        client.send(text);
    }

    /**
     * ask to the client if he want to choose a character card. Called by method
     */
    @Override
    public void askActivateCharacterCard(){
        if (viewController.getMainPlayer().getCoins() == 0) {
            System.out.println("You can't activate any character card because you have 0 coins");
            return;
        }

        System.out.println("You have " + viewController.getMainPlayer().getCoins() + " coins");
        System.out.println("The available character cards are the following\n");

        for(Integer c: usableCC){
            getCharacterCardById(c).showCard();
        }
        String str;
        do {
            System.out.println("Do you want to activate a character card? (yes|no)");

            str = input.nextLine();

            if(!str.equalsIgnoreCase("yes") && !str.equalsIgnoreCase("no"))
                System.out.println("Please select yes or no");
        }while(!str.equalsIgnoreCase("yes") && !str.equalsIgnoreCase("no"));
        if (str.equalsIgnoreCase("yes")) {
            askCharacterCard();
        }
    }

    /**
     * communicate to the client to choose a character card. Called by method
     */
    @Override
    public void askCharacterCard(){
        System.out.println("Choose one of the character cards");

        int choice=0;
        CharacterCard card;
        String str;
        do {
            str = input.nextLine();

            if (stringToInteger(str) == null)
                System.out.println("Not an int");
            else {
                choice = stringToInteger(str);
                if (!usableCC.contains(choice))
                    System.out.println("Not valid character card id");
            }
        } while(!usableCC.contains(choice));
        card = getCharacterCardById(choice);
        if (!card.activate())
            askActivateCharacterCard();
        else {

            usedCharacterCard = true;
            viewController.getMainPlayer().decreaseCoins(card.getPrice());
            updateCharacterCardPrice(card.getCardId());
        }
    }

    /**
     * choose the numOfPlayers and mode. (only to the first player connected). Called by message
     */
    @Override
    public void askSetGameStatus() {
        viewController.setResumeFrom(Phase.CHOOSE_TOWER);
        System.out.println("Choose number of players (2,3 or 4): ");
        int numOfPlayers = 0;
        String mode;
        boolean notValidChoice;
        String str;
        do{
            str = input.nextLine();

            if (stringToInteger(str) == null) {
                System.out.println("Not an int");
                notValidChoice = true;
            }
            else {
                numOfPlayers = stringToInteger(str);
                notValidChoice = (numOfPlayers < 2 || numOfPlayers > 4);
                if (notValidChoice)
                    System.out.println("Invalid num of players, please choose a new value (from 2 to 4): ");
            }
        } while (notValidChoice);
        System.out.println("Choose mode: ");
        do {
            mode = input.nextLine().toLowerCase();

            notValidChoice = (!mode.equals("expert") && !mode.equals("normal"));
            if (notValidChoice)
                System.out.println("Invalid game mode, please choose a valid one (between normal and expert): ");
        } while (notValidChoice);

        viewController.updateGameStatus(numOfPlayers,mode);

        System.out.println("Number of players selected: " + numOfPlayers + "\n" + "Mode selected: " + mode);
        Gson gson = new Gson();
        cmSetGameStatus message = new cmSetGameStatus(numOfPlayers, mode);
        String text = gson.toJson(message, cmSetGameStatus.class);
        client.send(text);
    }



    /**
     * ask to set a color for the tower (the controller will notify if is correctly set). Called by method
     */
    @Override
    public void askTower(){
        viewController.setResumeFrom(Phase.CHOOSE_DECK);
        System.out.println("\n\nCHOOSING TOWER\n");
        System.out.println("Choose tower color:");
        System.out.println("The available towers are: ");
        if(viewController.getAvailableTowers().contains("WHITE"))
            System.out.println("WHITE");
        if(viewController.getAvailableTowers().contains("BLACK"))
            System.out.println("BLACK");
        if(viewController.getNumOfPlayers()==3&&viewController.getAvailableTowers().contains("GRAY"))
            System.out.println("GRAY");
        if(viewController.getNumOfPlayers()==4)
            System.out.println("Two players can chose the same tower color and they will be in the same team");
        String colorChoice;
            do {
                colorChoice = input.nextLine().toUpperCase();

                if (!viewController.getAvailableTowers().contains(colorChoice))
                    System.out.println("Invalid Tower color, please select a valid one: ");
            } while (!viewController.getAvailableTowers().contains(colorChoice));
            viewController.getMainPlayer().setTower(colorChoice);
            viewController.getAvailableTowers().remove(colorChoice);
            System.out.println("Chosen Tower color: " + colorChoice.toLowerCase());
            cmTower message = new cmTower(colorChoice);
            String text = gson.toJson(message, cmTower.class);
            client.send(text);

    }

    /**
     * ask to choose a deck (the controller will do a check). Called by method
     */
    @Override
    public void askDeck(){
        System.out.println("\n\nCHOOSING DECK\n");
        System.out.println("Please select a deck");
        viewController.setResumeFrom(Phase.CHOOSE_SUPPORT_CARD);
        for (String deck : viewController.getAvailableDecks()) {
            System.out.println(deck);
        }
        String userChoice;
        boolean deckAvailable = false;
        do {
            userChoice = input.nextLine();

            for (String deck : viewController.getAvailableDecks()) {
                if (userChoice.equalsIgnoreCase(deck)) {
                    deckAvailable = true;
                    break;
                }
            }
            if (!deckAvailable)
                System.out.println("Invalid deck, please choose a valid one: ");
        } while (!deckAvailable);

        System.out.println("Chosen deck: " + userChoice);
        cmDeck message = new cmDeck(userChoice);
        String text = gson.toJson(message, cmDeck.class);
        client.send(text);
        viewController.getAvailableDecks().remove(userChoice.toUpperCase());
    }

    /**
     * ask to choose a support card. Called by method
     */
    @Override
    public void askSupportCard(){
        viewController.resetSupportCards();
        usedCharacterCard=false;
        viewController.setResumeFrom(Phase.CHOOSE_STUDENTS_TO_ISLAND);
        System.out.println("\n\nCHOOSING SUPPORT CARD\n");
        String card;
        System.out.println("Already chosen support cards:");
        for(PlayerView playerView: viewController.getPlayers()){
            if(playerView.getUsedSupportCard()!=null){
                card= "Card id: "+playerView.getUsedSupportCard().getId()+", card turn order: "+playerView.getUsedSupportCard().getTurnOrder()+", card movements: "+playerView.getUsedSupportCard().getMovement();
                System.out.println(card);
            }
        }
        System.out.println("Please chose the id of a support card among the following:");
        for (SupportCard supportCard : viewController.getMainPlayer().getSupportCards()) {
            card= "Card id: "+supportCard.getId()+", card turn order: "+supportCard.getTurnOrder()+", card movements: "+supportCard.getMovement();
            System.out.println(card);
        }
        boolean supportCardAvailable = false;
        int supportCardChoice = 0;
        String str;
            do {
                str = input.nextLine();

                if (stringToInteger(str) == null)
                    System.out.println("Not an int");
                else {
                    supportCardChoice = stringToInteger(str);
                    for (SupportCard supportCard : viewController.getMainPlayer().getSupportCards()) {
                        if (supportCardChoice == supportCard.getId()) {
                            supportCardAvailable = true;
                            break;
                        }
                    }
                    if (!supportCardAvailable)
                        System.out.println("Invalid support card, please choose a valid one: ");
                }
            } while (!supportCardAvailable);

        System.out.println("Chosen support card: " + supportCardChoice);
        cmSupportCard message = new cmSupportCard(supportCardChoice);
        String text = gson.toJson(message, cmSupportCard.class);
        client.send(text);
    }


    /**
     * ask to the client which students wants to move from H to D. Called by method
     */
    @Override
    public void askMoveStudentsHToD(){
        System.out.println("\n\nCHARACTER CARD ACTIVATION\n");
        if(viewController.getMode().equals("expert") && (!usedCharacterCard) && anyUsableCC())
            askActivateCharacterCard();

        viewController.setResumeFrom(Phase.CHOOSE_MOTHER_MOVEMENTS);

        int availableMovements = viewController.getAvailableStudentsMovements();
        System.out.println("\n\nSTUDENTS FROM HALL TO DINING HALL\n");
        if(availableMovements > 0) {
            ArrayList<StudentColor> chosenStudents;
            System.out.println("Your current Hall: ");
            for (StudentColor student : viewController.getMainPlayer().getHall()) {
                System.out.println(student);
            }
            System.out.println("\nYour current Dining Hall: ");
            boolean empty = true;
            for (StudentColor student : viewController.getMainPlayer().getDiningHall()) {
                if(empty)
                    empty = false;
                System.out.println(student);
            }
            if (empty)
                System.out.println("It is empty");
            System.out.println("\nYou can move " + availableMovements + " students");

                chosenStudents = askStudentsFromHall(availableMovements, false);
                viewController.getMainPlayer().addToDiningHall(chosenStudents);
                cmStudentsMovementsHToD message = new cmStudentsMovementsHToD(chosenStudents);
                client.send(gson.toJson(message, cmStudentsMovementsHToD.class));
        }
        else {
            System.out.println("You can't select anymore students.\n");
            client.send(gson.toJson(new cmStudentsMovementsHToD(null), cmStudentsMovementsHToD.class));
        }
        viewController.resetAvailableStudentsMovements();
    }

    /**
     * ask to the client which students wants to move from H to I (single or multiple). Called by method
     */
    @Override
    public void askMoveStudentsHToI(){
        System.out.println("\n\nCHARACTER CARD ACTIVATION\n");
        if(viewController.getMode().equals("expert") && (!usedCharacterCard) && anyUsableCC())
            askActivateCharacterCard();

        viewController.setResumeFrom(Phase.CHOOSE_STUDENTS_TO_DINING_HALL);

        int availableMovements;
        System.out.println("\n\nSTUDENTS FROM HALL TO ISLANDS\n");
        showIslands(null);
        System.out.println("Your current Hall: ");
        for(StudentColor student: viewController.getMainPlayer().getHall()){
            System.out.println(student);
        }

        String decisionToMoveStudents;
        do{
            System.out.println("\nDo you want to move some students from your Hall to an Island? (yes|no)");
            decisionToMoveStudents = input.nextLine();

        } while((!decisionToMoveStudents.equalsIgnoreCase("yes")) && (!decisionToMoveStudents.equalsIgnoreCase("no")));

        if(decisionToMoveStudents.equalsIgnoreCase("yes")) {
            int chosenIsland;
            int availableIslandChoices = 3;
            int numStudents = 0;
            ArrayList<StudentColor> studentsToI = new ArrayList<>();
            HashMap<Integer, ArrayList<StudentColor>> movementsHtoI = new HashMap<>();

            do {
                availableMovements = viewController.getAvailableStudentsMovements();
                System.out.println("Choose the island where you want to move the students. You can still move "
                        + availableMovements + " students");

                chosenIsland = askIsland(false, null)-1;
                availableIslandChoices--;
                System.out.println("Choose the number of students that you want to move to this island " +
                        "(from 0 up to " + availableMovements + ") ");
                String str;
                boolean notValidChoice;
                do {
                    str = input.nextLine();

                    if (stringToInteger(str) == null) {
                        System.out.println("Not an int");
                        notValidChoice = true;
                    }
                    else {
                        numStudents = stringToInteger(str);
                        notValidChoice = (numStudents > 3 || numStudents < 0);
                        if (notValidChoice)
                            System.out.println("Not valid, please choose a value between 0 and " + availableMovements + ": ");
                    }
                } while (notValidChoice);

                viewController.setAvailableStudentsMovements(availableMovements - numStudents);
                availableMovements-=numStudents;
                studentsToI.addAll(askStudentsFromHall(numStudents, false));
                movementsHtoI.put(chosenIsland, studentsToI);
                viewController.getMainPlayer().getHall().removeAll(studentsToI);
                viewController.getIsland(chosenIsland).addStudents(studentsToI);
                if(viewController.getAvailableStudentsMovements()>0) {
                    do {
                        System.out.println("Do you want to move any more students? (yes|no)");
                        decisionToMoveStudents = input.nextLine();

                    } while ((!decisionToMoveStudents.equalsIgnoreCase("yes")) && (!decisionToMoveStudents.equalsIgnoreCase("no")));
                }

            } while (availableIslandChoices > 0 && availableMovements > 0 && decisionToMoveStudents.equalsIgnoreCase("yes"));

            cmStudentsMovementsHToI message = new cmStudentsMovementsHToI(movementsHtoI);
            client.send(gson.toJson(message, cmStudentsMovementsHToI.class));
        }
        else
            client.send(gson.toJson(new cmStudentsMovementsHToI(null), cmStudentsMovementsHToI.class));
    }

    @Override
    public ArrayList<StudentColor> askStudentsFromHall(int numOfStudents, boolean showHall){
        StudentColor color;
        String studentChoice;
        ArrayList<StudentColor> chosenStudents = new ArrayList<>();

        if (showHall) {
            System.out.println("Your current Hall: ");
            for (StudentColor student : viewController.getMainPlayer().getHall())
                System.out.println(student);
        }

        System.out.println("Choose the students that you want to move: ");
        for(int i = 0; i< numOfStudents; i++) {
            do {
                System.out.println("Please select a student (choice " + (i+1) + ") : ");
                studentChoice = input.nextLine();

                color = StudentColor.getStudentFromString(studentChoice);
                if(color==null)
                    System.out.println("Not valid choice");
                else if(!viewController.getMainPlayer().getHall().contains(color)){
                    System.out.println("There are no "+studentChoice.toLowerCase()+" students");
                }
            } while (!viewController.getMainPlayer().getHall().contains(color));
            chosenStudents.add(color);
            viewController.getMainPlayer().removeFromHall(color);
        }
        return chosenStudents;
    }

    /**
     * ask where he wants to put mother nature (the controller will do a check). Called by method
     */
    @Override
    public void askMotherNatureMovements(){
        System.out.println("\n\nCHARACTER CARD ACTIVATION\n");
        if(viewController.getMode().equals("expert") && (!usedCharacterCard) && anyUsableCC())
            askActivateCharacterCard();

        viewController.setResumeFrom(Phase.CHOOSE_CLOUDS);

        int chosenIsland;
        int maxMovements= viewController.getMainPlayer().getUsedSupportCard().getMovement();
        boolean show= true;
        boolean validChoice;

        System.out.println("\n");
        System.out.println("\nMOTHER NATURE MOVEMENTS\n");
        System.out.println("Max movements: "+ maxMovements + "\n");

        do {
            chosenIsland = askIsland(show, maxMovements)-1;
            show=false;
            int movements = convertIslandToMovements(chosenIsland);
            validChoice = (movements <= maxMovements && movements > 0);
            if(!validChoice)
                System.out.println("Movements exceed max movements: " +maxMovements);
        }while (!validChoice);
        System.out.println("Chosen island: " + (chosenIsland+1));
        cmMoveMother message = new cmMoveMother(convertIslandToMovements(chosenIsland));
        client.send(gson.toJson(message, cmMoveMother.class));
    }

    private int convertIslandToMovements(int island){
        int movements = island-viewController.getMotherPosition();
        if(movements<0){
            movements = viewController.getAvailableIslands().size()+movements;
        }
        return movements;
    }

    public void showIslands(Integer range){
        int numOfIslands = viewController.getNumOfAvailableIslands();
        System.out.println("Mother Nature is on island "+(viewController.getMotherPosition()+1) + "\n");
        String text;
        System.out.println(viewController.getMainPlayer().getNickname() + "'s tower is " + viewController.getMainPlayer().getTower());
        for(PlayerView player : viewController.getPlayers()){
            System.out.println(player.getNickname() + "'s tower is " + player.getTower());
        }
        System.out.println();
        for(StudentColor color: StudentColor.values()){
            text= "The "+ color.toString().toLowerCase()+ " teacher is ";
            text = text + viewController.getTeacherOfColor(color);
            System.out.println(text);
        }
        System.out.println("\n");
        int from = 0;
        int to = numOfIslands;

        if (range != null) {
            from = viewController.getMotherPosition();
            if (from + range< numOfIslands) {
                to = from + range + 1;
                setRange(from, to);
            }
            else {
                setRange(from, to);
                setRange(0, (from + range) % numOfIslands+1);
            }
        }
        else {
            setRange(from,to);
        }
    }

    /**
     * set the values to pass to showIsland() so that only 5 island maxRange island are shown on the same line
     * @param from first island to be shown
     * @param to first island that will not be shown
     */
    private void setRange(int from, int to){
        int maxRange=5;
        while(to-from>maxRange){
            showRange(from,from+maxRange);
            from=from+maxRange;
        }
        showRange(from,to);
    }

    private void showRange(int from, int to) {
        ArrayList<IslandView> availableIslands = viewController.getAvailableIslands();
        int numOfSpaces=9;
        int maxSegmentLength=23;
        StringBuilder text= new StringBuilder();
        String segment;
        for(int i= from; i <to; i++) {
            segment = "Students on island " + (i+1);
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        System.out.println(text);
        for(StudentColor color: StudentColor.values()) {
            text=new StringBuilder();
            for (int i = from; i < to; i++) {
                segment = color.toString().toLowerCase() + " students: " + availableIslands.get(i).getStudentsByColor(color);
                text.append(segment);
                text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
            }
            System.out.println(text);
        }
        text=new StringBuilder();
        for(int i= from; i < to; i++){
            segment="Block on island: " + availableIslands.get(i).getBlockCard();
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        System.out.println(text);
        text=new StringBuilder();
        for(int i= from; i <to; i++){
            if (availableIslands.get(i).getTower() == null)
               segment="No tower on island";
            else
                segment="Tower on island: " + availableIslands.get(i).getTower();
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        System.out.println(text);
        text=new StringBuilder();
        for(int i= from; i < to; i++){
            if(availableIslands.get(i).getTower()!=null)
                segment="Num of towers: " + availableIslands.get(i).getNumOfTowers();
            else
                segment="";
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        System.out.println(text);
        System.out.println("\n");
    }



    /**
     * used to ask the player to choose an island
     * @param show if true then the list of islands is shown
     * @return an int between 1 and 12
     */
    public int askIsland(boolean show, Integer range) {
        int chosenIsland=-1;
        if (show)
            showIslands(range);
        System.out.println("Choose an island");
        boolean notValidChoice;
        String str;
        do {
            str = input.nextLine();

            if (stringToInteger(str) == null) {
                System.out.println("Not an int");
                notValidChoice = true;
            }
            else {
                chosenIsland = stringToInteger(str);
                notValidChoice = (chosenIsland < 1 || chosenIsland > viewController.getAvailableIslands().size());
                if (notValidChoice)
                    System.out.println("Invalid island, please choose a valid one: ");
            }
        } while (notValidChoice);
        return chosenIsland;
    }

    /**
     * ask the client from which C wants to take the students. Called by method
     */
    @Override
    public void askCloud(){
        System.out.println("\n\nCHARACTER CARD ACTIVATION\n");
        if(viewController.getMode().equals("expert") && (!usedCharacterCard) && anyUsableCC())
            askActivateCharacterCard();

        viewController.setResumeFrom(Phase.CHOOSE_SUPPORT_CARD);

        System.out.println("\n\nCHOOSING CLOUD\n");

        System.out.println("Please select a cloud");
        int i=1;
        for (CloudView cloudView: viewController.getAvailableClouds()) {
            if(!cloudView.getStudents().isEmpty()){
                System.out.println("Cloud: " + (i) + "\n" + "Students on Cloud " + (i+1) + ": " + cloudView.getStudents() + ";");
            }
            i++;
        }
        int chosenCloud = 0;
        boolean notValidChoice;
        String str;
        do {
            str = input.nextLine();

            if (stringToInteger(str) == null) {
                System.out.println("Not an int");
                notValidChoice = true;
            }
            else {
                chosenCloud = stringToInteger(str) - 1;
                notValidChoice = (chosenCloud < 0 || chosenCloud > viewController.getAvailableClouds().size() - 1);
                if (notValidChoice)
                    System.out.println("Invalid cloud, please choose a valid one: ");
            }
        } while (notValidChoice);
        viewController.getMainPlayer().addToHall(viewController.getCloud(chosenCloud).getStudents());
        viewController.getCloud(chosenCloud).removeStudents();
        System.out.println("Chosen cloud:  " + (chosenCloud+1));
        cmCloud message = new cmCloud(chosenCloud);
        client.send(gson.toJson(message, cmCloud.class));
    }

    /**
     * show a message (string) on the client screen. Called by message
     * @param message message to be shown
     */
    @Override
    public void showString(String message) {
        System.out.println(message);
    }

    /**
     * show the character card used by a player and his name. Called by message
     * @param id id of the used card
     */
    @Override
    public void showCharacterCard(int id) {
        System.out.println("Character card: " + id + "\n" + "Character card price: " + getCharacterCardById(id).getPrice());
        System.out.println("\n");
    }

    /**
     * show the support card used by a player and his name. Called by message
     * @param id id of the card
     */
    @Override
    public void showSupportCard(int id) {
        System.out.println(viewController.getCurrentPlayer() + " used the following support card");
        System.out.println("Support card: " + id + "\n" +
                "Support card movements: " + viewController.getSupportCardByID(id).getMovement() + "\n" +
                "Support card turn order: " +viewController.getSupportCardByID(id).getTurnOrder() + "\n");
        viewController.getPlayerByNick(viewController.getCurrentPlayer()).setUsedSupportCard(id);
    }


    /**
     * updates the price of a specific character card. Called by message
     * @param id id of the character card
     */
    @Override
    public void updateCharacterCardPrice(int id) {
        getCharacterCardById(id).increasePrice();
    }

    @Override
    public void showGameResults(ArrayList<String> winners, ArrayList<String> losers) {
        int numOfPlayers = viewController.getNumOfPlayers();
        if (numOfPlayers == 2)
            System.out.println("Winner: " + winners + "\n" + "Loser: " + losers);
        else if (numOfPlayers == 3)
            System.out.println("Winner: " + winners + "\n" + "Losers: " + losers);
        else if (numOfPlayers == 4)
            System.out.println("Winners: " + winners + "\n" + "Losers: " + losers);

        disconnectFromServer();
    }



    @Override
    public void updateStudentsOnCard(int id, ArrayList<StudentColor> students, boolean add){
        getCharacterCardById(id).updateStudentsOnCard(students, add);
    }

    /**
     * add a block-sign for the character card 5
     */
    @Override
    public void updateBlockOnCard(boolean add){
        getCharacterCardById(5).updateAvailableBlockCards(add);
    }

    @Override
    public void updatePlayerCoins(int coins) {
        viewController.updatePlayerCoins(coins);
    }


    public Client getClient(){
        return client;
    }

    @Override
    public void addAvailableCC(int card) {
        availableCC.add(ccc.createCard(card, this));
    }

    @Override
    public void disconnectFromServer() {
        System.out.println("The game is finished, press enter to close the game.");

        String str;
        str = input.nextLine();

        if (!str.isEmpty()) {
            cmDisconnect disconnect = new cmDisconnect();
            String text = gson.toJson(disconnect, cmNickname.class);
            client.send(text);
        }

        System.exit(0);
    }

    @Override
    public void closeGame(String message, int exit) {
        System.out.println(message);

        System.exit(exit);
    }

    @Override
    public void addStudentToPlayerD(String playerNick, ArrayList<StudentColor> students) {
        viewController.addStudentToPlayerD(playerNick, students);
    }

    @Override
    public void removeStudentsFromPlayerD(String playerNick, ArrayList<StudentColor> students) {
        viewController.removeStudentsFromPlayerD(playerNick, students);
    }

    @Override
    public void addStudentsToHall(ArrayList<StudentColor> students) {
        viewController.addStudentsToHall(students);
    }

    @Override
    public void removeFromPlayerHall(String nick, ArrayList<StudentColor> students) {
        viewController.removeFromPlayerHall(nick, students);
    }

    @Override
    public void removeStudentsFromHall(ArrayList<StudentColor> students) {
        viewController.removeStudentsFromHall(students);
    }

    @Override
    public void updateTeacher(HashMap<StudentColor, String> roles) {
        viewController.updateTeacher(roles);
    }

    @Override
    public void setPlayerDeck(String deck) {
        viewController.setPlayerDeck(deck);
    }

    @Override
    public void updateTowerColor(String tower) {
        viewController.updateTowerColor(tower);
    }

    @Override
    public void updateGameStatus(int numOfPlayers, String mode) {
        viewController.updateGameStatus(numOfPlayers, mode);
    }


    public CharacterCard getCharacterCardById(int id){
        for (CharacterCard card : availableCC)
            if(card.getCardId()==id)
                return card;
        return null;
    }


    public Integer stringToInteger(String str) {
        boolean valid = true;

        if(str.equals(""))
            return null;
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) < 48 || str.charAt(i) > 57) {
                valid = false;
                break;
            }
        }

        if (valid)
            return Integer.valueOf(str);

        return null;
    }


    public void showAllDH() {
        System.out.println("Your dining hall: " + viewController.getMainPlayer().getDiningHall());

        for (PlayerView pla : viewController.getPlayers())
            System.out.println("Player " + pla.getNickname() + "'s dining hall: " + pla.getDiningHall());
    }

    private boolean anyUsableCC(){
        updateUsableCC();
        return !usableCC.isEmpty();
    }

    private void updateUsableCC(){
        usableCC.clear();
        for (CharacterCard card : availableCC) {
            if (card.checkCCPrecondition()) {
                usableCC.add(card.getCardId());
            }
        }
    }
}

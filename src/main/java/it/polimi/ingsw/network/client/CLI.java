package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.clientModel.*;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.server.model.StudentColor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import it.polimi.ingsw.network.server.model.SupportCard;

public class CLI implements View, Runnable {

    private final HashMap<StudentColor, String> teachers;
    private final MotherNatureView motherNature;
    private final ArrayList<CloudView> availableClouds;
    private final ArrayList<IslandView> availableIslands;
    private Phase resumeFrom = null;
    private PlayerView player;
    private final ArrayList<PlayerView> players;
    private ArrayList<String> availableDecks;
    private ArrayList<String> availableTowers;
    private final ArrayList<CharacterCard> availableCC;

    private final ArrayList<CharacterCard> usableCC;
    private final CharacterCardCreator ccc;
    private boolean usedCharacterCard;
    private final Client client;
    private String currentPlayer;
    private String mode;
    private int numOfPlayers;
    private final BufferedReader br;
    private final Gson gson;
    private int availableStudentsMovements = 3;


    public CLI(Client client) {
        br = new BufferedReader(new InputStreamReader(System.in));
        this.client = client;
        gson = new Gson();
        availableCC = new ArrayList<>();
        ccc = new CharacterCardCreator();
        availableClouds = new ArrayList<>();
        availableIslands = new ArrayList<>();
        teachers = new HashMap<>();
        players = new ArrayList<>();
        motherNature = new MotherNatureView();
        initAvailableDecks();
        initAvailableTowers();
        initAvailableIslands();
        usableCC = new ArrayList<>();
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

    @Override
    public void run() {
    }

    @Override
    public PlayerView getPlayer() {
        return player;
    }

    public Phase getResumeFrom() {
        return resumeFrom;
    }

    /**
     * communicate to the client to insert the nickname. Called by message
     */
    @Override
    public void askNickName(){
        resumeFrom = Phase.CHOOSE_TOWER;

        String str;
        try {
            str = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player = new PlayerView(str);

        cmNickname message = new cmNickname(str);
        String text = gson.toJson(message, cmNickname.class);
        client.send(text);
    }

    /**
     * ask to the client if he want to choose a character card. Called by method
     */
    @Override
    public void askActivateCharacterCard(){

        if (player.getCoins() == 0) {
            System.out.println("You can't activate any character card because you have 0 coins");
            return;
        }

        System.out.println("You have " + player.getCoins() + " coins");
        System.out.println("The available character cards are the following\n");
        updateUsableCC();
        for(CharacterCard c: usableCC){
            c.showCard();
            System.out.println("\n");
        }
        String str = "";
        do {
            System.out.println("Do you want to activate a character card? (yes|no)");
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        int choice;
        CharacterCard card = null;
        String str = "";
        do {
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (stringToInteger(str) == null)
                System.out.println("Not an int");
            else {
                choice = stringToInteger(str);
                card = getUsableCharacterCardById(choice);
                if (card == null)
                    System.out.println("Not valid character card id");
            }
        } while(card == null);

        if (!card.activate())
            askActivateCharacterCard();
        else {
            usedCharacterCard = true;
            player.decreaseCoins(card.getPrice());
        }
    }

    /**
     * choose the numOfPlayers and mode. (only to the first player connected). Called by message
     */
    @Override
    public void askSetGameStatus() {
        resumeFrom = Phase.CHOOSE_TOWER;
        System.out.println("Choose number of players (2,3 or 4): ");
        int numOfPlayers = 0;
        String mode;
        boolean notValidChoice;
        String str = "";
        do{
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            try {
                mode = br.readLine().toLowerCase();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            notValidChoice = (!mode.equals("expert") && !mode.equals("normal"));
            if (notValidChoice)
                System.out.println("Invalid game mode, please choose a valid one (between normal and expert): ");
        } while (notValidChoice);

        this.mode = mode;
        this.numOfPlayers = numOfPlayers;

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
        resumeFrom = Phase.CHOOSE_DECK;
        System.out.println("Choose tower color:");
        System.out.println("The available towers are: ");
        if(availableTowers.contains("WHITE"))
            System.out.println("WHITE");
        if(availableTowers.contains("BLACK"))
            System.out.println("BLACK");
        if(numOfPlayers==3&&availableTowers.contains("GRAY"))
            System.out.println("GRAY");
        if(numOfPlayers==4)
            System.out.println("Two players can chose the same tower color and they will be in the same team");
        String colorChoice = "";
            try {
                do {
                    colorChoice = br.readLine().toUpperCase();
                    if (!availableTowers.contains(colorChoice))
                        System.out.println("Invalid Tower color, please select a valid one: ");
                } while (!availableTowers.contains(colorChoice));
            }catch(IOException e)
            {e.printStackTrace();}
            player.setTower(colorChoice);
            availableTowers.remove(colorChoice);
            System.out.println("Chosen Tower color: " + colorChoice.toLowerCase());
            Gson gson = new Gson();
            cmTower message = new cmTower(colorChoice);
            String text = gson.toJson(message, cmTower.class);
            client.send(text);

    }

    /**
     * ask to choose a deck (the controller will do a check). Called by method
     */
    @Override
    public void askDeck(){
        System.out.println("Please select a deck");
        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        for (String deck : availableDecks) {
            System.out.println(deck);
        }
        String userChoice ="";
        boolean deckAvailable = false;
        try {
            do {
                userChoice = br.readLine();
                for (String deck : availableDecks) {
                    if (userChoice.toUpperCase().equals(deck)) {
                        deckAvailable = true;
                        break;
                    }
                }
                if (!deckAvailable)
                    System.out.println("Invalid deck, please choose a valid one: ");
            } while (!deckAvailable);
        }catch(IOException e)
        {e.printStackTrace();}
        System.out.println("Chosen deck: " + userChoice);
        cmDeck message = new cmDeck(userChoice);
        String text = gson.toJson(message, cmDeck.class);
        client.send(text);
        availableDecks.remove(userChoice.toUpperCase());
    }

    /**
     * ask to choose a support card. Called by method
     */
    @Override
    public void askSupportCard(){
        resetSupportCards();
        usedCharacterCard=false;
        resumeFrom= Phase.CHOOSE_STUDENTS_TO_ISLAND;
        String card;
        System.out.println("Already chosen support cards:");
        for(PlayerView playerView: players){
            if(playerView.getUsedSupportCard()!=null){
                card= "Card id: "+playerView.getUsedSupportCard().getId()+", card turn order: "+playerView.getUsedSupportCard().getTurnOrder()+", card movements: "+playerView.getUsedSupportCard().getMovement();
                System.out.println(card);
            }
        }
        System.out.println("Please chose the id of a support card among the following:");
        for (SupportCard supportCard : player.getSupportCards()) {
            card= "Card id: "+supportCard.getId()+", card turn order: "+supportCard.getTurnOrder()+", card movements: "+supportCard.getMovement();
            System.out.println(card);
        }
        boolean supportCardAvailable = false;
        int supportCardChoice = 0;
        String str = "";
            do {
                try {
                    str = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (stringToInteger(str) == null)
                    System.out.println("Not an int");
                else {
                    supportCardChoice = stringToInteger(str);
                    for (SupportCard supportCard : player.getSupportCards()) {
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
     * ask to the client which students wants to move from H to D. Called by method
     */
    @Override
    public void askMoveStudentsHToD(){
        if(mode.equals("expert") && (!usedCharacterCard)&& anyUsableCC())
            askActivateCharacterCard();

        resumeFrom = Phase.CHOOSE_MOTHER_MOVEMENTS;

        if(availableStudentsMovements > 0) {
            ArrayList<StudentColor> chosenStudents;
            System.out.println("Your current Hall: ");
            for (StudentColor student : player.getHall()) {
                System.out.println(student);
            }
            System.out.println("Your current Dining Hall: ");
            boolean empty = true;
            for (StudentColor student : player.getDiningHall()) {
                if(empty)
                    empty = false;
                System.out.println(student);
            }
            if (empty)
                System.out.println("It is empty");
            System.out.println("You can move " + availableStudentsMovements + " students");

                chosenStudents= askStudentsFromHall(availableStudentsMovements, false);
                player.addToDiningHall(chosenStudents);
                cmStudentsMovementsHToD message = new cmStudentsMovementsHToD(chosenStudents);
                client.send(gson.toJson(message, cmStudentsMovementsHToD.class));
        }
        else {
            System.out.println("You can't select anymore students.");
            client.send(gson.toJson(new cmStudentsMovementsHToD(null), cmStudentsMovementsHToD.class));
        }
        availableStudentsMovements = 3;
    }

    /**
     * ask to the client which students wants to move from H to I (single or multiple). Called by method
     */
    @Override
    public void askMoveStudentsHToI(){
        if(mode.equals("expert") && (!usedCharacterCard)&& anyUsableCC())
            askActivateCharacterCard();

        resumeFrom = Phase.CHOOSE_STUDENTS_TO_DINING_HALL;

        System.out.println("\n\nSTUDENTS FROM HALL TO ISLANDS");
        showIslands(null);
        System.out.println("Your current Hall: ");
        for(StudentColor student: player.getHall()){
            System.out.println(student);
        }

        String decisionToMoveStudents = "";
        do{
            System.out.println("Do you want to move some students from your Hall to an Island? (yes|no)");
            try {
                decisionToMoveStudents = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while((!decisionToMoveStudents.equalsIgnoreCase("yes")) && (!decisionToMoveStudents.equalsIgnoreCase("no")));

        if(decisionToMoveStudents.equalsIgnoreCase("yes")) {
            int chosenIsland;
            int availableIslandChoices = 3;
            int numStudents = 0;
            ArrayList<StudentColor> studentsToI = new ArrayList<>();
            HashMap<Integer, ArrayList<StudentColor>> movementsHtoI = new HashMap<>();

            do {
                System.out.println("Choose the island where you want to move the students. You can still move "
                        + availableStudentsMovements + " students");

                chosenIsland = askIsland(false, null)-1;
                availableIslandChoices--;
                System.out.println("Choose the number of students that you want to move to this island " +
                        "(from 0 up to " + availableStudentsMovements + ") : ");
                String str = "";
                boolean notValidChoice;
                do {
                    try {
                        str = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (stringToInteger(str) == null) {
                        System.out.println("Not an int");
                        notValidChoice = true;
                    }
                    else {
                        numStudents = stringToInteger(str);
                        notValidChoice = (numStudents > 3 || numStudents < 0);
                        if (notValidChoice)
                            System.out.println("Not valid, please choose a value between 0 and " + availableStudentsMovements + ": ");
                    }
                } while (notValidChoice);

                availableStudentsMovements -= numStudents;
                studentsToI.addAll(askStudentsFromHall(numStudents, false));
                movementsHtoI.put(chosenIsland, studentsToI);
                player.getHall().removeAll(studentsToI);
                availableIslands.get(chosenIsland).addStudents(studentsToI);
                if(availableStudentsMovements>0) {
                    decisionToMoveStudents = "false";
                    do {
                        System.out.println("Do you want to move any more students? (yes|no)");
                        try {
                            decisionToMoveStudents = br.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } while ((!decisionToMoveStudents.equalsIgnoreCase("yes")) && (!decisionToMoveStudents.equalsIgnoreCase("no")));
                }

            } while (availableIslandChoices > 0 && availableStudentsMovements > 0 && decisionToMoveStudents.equalsIgnoreCase("yes"));

            cmStudentsMovementsHToI message = new cmStudentsMovementsHToI(movementsHtoI);
            client.send(gson.toJson(message, cmStudentsMovementsHToI.class));
        }
        else
            client.send(gson.toJson(new cmStudentsMovementsHToI(null), cmStudentsMovementsHToI.class));
    }

    @Override
    public ArrayList<StudentColor> askStudentsFromHall(int numOfStudents, boolean showHall){
        StudentColor color;
        String studentChoice = "";
        ArrayList<StudentColor> chosenStudents = new ArrayList<>();

        if (showHall) {
            System.out.println("Your current Hall: ");
            for (StudentColor student : player.getHall())
                System.out.println(student);
        }

        System.out.println("Choose the students that you want to move: ");
        for(int i = 0; i< numOfStudents; i++) {
            do {
                System.out.println("Please select a student (choice " + (i+1) + ") : ");
                try {
                    studentChoice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                color = StudentColor.getStudentFromString(studentChoice);
                if(color==null)
                    System.out.println("Not valid choice");
                else if(!player.getHall().contains(color)){
                    System.out.println("There are no "+studentChoice.toLowerCase()+" students");
                }
            } while (!player.getHall().contains(color));
            chosenStudents.add(color);
            player.removeFromHall(color);
        }
        return chosenStudents;
    }

    /**
     * ask the client from which C wants to take the students. Called by method
     */
    @Override
    public void askCloud(){
        System.out.println("Please select a cloud");
        if(mode.equals("expert") && (!usedCharacterCard)&& anyUsableCC())
            askActivateCharacterCard();

        resumeFrom = Phase.CHOOSE_SUPPORT_CARD;
        for (int i = 0; i < availableClouds.size(); i++) {
            if(!availableClouds.get(i).getStudents().isEmpty()){
                System.out.println("Cloud: " + (i+1) + "\n" + "Students on Cloud " + (i+1) + ": " + availableClouds.get(i).getStudents() + ";");
            }
        }
        int chosenCloud = 0;
        boolean notValidChoice;
        String str = "";
        do {
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (stringToInteger(str) == null) {
                System.out.println("Not an int");
                notValidChoice = true;
            }
            else {
                chosenCloud = stringToInteger(str) - 1;
                notValidChoice = (chosenCloud < 0 || chosenCloud > availableClouds.size() - 1);
                if (notValidChoice)
                    System.out.println("Invalid cloud, please choose a valid one: ");
            }
        } while (notValidChoice);
        player.addToHall(availableClouds.get(chosenCloud).getStudents());
        availableClouds.get(chosenCloud).removeStudents();
        System.out.println("Chosen cloud:  " + (chosenCloud+1));
        cmCloud message = new cmCloud(chosenCloud);
        client.send(gson.toJson(message, cmCloud.class));

    }

    /**
     * ask where he wants to put mother nature (the controller will do a check). Called by method
     */
    @Override
    public void askMotherNatureMovements(){
        if(mode.equals("expert") && (!usedCharacterCard)&& anyUsableCC())
            askActivateCharacterCard();

        resumeFrom = Phase.CHOOSE_CLOUDS;
        int chosenIsland;
        int maxMovements=player.getUsedSupportCard().getMovement();
        boolean show= true;
        boolean validChoice;

        System.out.println("\n");
        System.out.println("MOTHER NATURE MOVEMENTS\n");
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
        int movements = island-motherNature.getCurrentIsland();
        if(movements<0){
            movements=availableIslands.size()+movements;
        }
        return movements;
    }

    public void showIslands(Integer range){
        System.out.println("\nMother Nature is on island "+(motherNature.getCurrentIsland()+1));
        String text;
        for(StudentColor color: StudentColor.values()){
            text= "The "+ color.toString().toLowerCase()+ " teacher is ";
            text = text + teachers.getOrDefault(color, "yet to be decided");
            System.out.println(text);
        }
        System.out.println("\n");
        int from = 0;
        int to = availableIslands.size();

        if (range != null) {
            from = motherNature.getCurrentIsland();
            if (from + range< availableIslands.size()) {
                to = from + range + 1;
                setRange(from, to);
            }
            else {
                setRange(from, to);
                setRange(0, (from + range) % availableIslands.size()+1);
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
        int numOfSpaces=9;
        int maxSegmentLength=23;
        String text="";
        String segment;
        for(int i= from; i <to; i++) {
            segment = "Students on island " + (i+1);
            text = text+segment;
            for(int j=1;j<(numOfSpaces+(maxSegmentLength-segment.length()) );j++ )
                text=text+" ";
        }
        System.out.println(text);
        for(StudentColor color: StudentColor.values()) {
            text="";
            for (int i = from; i < to; i++) {
                segment = color.toString().toLowerCase() + " students: " + availableIslands.get(i).getStudentsByColor(color);
                text = text + segment;
                for (int j = 1; j < (numOfSpaces + (maxSegmentLength - segment.length())); j++)
                    text = text + " ";
            }
            System.out.println(text);
        }
        text="";
        for(int i= from; i < to; i++){
            segment="Block on island: " + availableIslands.get(i).getBlockCard();
            text = text+segment;
            for(int j=1;j<(numOfSpaces+(maxSegmentLength-segment.length()) );j++ )
                text=text+" ";
        }
        System.out.println(text);
        text="";
        for(int i= from; i <to; i++){
            if (availableIslands.get(i).getTower() == null)
               segment="No tower on island";
            else
                segment="Tower on island: " + availableIslands.get(i).getTower();
            text = text+segment;
            for(int j=1;j<(numOfSpaces+(maxSegmentLength-segment.length()) );j++ )
                text=text+" ";
        }
        System.out.println(text);
        text="";
        for(int i= from; i < to; i++){
            if(availableIslands.get(i).getTower()!=null)
                segment="Num of towers: " + availableIslands.get(i).getNumOfTowers();
            else
                segment="";
            text = text+segment;
            for(int j=1;j<(numOfSpaces+(maxSegmentLength-segment.length()) );j++ )
                text=text+" ";
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
        String str = "";
        do {
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (stringToInteger(str) == null) {
                System.out.println("Not an int");
                notValidChoice = true;
            }
            else {
                chosenIsland = stringToInteger(str);
                notValidChoice = (chosenIsland < 1 || chosenIsland > availableIslands.size());
                if (notValidChoice)
                    System.out.println("Invalid island, please choose a valid one: ");
            }
        } while (notValidChoice);
        return chosenIsland;
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

    public void setAdditionalTurnOrder(int id, double additionalTurnOrder){
        getSupportCardByID(id).setAdditionalTurnOrder(additionalTurnOrder);
    }

    /**
     * show the support card used by a player and his name. Called by message
     * @param id id of the card
     */
    @Override
    public void showSupportCard(int id) {
        System.out.println(currentPlayer + " used the following support card");
        System.out.println("Support card: " + id + "\n" +
                "Support card movements: " + getSupportCardByID(id).getMovement() + "\n" +
                "Support card turn order: " + getSupportCardByID(id).getTurnOrder() + "\n");
        getPlayerByNick(currentPlayer).setUsedSupportCard(id);
    }

    /**
     * show on the client screen which support card is using during that turn. Called by method
     */
    @Override
    public void updateUsedSupportCard(int id) {
        getPlayerByNick(currentPlayer).setUsedSupportCard(id);
        updateAvailableSupportCards();
    }

    @Override
    public PlayerView getPlayerByNick(String nick){
        if (player.getNickname().equals(nick))
            return player;
        for(PlayerView player: players){
            if(player.getNickname().equals(nick))
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
     * @param id id of the character card
     */
    @Override
    public void updateCharacterCardPrice(int id) {
        getCharacterCardById(id).increasePrice();
    }

    /**
     * updates the new position of mother nature (when other players changes it). Called by message
     * @param island island where mother nature is
     */
    @Override
    public void updateMotherPosition(int island) {
        motherNature.setCurrentIsland(island);
    }

    /**
     * update the Tower color chosen by the current player. Called by message
     * @param tower color of the chosen tower
     */
    @Override
    public void updateTowerColor(String tower) {
        getPlayerByNick(currentPlayer).setTower(tower);
        availableTowers.remove(tower);
    }

    /**
     * place a block on the specified island. Called by message
     * @param island: island to lock
     */
    @Override
    public void blockIsland(int island) {
        availableIslands.get(island).addBlockCard();
    }

    /**
     * removes the block on the specified island
     * @param island island to unblock
     */
    @Override
    public void unlockIsland(int island){
        availableIslands.get(island).removeBlockCard();
    }

    /**
     * merge two islands. Called by message
     * @param toBeMerged island that will be merged
     * @param mergeTo island in to which the other island will be merged
     */
    @Override
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
    @Override
    public void addStudentsOnIsland(int island, ArrayList<StudentColor> students) {
        availableIslands.get(island).addStudents(students);
    }

    /**
     * updates the added students in the dining hall of the specified player. Called by message
     * @param nick nick of the player
     * @param students students to add
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

        disconnectFromServer();
    }

    /**
     * remove the students from the dining hall of a specified player. Called by message
     * @param nick nick of the player
     * @param students students to remove
     */
    @Override
    public void removeStudentsFromPlayerD(String nick, ArrayList<StudentColor> students) {
        getPlayerByNick(nick).removeFromDiningHall(students);
    }

    /**
     * update the hall of a specified player. Called by message
     * @param students students to add
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
    public void updateStudentsOnCard(int id, ArrayList<StudentColor> students, boolean add){
        getCharacterCardById(id).updateStudentsOnCard(students, add);
    }

    /**
     * update the amount of coins that the player has. Called by message
     * @param coin amount of coins
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
    public ArrayList<IslandView> getAvailableIslands() {
        return availableIslands;
    }


    /**
     * keep track of the status of the turn
     */
    public void resumeFrom(){
        switch (resumeFrom) {
            case CHOOSE_SUPPORT_CARD -> askSupportCard();
            case CHOOSE_MOTHER_MOVEMENTS -> askMotherNatureMovements();
            case CHOOSE_CLOUDS -> askCloud();
            case CHOOSE_TOWER -> askTower();
            case CHOOSE_DECK -> askDeck();
            case CHOOSE_STUDENTS_TO_DINING_HALL -> askMoveStudentsHToD();
            case CHOOSE_STUDENTS_TO_ISLAND -> askMoveStudentsHToI();
        }

    }

    /**
     * add a block-sign for the character card 5
     */
    @Override
    public void updateBlockOnCard(boolean add){
        getCharacterCardById(5).updateAvailableBlockCards(add);
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
    @Override
    public void updateTowerOnIsland(int island, String tower) {
        availableIslands.get(island).setTower(tower);
    }

    /**
     * add students on cloud
     * if the cloud doesn't exit, it will be created
     * @param cloud index of the cloud (first index is 0)
     * @param students students to add
     */
    @Override
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
        getPlayerByNick(currentPlayer).setDeck(deck);
        availableDecks.remove(deck.toUpperCase());
    }

    /**
     * updates the support card used by the current player
     */
    @Override
    public void setSupportCard(int id){
        getPlayerByNick(currentPlayer).setUsedSupportCard(id);
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
        try {
            str = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!str.isEmpty()) {
            cmDisconnect disconnect = new cmDisconnect(player.getNickname());
            String text = gson.toJson(disconnect, cmNickname.class);
            client.send(text);
        }

        closeGame();
    }

    @Override
    public void closeGame() {
        System.exit(-1);
    }


    public CharacterCard getCharacterCardById(int id){
        for (CharacterCard card : availableCC)
            if(card.getCardId()==id)
                return card;
        return null;
    }

    @Override
    public void addPlayers(ArrayList<String> players){
        for(String nick: players){
            if(!player.getNickname().equals(nick))
                this.players.add(new PlayerView(nick));
        }
    }

    @Override
    public void removeFromPlayerHall(String nick, ArrayList<StudentColor> students) {
        getPlayerByNick(nick).removeFromHall(students);
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

    public int getMotherPosition() {
        return motherNature.getCurrentIsland();
    }

    public void showAllDH() {
        System.out.println("Your dining hall: " + player.getDiningHall());

        for (PlayerView pla : players)
            System.out.println("Player " + pla.getNickname() + "'s dining hall: " + pla.getDiningHall());
    }

    private boolean anyUsableCC(){
        updateUsableCC();
        return !usableCC.isEmpty();

    }

    private void updateUsableCC(){
        usableCC.clear();
        for(CharacterCard card: availableCC){
            if(card.checkCCPrecondition())
                usableCC.add(card);
        }
    }

    public CharacterCard getUsableCharacterCardById(int id){
        for (CharacterCard card : usableCC)
            if(card.getCardId()==id)
                return card;
        return null;
    }
}

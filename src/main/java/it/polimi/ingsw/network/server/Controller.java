package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.Exceptions.LastSupportCardUsedException;
import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.server.model.*;

import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;
import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCardBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private final Gson gson;
    private final Game game;
    private GameBoard board;
    private CharacterCardBoard characterCardBoard;
    /**
     * necessary  to send messages
     */
    private final Server server;

    /**
     * constructor
     * @param server is used to set server attribute
     */
    public Controller(Server server){
        gson= new Gson();
        game= new Game();

        this.server=server;
    }

    public void notifyGameStatus(){
        smGameStatus message= new smGameStatus(game.getNumOfPlayers(), game.getMode());
        server.sendAll(gson.toJson(message, smGameStatus.class));
    }

    /**
     * @return true if game numOfPlayers and game mode have been set
     */
    public boolean gameIsSet(){
        return game.gameIsSet();
    }

    /**
     * creates a new Player with the specified nick and adds it to the players list
     * @param nick name of the new player
     */
    public void addPlayerToGame(String nick){

        game.getPlayers().add(new Player(nick));
        if(game.getPlayers().size()==1){
            game.setCurrentPlayer();
        }
    }

    /**
     * notify players of other players
     */
    public void notifyPlayers(){
        smAddPlayers message = new smAddPlayers();
        for(Player player: game.getPlayers())
            message.addNick(player.getNickName());
        server.sendAll(gson.toJson(message, smAddPlayers.class));
    }

    /**
     * this method is used to check if a player with the specified nick already exists
     * @param nick nick we want to check if it is already present
     * @return true if a player with the specified nick is already present.
     */
    public  boolean alreadyPresent(String nick){
        for(Player player : game.getPlayers())
            if(player.getNickName().equals(nick))
                return true;
        return false;
    }

    public Game getGame() {
        return game;
    }

    public GameBoard getBoard(){
        return board;
    }

    public CharacterCardBoard getCharacterCardBoard(){
        return characterCardBoard;
    }

    /**
     * this method is used to notify all player of the available character cards for this game
     */
    public void notifyAvailableCC(){
        ArrayList<Integer> availableCards = new ArrayList<>();
        for (CharacterCard card : characterCardBoard.getAvailableCards()) {
            availableCards.add(card.getCardId());
        }
        smAvailableCharacterCards message = new smAvailableCharacterCards(availableCards);
        server.sendAll(gson.toJson(message, smAvailableCharacterCards.class));
        for (CharacterCard card : characterCardBoard.getAvailableCards()) {
            if (card.getCardId() == 1)
                refillCard(1, 4);
            else if (card.getCardId() == 7)
                refillCard(7, 6);
            else if (card.getCardId() == 11)
                refillCard(11, 4);
        }
    }

    /**
     * notify to the view the initialization of the islands
     */
    public void notifyStudentsOnIslands() {
        smStudentsOnIsland message;
        for (int i=0; i<board.getIslands().size(); i++) {
            message = new smStudentsOnIsland(i, board.getIsland(i).getStudents());
            server.sendAll(gson.toJson(message, smStudentsOnIsland.class));
        }
    }

    /**
     * notify to the view the position of Mother Nature
     */
    public void notifyMotherNaturePosition(){
        smMotherPosition message= new smMotherPosition(board.getMotherNature().getCurrentIsland());
        server.sendAll(gson.toJson(message, smMotherPosition.class));
    }

    public void notifyCurrentPlayer() {
        String nickname = game.getCurrentPlayer().getNickName();
        smCurrentPlayer message = new smCurrentPlayer("The current player is " + nickname, nickname);
        server.sendAll(gson.toJson(message, smCurrentPlayer.class));
    }

    public void initializePlayersHall() {
        for (Player player : game.getPlayers()) {
            try {
                player.getBoard().addStudentsToHall(board.getBag().draw(7));
            } catch (LastStudentDrawnException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * sets game status
     * @param mode it could be "normal" or "expert"
     * @param numOfPlayers it is an int between 1 and 4
     */
    public void setGameStatus(String mode, int numOfPlayers){
        game.setMode(mode);
        game.setNumOfPlayers(numOfPlayers);
        board=new GameBoard(numOfPlayers);
        board.setPlayers(game.getPlayers());
        if(mode.equals("expert")){
            characterCardBoard=new CharacterCardBoard();
            characterCardBoard.initializeAvailableCC();

        }
    }

    public boolean gameIsFull(){
        return game.gameIsFull();
    }

    /**
     * sets the tower of the specified color for the current player and notify other players
     * @param tower color of the tower to set
     */
    public void setTower( String tower){
        String nick;
        nick = game.getCurrentPlayer().getNickName();
        ServerMessage message;
        message = new smStudentsInHall(game.getCurrentPlayer().getBoard().getHall().getStudents(), true);
        server.sendAll(gson.toJson(message, smStudentsInHall.class));
        game.getCurrentPlayer().setTower(board.getTower(tower));
        String text="Player " + nick + " has chosen the "+ tower+ " tower.";
        if(game.getNumOfPlayers()==4) {
            for (Player player : game.getPlayers()) {
                if(player.getTower()!=null)
                    if (game.getCurrentPlayer().getTower().equals(player.getTower()))
                        text = text + "\nThey will be in team with " + player.getNickName();
            }
        }
        message= new smChosenTower(
                text,
                tower);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smChosenTower.class));
        game.nextPlayer();
        nick = game.getCurrentPlayer().getNickName();
        if(game.firstPlayerOfRound()){
            text = nick + " will chose their deck";
        }
        else{
            text = nick + " will chose their tower";
        }
        smCurrentPlayer message2 = new smCurrentPlayer(
                text,
                nick
        );
        server.sendAll(gson.toJson(message2, smCurrentPlayer.class));
    }

    /**
     * set the deck of the current player and notify other players
     * called by client message
     * @param deck color of the deck to set
     */
    public void setDeck(String deck){
        String nick = game.getCurrentPlayer().getNickName();
        String text = nick + " chosen the " + deck + " deck";
        smUsedDeck message = new smUsedDeck(
                text,
                deck
        );
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smUsedDeck.class));
        game.nextPlayer();
        nick = game.getCurrentPlayer().getNickName();
        if(game.firstPlayerOfRound()){
            text = nick + " will chose their support card";
            notifyStudentsOnCloud();
        }
        else {
            text = nick + " will chose their deck";
        }
        smCurrentPlayer message2 = new smCurrentPlayer(
                text,
                nick
        );
        server.sendAll(gson.toJson(message2, smCurrentPlayer.class));
    }

    /**
     * Notify students on clouds at the start of the game
     */
    private void notifyStudentsOnCloud(){
        String text;
        smStudentsOnCloud message;
        int num;
        for(Cloud cloud : board.getClouds()){
            num = board.getCloudNumber(cloud) ;
            text = "Cloud " + num + " have been refilled";
            message = new smStudentsOnCloud(
                    text,
                    num-1,
                    cloud.getStudents()
            );
            server.sendAll(gson.toJson(message, smStudentsOnCloud.class));
        }
    }

    /**
     * Refills clouds and notify players. if the bag is emptied while refilling cloud players will be notified
     * that this will be the last round because the last student has been drawn
     */
    private void refillClouds(){
        int studentsToRefill;
        int studentsRefilled;
        String text;
        for(Cloud cloud : board.getClouds()){
            if(!game.lastStudentDrawn()) {
                studentsToRefill= cloud.getMaxStudents();
                try {
                    board.refillCloud(cloud);
                } catch (LastStudentDrawnException e) {
                    game.setLastStudentDrawn(true);
                    smNotify message = new smNotify("This will be the last round because the last student has been drawn while refilling clouds");
                    server.sendAll(gson.toJson(message, smNotify.class));
                }
                int num = board.getCloudNumber(cloud);
                studentsRefilled = cloud.getStudents().size();
                if(studentsToRefill==studentsRefilled)
                    text = "cloud number " + num + " has been refilled";
                else
                    text = "Only " + studentsRefilled + " students have been placed on cloud " + num + "because there are no more available students to be drawn";
                smStudentsOnCloud message = new smStudentsOnCloud(
                        text,
                        num - 1,
                        cloud.getStudents());
                server.sendAll(gson.toJson(message, smStudentsOnCloud.class));
            }
            else{
                text = "cloud " + board.getCloudNumber(cloud) + " has not been refilled because there are no more available students to be drawn";
                smNotify message = new smNotify(text);
                server.sendAll(gson.toJson(message, smNotify.class));
            }
        }
        game.nextPlayer();
        String nick = game.getCurrentPlayer().getNickName();
        smCurrentPlayer message = new smCurrentPlayer(
                nick + " will chose a support card",
                nick
        );
        server.sendAll(gson.toJson(message, smCurrentPlayer.class));
    }

    /**
     * Checks if the specified support card is valid and notify all players.
     * called by client message SupportCard
     * if it is not valid only current player will be notified
     * if the last support card has been used all players will be notified that this will be the last round
     * @param cardId id of the support card
     */
    public void setUsedSupportCard(int cardId){
        String nick =game.getCurrentPlayer().getNickName();
        ServerMessage message;
        boolean valid=true;
        String text;
        ArrayList <Integer> usedSupportCards = getUsedSupportCards();
        ArrayList<Integer> usableSupportCards = new ArrayList<>();
        if(usedSupportCards.contains(cardId)){
            for(SupportCard card : game.getCurrentPlayer().getDeck()) {
                if (!getUsedSupportCards().contains(card.getId())) {
                    valid = false;
                    usableSupportCards.add(card.getId());
                }
            }
            if(valid) {
                int additionalTurnOrder=0;
                for (Integer usedSupportCard : usedSupportCards) {
                    if (usedSupportCard == cardId)
                        additionalTurnOrder++;
                }
                game.getCurrentPlayer().getCardById(cardId).setAdditionalTurnOrder(additionalTurnOrder*0.1);
                text="All "+nick +" available support cards have been used by other players in this round./n"+"" + nick+
                        " will use the chosen support card but you will play after the player that have already chosen the same one";
            }
            else {
                text="Your chosen support card has already been used by another player in this round and you have support cards that you can use in this round:\n";
                text=text+usableSupportCards+"\n";
                text= text +"Please choose another support card";
                message= new smNotValidSupportCard(text);
                String json = gson.toJson(message, smNotValidSupportCard.class);
                server.sendMessage(game.getCurrentPlayer().getNickName(),json);
                return;
            }
        }
        else {
            text = "Player " + nick + " used support card number " + cardId;
        }
        message=new smUsedSupportCard(
                text,
                cardId,
                game.getCurrentPlayer().getCardById(cardId).getAdditionalTurnOrder());
        server.sendAll(gson.toJson(message, smUsedSupportCard.class));
        try {
            game.getCurrentPlayer().setUsedSupportCard(cardId);
        } catch (LastSupportCardUsedException e) {
            if(!game.lastSupportCardUsed()) {
                game.setLastSupportCardUsed();
                text = "This will be the last round because " + nick +
                        " has used their last support card";
                message = new smNotify(text);
                String json = gson.toJson(message, smNotify.class);
                server.sendAll(json);
            }
        }
        finally {
            if (game.lastPlayerOfRound())
                TurnOrder();
            else {
                game.nextPlayer();
                String text2 = game.getCurrentPlayer().getNickName() + " will chose their support card";
                smCurrentPlayer message2 = new smCurrentPlayer(text2, game.getCurrentPlayer().getNickName());
                server.sendAll(gson.toJson(message2, smCurrentPlayer.class));
            }
        }
    }

    /**
     * checks the support card used by other players in this round
     * @return an ArrayList with the card ids of all already used support card in this round
     */
    private ArrayList<Integer> getUsedSupportCards(){
        ArrayList<Integer> supportCards= new ArrayList<>();
        for(Player player : game.getPlayers()){
            if(!player.equals(game.getCurrentPlayer()))
                if(player.getUsedSupportCard()!=null)
                    supportCards.add(player.getUsedSupportCard().getId());
        }
        return supportCards;
    }

    /**
     * determines the players' turn order for the following action and planning phase
     * notify all players
     */
    private void TurnOrder(){
        Player firstPlayer;
        ArrayList<Player> roundOrder=new ArrayList<>();
        ArrayList<String> nicks=new ArrayList<>();
        while (game.getPlayers().size()>0){
            firstPlayer=game.getPlayers().get(0);
            for(Player player : game.getPlayers()){
                if(player.getUsedSupportCard().getTurnOrder()<firstPlayer.getUsedSupportCard().getTurnOrder())
                    firstPlayer=player;
            }
            roundOrder.add(firstPlayer);
            game.getPlayers().remove(firstPlayer);
        }
        game.getPlayers().addAll(roundOrder);
        game.setCurrentPlayer();
        for(Player player : roundOrder) {
            nicks.add(player.getNickName());
        }
        String s="The players order for the current action phase and the next planning phase will be the following: "+
                nicks;
        smNotify message = new smNotify(s);
        String json= gson.toJson(message, smNotify.class);
        server.sendAll(json);
        String text = game.getCurrentPlayer().getNickName() + " will choose which students to move to islands";
        smCurrentPlayer message2 = new smCurrentPlayer(text, game.getCurrentPlayer().getNickName());
        server.sendAll(gson.toJson(message2, smCurrentPlayer.class));
    }

    /**
     * move the specified students from current player's hall to specified island.
     * called by client message StudentsMovementsHToI
     * Notify to other players the new students on the island.
     * Notify other players of the students removed from current player's hall
     * @param island island to which move students
     * @param students students to move
     */
    public void moveStudentsHtoI(Integer island, ArrayList<StudentColor> students){
        ServerMessage message;
        board.getIsland(island).addStudents(students);
        game.getCurrentPlayer().getBoard().removeStudentsFromHall(students);
        message = new smStudentsOnIsland(
                "Students on island "+ island+" have been updated",
                island,
                students);
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smStudentsOnIsland.class));
        String text = "Students in the hall of " + game.getCurrentPlayer().getNickName() + " have been updated";
        message= new smStudentsInHall(
                text,
                students,
                false);
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smStudentsInHall.class));
        message = new smResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smResumeTurn.class));
    }

    /**
     * notify other players if the current players doesn't move any students from their hall to dining hall
     */
    public void notifyNothingToDiningHall(){
        String text= game.getCurrentPlayer().getNickName() +" will not move any students to their dining hall because they already move 3 students from their hall to islands";
        smNotify message = new smNotify(text);
        server.sendAllExceptPlayer(game.getCurrentPlayer().getNickName(), gson.toJson(message, smNotify.class));
        server.sendMessage(game.getCurrentPlayer().getNickName(), gson.toJson(new smResumeTurn(), smResumeTurn.class));
    }

    /**
     * notify other players if the current players doesn't move any students from their hall to islands
     */
    public void notifyNothingToIslands(){
        String text= game.getCurrentPlayer().getNickName() +" chosen to not move students to islands";
        smNotify message = new smNotify(text);
        server.sendAllExceptPlayer(game.getCurrentPlayer().getNickName(), gson.toJson(message, smNotify.class));
        server.sendMessage(game.getCurrentPlayer().getNickName(), gson.toJson(new smResumeTurn(), smResumeTurn.class));
    }

    /**
     * move specified students from current player's hall to dining hall
     * called by client message StudentsMovementsHToD
     * Notify to other players the students added to current player's dining hall
     * Notify to other players the students removed from current player's hall
     * if mode is "expert" notify all student of the coins earned by the current player
     * @param students students to move
     */
    public void moveStudentsHToD(ArrayList<StudentColor> students, boolean normal){
        String nickName = game.getCurrentPlayer().getNickName();
        String text;
        ServerMessage message;
        game.getCurrentPlayer().getBoard().addStudentsToDiningHall(students);
        game.getCurrentPlayer().getBoard().removeStudentsFromHall(students);
        text="Students in the dining hall of " + nickName + " have been updated";
        message= new smStudentsInDiningHall(
                text,
                nickName,
                students,
                true);
        server.sendAllExceptPlayer(
                nickName,
                gson.toJson(message, smStudentsInDiningHall.class));
        text="Students in " + nickName + " hall have been updated";
        message= new smStudentsInHall(
                text,
                students,
                false);
        server.sendAllExceptPlayer(
                nickName,
                gson.toJson(message, smStudentsInHall.class));
        if(game.isExpertMode()){
            if(game.getCurrentPlayer().getBoard().assignCoins()){
                text = nickName + " coins have been increased";
                message = new smCoins(
                        text,
                        //nickName,
                        game.getCurrentPlayer().getBoard().getCoins());
                server.sendAll(gson.toJson(message, smCoins.class));
            }
        }
        assignTeachers(normal);
    }

    /**
     * assign teacher roles to different players
     * depending on the number of students on each player's hall
     * notify all players
     */
    public void assignTeachers(boolean normal){
        String s="The new teachers are the following:\n";
        HashMap<StudentColor,String> roles= new HashMap<>();
        for(StudentColor color : StudentColor.values()){
            getTeacher(color);
        }
        for(StudentColor color : StudentColor.values()) {
            Player previousTeacher = getTeacher(color);
            Player teacher = getTeacher(color);
            double maxStudents;
            if(teacher!=null) {
                maxStudents = teacher.getBoard().countStudentColorInDH(color);
            }
            else
                maxStudents=0;
            for(Player player : game.getPlayers()) {
                int playerStudents = player.getBoard().countStudentColorInDH(color);
                if(maxStudents<playerStudents){
                    teacher=player;
                    maxStudents=playerStudents;
                }
                else if (playerStudents == maxStudents) {
                    if (game.isCurrentPlayer(player.getNickName())) {
                        if (game.isExpertMode()) {
                            if (player.hasBonusToPromotion()) {
                                teacher=player;
                                maxStudents=maxStudents+0.5;
                            }
                        }
                    }
                }
            }
            if(teacher!=null){
                if(previousTeacher==null)
                    teacher.addRole(color);
                else if(!previousTeacher.equals(teacher)){
                    previousTeacher.removeRole(color);
                    teacher.addRole(color);
                }
                roles.put(color, teacher.getNickName());
                s=s + "the " + color.toString().toLowerCase()+" teacher is " + teacher.getNickName()+"\n";
            }
        }
        game.getCurrentPlayer().setBonusToPromotion(false);
        smTeacherAssignment message=new smTeacherAssignment(s, roles);
        String json= gson.toJson(message, smTeacherAssignment.class);
        server.sendAll(json);
        if(normal) {
            String nick = game.getCurrentPlayer().getNickName();
            String text = nick + " will chose movements of Mother Nature";
            smCurrentPlayer message2 = new smCurrentPlayer(
                    text,
                    nick
            );
            server.sendMessage(
                    game.getCurrentPlayer().getNickName(),
                    gson.toJson(message2, smCurrentPlayer.class));
        }
    }


    /**
     * return the teacher of the specified color
     * @param color color of the teacher
     * @return the player who is the teacher of the specified color.
     */
    private Player getTeacher(StudentColor color){
        for(Player player : game.getPlayers()){
            if(player.getRoles().contains(color)) {
                //System.out.println(color + " : "+player.getNickName());
                return player;
            }
        }
        return null;
    }

    /**
     * changes mother nature position and calls method to calculate majority on the island reached by mother nature
     * called by client message MoveMother
     * notify all player except current player of the new mother nature's position
     * if only three island remain after merge or all towers of a certain color are placed
     * players will be notified that the game is ended and winners will be calculated
     * @param movements number of movements
     */
    public void moveMotherNature(int movements){
        board.moveMotherNature(movements);
        String nick = game.getCurrentPlayer().getNickName();
        int currentIsland = board.getMotherNature().getCurrentIsland() + 1;
        String text  = "Mother Nature is now visiting island " + currentIsland;
        smMotherPosition message = new smMotherPosition(text, currentIsland -1 );
        server.sendAll( gson.toJson(message, smMotherPosition.class));
        try {
            board.MajorityOnCurrentIsland(server, game.getCurrentPlayer());
        } catch (EndGameException e) {
            smNotify message2 = new smNotify("Game will end now because " + e.getMessage());
            server.sendAll(gson.toJson(gson.toJson(message2, smNotify.class)));
            gameResults();
            return;
        }
        text = game.getCurrentPlayer().getNickName() + " will chose a cloud";
        smCurrentPlayer message2 = new smCurrentPlayer(
                text,
                nick
        );
        server.sendAll(gson.toJson(message2, smCurrentPlayer.class));
    }

    /**
     * sets the cloud chosen by the current player
     * and notify all players except current
     * called by client message Cloud
     * @param cloud cloud chosen
     */
    public void setChosenCloud(int cloud){
        ServerMessage message;
        String text;
        String nick = game.getCurrentPlayer().getNickName();
        ArrayList<StudentColor> students = board.takeStudentsFromCloud(cloud);
        game.getCurrentPlayer().getBoard().addStudentsToHall(students);
        System.out.println("students in hall");
        text = nick + " has chosen cloud " + (cloud + 1);
        message = new smChosenCloud(text, cloud);
        server.sendAllExceptPlayer(nick, gson.toJson(message, smChosenCloud.class));
        message= new smStudentsInHall(
                text,
                students,
                true);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smStudentsInHall.class));
        if(!game.lastPlayerOfRound()){
            game.nextPlayer();
            nick=game.getCurrentPlayer().getNickName();
            smCurrentPlayer message3 = new smCurrentPlayer(
                    nick + " will chose which students to move to islands",
                    nick);
            server.sendAll(gson.toJson(message3, smCurrentPlayer.class));
        }
        else{
            if(game.lastStudentDrawn() || game.lastSupportCardUsed()){
                smNotify message3 = new smNotify(
                        "The game has ended because this was the last round"
                );
                server.sendAll(gson.toJson(message3, smNotify.class));
            }
            else{
                newRound();
            }
        }
    }

    /**
     * if this is the last round this method will notify all players, and
     * it will call gameResults.
     * Otherwise, it will call refill Clouds to start a new round
     */
    public void newRound(){
        if(game.lastStudentDrawn()||game.lastSupportCardUsed()){
            smNotify message = new smNotify("Game has ended. Please wait for game results");
            String json= gson.toJson(message, smNotify.class);
            server.sendAll(json);
            gameResults();
        }
        else {
            //game.nextPlayer();
            for (Player player : game.getPlayers())
                player.clearUsedSupportCard();
            refillClouds();
        }

    }

    /**
     * this method will calculate winners and losers
     */
    private void gameResults(){
        ArrayList<String> winners=new ArrayList<>();
        ArrayList<String> losers=new ArrayList<>();
        Tower winnerTower=board.getTowers().get(0);
        boolean tie=true;
        for(int i=1; i<2+game.getNumOfPlayers()%2; i++){
            if(winnerTower.getAvailableTowers()!=board.getTowers().get(i).getAvailableTowers()){
                tie=false;
                if (winnerTower.getAvailableTowers()>board.getTowers().get(i).getAvailableTowers())
                    winnerTower = board.getTowers().get(i);
            }
        }
        if(tie){
            winnerTower= towerWithMoreTeachers();
        }
        for(Player player : game.getPlayers()){
            if(player.getTower().equals(winnerTower))
                winners.add(player.getNickName());
            else
                losers.add(player.getNickName());
        }
        String s= "Game results";
        smResults message = new smResults(s,winners,losers);
        String json=gson.toJson(message, smResults.class);
        server.sendAll(json);
    }

    /**
     * return the tower with the most teachers connected. if the num of players is 4
     * the num of teachers connected to a tower is the sum
     * of the number of teachers connected to players that shares that tower
     * @return the tower with more teachers
     */
    public Tower towerWithMoreTeachers(){
        HashMap<Integer,Tower> teachersPerTower=new HashMap<>();
        for(Tower tower : board.getTowers()) {
            int num=0;
            for (Player player: game.getPlayers()){
                if(player.getTower().equals(tower))
                    num=num+player.getRoles().size();
            }
            teachersPerTower.put(num,tower);
        }
        int max=0;
        for(Integer i: teachersPerTower.keySet()){
            if(i>max)
                max=i;
        }
        return teachersPerTower.get(max);
    }

    /**
     * refills the character card specified and notify all players
     * @param card id of the card to refill
     * @param studentsToRefill number of students to refill
     */
    public void refillCard(int card, int studentsToRefill){
        String text;
        if(!game.lastStudentDrawn()){
            ArrayList<StudentColor> students;
            try {
                 students = board.getBag().draw(studentsToRefill);
            } catch (LastStudentDrawnException e) {
                game.setLastStudentDrawn(true);
                String text2 = "This will be the last round because the last student has been drawn";
                smNotify message = new smNotify(text2);
                server.sendAll(gson.toJson(message, smNotify.class));
                gameResults();
                return;
            }
            if(students.size()<studentsToRefill){
                text = "Only " + students.size() + " students have been refilled on character card " + card;
            }
            else{
                text = "Students on character card " + card + " have been refilled";
            }
            smStudentsOnCard message = new smStudentsOnCard(
                    text,
                    card,
                    students,
                    true);
            server.sendAll(gson.toJson(message, smStudentsOnCard.class));
        }
        else{
            text = "Character card " + card + " was not refilled because the are no more students";
            smNotify message = new smNotify(text);
            server.sendAll(gson.toJson(message, smNotify.class));
        }
    }

    /**
     * sets bonusToPromotion as true for the current player for this turn
     * called by me client message CC2 if card id is 2
     */
    public void setBonusToPromotion(){
        game.getCurrentPlayer().setBonusToPromotion(true);
        //resumeTurn();
    }

    /**
     * sets additionalInfluencePoints as true for the current player for this turn
     * called by me client message CC2 if card id is 8
     */
    public void setAdditionalInfluencePoints(){
        game.getCurrentPlayer().setAdditionalInfluencePoints(true);
        //resumeTurn();
    }

    /**
     * place a block on the specified island
     * called by client message CCG5
     * @param island index of the island
     */
    public void setBlockOnIsland(int island){
        ServerMessage message;
        board.getIsland(island).addBlockCard();
        String text1= game.getCurrentPlayer().getNickName() + " has placed a block on island number " + island;
        message= new smBlockOnIsland(
                text1,
                island,
                true);
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smBlockOnIsland.class));
        message = new smBlockOnCard(true);
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smBlockOnCard.class));
    }

    /**
     * remove 3 students of the specified color from all players' dining hall and put them back in the bag
     * if lastStudentDrawn is set to true it will be set to false
     * @param color color of the students to remove
     */
    public void removeStudents(StudentColor color){
        ArrayList<StudentColor> students=new ArrayList<>();
        ServerMessage message;
        String text;
        for(Player player : game.getPlayers()){
            students.clear();
            int studentRemoved= player.getBoard().removeStudents(color);
            for(int i=0; i<studentRemoved; i++){
                students.add(color);
            }
            if(students.size()>0) {
                board.getBag().addStudents(students);
                if (game.lastStudentDrawn()) {
                    game.setLastStudentDrawn(false);
                    message = new smNotify("This is no more the last round because some students have been added to the bag");
                    server.sendAll(gson.toJson(message, smNotify.class));

                }
            }
            text = studentRemoved + " " +color.toString().toLowerCase();
            if(studentRemoved==1)
                text = text + " student has";
            else
                text = text + " students have";
            text = text + " been removed form the hall of " + player.getNickName();
            message = new smStudentsInHall(
                    text,
                    player.getNickName(),
                    students,
                    false);
            server.sendAll(gson.toJson(message, smStudentsInHall.class));
        }
    }

    /**
     * sets the specified color as ignoredColor for this turn
     * @param color color tio set as ignored
     */
    public void ignoreColor(StudentColor color){
        board.addIgnoredColor(color);
        String text = "For this turn " + color.toString().toLowerCase() + " students will not be counted in the assigment of influence points";
        smNotify message = new smNotify(text);
        server.sendAll(gson.toJson(message, smNotify.class));
        //resumeTurn();
    }

    /**
     * to move mother nature as effect of character card 4
     * @param movements mother nature's movements
     */
    public void additionalMotherMovements(int movements){
        board.moveMotherNature(movements);
        int currentIsland = board.getMotherNature().getCurrentIsland() + 1;
        String text  = "Mother Nature has been moved to island " + currentIsland;
        smMotherPosition message = new smMotherPosition(text, currentIsland -1 );
        server.sendAllExceptPlayer(game.getCurrentPlayer().getNickName(), gson.toJson(message, smMotherPosition.class));
    }

    /**
     * calculate majority as effect of character card 3
     * @param island on which island calculate the majority
     */
    public  void additionalMajority(int island){
        try {
            board.MajorityOnIsland(island, server, game.getCurrentPlayer());
        } catch (EndGameException e) {
            smNotify message = new smNotify("Game will end now because " + e.getMessage());
            server.sendAll(gson.toJson(message, smNotify.class));
            gameResults();
            return;
        }
    }

    /**
     * moves specified students form current player's dining hall to hall
     * and notify all players except current
     * This method is used to implement effect of character card 10
     * @param students students to move
     */
    public void moveStudentsDToH(ArrayList<StudentColor> students){
        Player player = game.getCurrentPlayer();
        String text;
        ServerMessage message;
        player.getBoard().addStudentsToHall(students);
        player.getBoard().removeStudentsFromD(students);
        text="Students in the dining hall "+player.getNickName()+" have been updated";
        message= new smStudentsInDiningHall(
                text,
                player.getNickName(),
                students,
                false);
        server.sendAllExceptPlayer(
                player.getNickName(),
                gson.toJson(message, smStudentsInDiningHall.class));
        text="Students in the hall of "+player.getNickName()+" have been updated";
        message= new smStudentsInHall(
                text,
                students,
                true);
        server.sendAllExceptPlayer(
                player.getNickName(),
                gson.toJson(message, smStudentsInHall.class));
    }

    /**
     * notify players except current of the character card used by the current players.
     * It also notifies if the price is increased
     * @param card id of the used card
     */
    public void notifyUsedCharacterCard( int card){
        String text = game.getCurrentPlayer().getNickName() + " has used character card " + card + "\n";
        boolean increasedPrice=characterCardBoard.getCharacterCard(card).increasePrice();
        if(increasedPrice){
            text = text + " The price of the card has been increased by one";
        }
        smUsedCharacterCard message = new smUsedCharacterCard(
                text,
                card,
                increasedPrice,
                characterCardBoard.getCharacterCard(card).getPrice());
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smUsedCharacterCard.class));
    }

    /**
     * this method is used to notify the current player so that they resume their turn
     */
    public void resumeTurn(){
        ServerMessage message = new smResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smResumeTurn.class));
    }

    public void setIgnoreTower(int island){
        board.getIsland(island).setIgnoreTower(true);
        String text = "For this turn towers will not give influence points";
        ServerMessage message = new smNotify(text);
        server.sendAll(
                gson.toJson(message, smNotify.class)
        );
    }

    /**
     * this method is used to remove a student from character card 1 and put it on a specified cloud
     * @param island to which the student will be moved
     * @param student student to move
     */
    public void addStudentToIsland(int island, StudentColor student){
        ArrayList<StudentColor> students = new ArrayList<>();
        students.add(student);
        String nick = game.getCurrentPlayer().getNickName();
        String text;
        ServerMessage message;
        board.getIsland(island).addStudent(student);
        text = "One " + student + " student has be4en taken from character card 1";
        message= new smStudentsOnCard(text,
                1,
                students,
                false);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smStudentsOnCard.class));
        text = "One " + student +" student has been added to island "+ (island+1);
        message = new smStudentsOnIsland(text,island,students);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smStudentsOnIsland.class)
        );
    }

    /**
     * this method is used to put remove a student from characterCard 11 and put it in current player's dining hall and re
     * @param student student to move
     */
    public void addStudentToD(StudentColor student){
        ArrayList<StudentColor> students = new ArrayList<>();
        students.add(student);
        String nick = game.getCurrentPlayer().getNickName();
        String text;
        ServerMessage message;
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(student);
        text = "One " + student.toString().toLowerCase() + " student has been taken from character card 11";
        message= new smStudentsOnCard(text,
                1,
                students,
                false);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smStudentsOnCard.class));
        text = "One " + student.toString().toLowerCase() +" student has been added to the dining hall of " + nick;
        message = new smStudentsInDiningHall(text,nick,students,true);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smStudentsInDiningHall.class)
        );
    }

    /**
     * this method is used to switch students between the current player's hall and character card 7
     * @param studentToH student to move to hall form character card 7
     * @param studentToCC7 students to move to character card 7 form hall
     */
    public void switchCC7(ArrayList<StudentColor> studentToH, ArrayList<StudentColor> studentToCC7){
        Player currentPlayer = game.getCurrentPlayer();
        String text;
        ServerMessage message;
        game.getCurrentPlayer().getBoard().addStudentsToHall(studentToH);
        message = new smStudentsOnCard(
                7,
                studentToH,
                false);
        server.sendAllExceptPlayer(
                currentPlayer.getNickName(),
                gson.toJson(message, smStudentsOnCard.class)
        );
        message = new smStudentsInHall(
                studentToH,
                true);
        server.sendAllExceptPlayer(
                currentPlayer.getNickName(),
                gson.toJson(message, smStudentsInHall.class)
        );
        game.getCurrentPlayer().getBoard().removeStudentsFromHall(studentToCC7);
        text = "Students on character card 7 have been updated";
        message = new smStudentsOnCard(
                text,
                7,
                studentToH,
                true);
        server.sendAllExceptPlayer(
                currentPlayer.getNickName(),
                gson.toJson(message, smStudentsOnCard.class)
        );
        text = "Students in the hall of " + currentPlayer.getNickName() + " have been updated";
        message = new smStudentsInHall(text,
                studentToH,
                false);
        server.sendAllExceptPlayer(
                currentPlayer.getNickName(),
                gson.toJson(message, smStudentsInHall.class)
        );
    }
}

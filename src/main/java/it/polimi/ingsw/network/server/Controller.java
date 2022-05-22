package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.Exceptions.LastSupportCardUsedException;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.server.model.*;

import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCardBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    Gson gson;
    Game game;
    GameBoard board;
    CharacterCardBoard characterCardBoard;
    Server server;

    public Controller(Server server){
        gson= new Gson();
        game= new Game();
        this.server=server;
        characterCardBoard=new CharacterCardBoard();
    }

    public void addPlayerToGame(String nick){
        game.getPlayers().add(new Player(nick));
    }

    public void setGameStatus(String mode, int numOfPlayers){
        game.setMode(mode);
        game.setNumOfPlayers(numOfPlayers);
        board=new GameBoard(numOfPlayers);
        board.setPlayers(game.getPlayers());
    }

    public void setTower( String tower){
        String nick;
        nick = game.getCurrentPlayer().getNickName();
        game.getCurrentPlayer().setTower(board.getTower(tower));
        String text="Player " + nick + " has chosen the "+ tower+ " tower.";
        for(Player player : game.getPlayers()){
            if(game.getCurrentPlayer().getTower().equals(player.getTower()))
                text = text + "\nThey will be in team with " + player.getNickName();
        }
        smNotifyChosenTower message= new smNotifyChosenTower(
                text,
                tower,
                nick);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smNotifyChosenTower.class));
        game.nextPlayer();
        nick = game.getCurrentPlayer().getNickName();
        if(game.firstPlayerOfRound()){
            text = nick + "will chose their deck";
        }
        else{
            text = nick + "will chose their tower";
        }
        smNotifyCurrentPlayer message2 = new smNotifyCurrentPlayer(
                text,
                nick
        );
        server.sendAll(gson.toJson(message2,smNotifyCurrentPlayer.class));
    }

    public void setDeck(String deck){
        String nick = game.getCurrentPlayer().getNickName();
        String text = nick + " chosen the " + deck + " deck";
        smNotifyUsedDeck message = new smNotifyUsedDeck(
                text,
                deck
        );
        game.nextPlayer();
        nick = game.getCurrentPlayer().getNickName();
        if(game.firstPlayerOfRound()){
            text = nick + " will chose their support card";
            notifyStudentsOnCloud();
        }
        else {
            text = nick + " will chose their deck";
        }
        smNotifyCurrentPlayer message2 = new smNotifyCurrentPlayer(
                text,
                nick
        );
        server.sendAll(gson.toJson(message2,smNotifyCurrentPlayer.class));
    }

    private void notifyStudentsOnCloud(){
        String text;
        smNotifyStudentsOnCloud message;
        int num;
        for(Cloud cloud : board.getClouds()){
            num = board.getCloudNumber(cloud) +1;
            text = "Student on cloud " + num + " have been drawn";
            message = new smNotifyStudentsOnCloud(
                    text,
                    num-1,
                    cloud.getStudents()
            );
            server.sendAll(gson.toJson(message,smNotifyCloud.class));
        }
    }

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
                    game.setLastStudentDrawn();
                    smNotify message = new smNotify("This will be the last round because the last student has been drawn while refilling clouds");
                    server.sendAll(gson.toJson(message, smNotify.class));
                }
                int num = board.getCloudNumber(cloud);
                studentsRefilled = cloud.getStudents().size();
                if(studentsToRefill==studentsRefilled)
                    text = "cloud number " + num + " has been refilled";
                else
                    text = "Only " + studentsRefilled + " students have been placed on cloud " + num + "because there are no more available students to be drawn";
                smNotifyStudentsOnCloud message = new smNotifyStudentsOnCloud(
                        text,
                        num - 1,
                        cloud.getStudents());
                server.sendAll(gson.toJson(message, smNotifyStudentsOnCloud.class));
            }
            else{
                text = "cloud " + board.getCloudNumber(cloud) + " has not been refilled because there are no more available students to be drawn";
                smNotify message = new smNotify(text);
                server.sendAll(gson.toJson(message, smNotify.class));
            }
        }
        game.nextPlayer();
        String nick = game.getCurrentPlayer().getNickName();
        smNotifyCurrentPlayer message = new smNotifyCurrentPlayer(
                nick + " will chose a support card",
                nick
        );
        server.sendAll(gson.toJson(message, smNotifyCurrentPlayer.class));
    }

    public void setUsedSupportCard(int cardId){
        ServerMessage message;
        boolean additional = false;
        boolean valid=true;
        String text;
        ArrayList<Integer> usableSupportCards = new ArrayList<>();
        if(getUsedSupportCards().contains(cardId)){
            for(SupportCard card : game.getCurrentPlayer().getDeck()) {
                if (!getUsedSupportCards().contains(card.getId())) {
                    valid = false;
                    usableSupportCards.add(card.getId());
                }
            }
            if(valid) {
                game.getCurrentPlayer().getCardById(cardId).setAdditionalTurnOrder();
                text="All your available support cards have been used by other players in this round./n"+"" +
                        "You will use the chosen support card but you will play after the player that have already chosen the same one";
                additional = true;
            }
            else {
                text="Your chosen support card has already been used by another player in this round"+
                        "Please choose another support card";
                message= new smNotValidSupportCard(text);
                String json = gson.toJson(message,smNotValidSupportCard.class);
                server.sendMessage(game.getCurrentPlayer().getNickName(),json);
                return;
            }
        }
        else {
            text = "Player " + game.getCurrentPlayer().getNickName() + " used support card number " + cardId;
        }
        message=new smNotifyUsedSupportCard(
                text,
                game.getCurrentPlayer().getNickName(),
                cardId,
                additional);
        server.sendAll(gson.toJson(message, smNotifyUsedSupportCard.class));
        try {
            game.getCurrentPlayer().setUsedSupportCard(cardId);
        } catch (LastSupportCardUsedException e) {
            game.setLastSupportCardUsed();
            text = "This will be the last round because " + game.getCurrentPlayer().getNickName() +
                    "has used their last support card";
            message= new smNotify(text);
            String json= gson.toJson(message,smNotify.class);
            server.sendAll(json);
        }

        if(game.lastPlayerOfRound())
            roundOrder();
        else {
            game.nextPlayer();
            String text2 = game.getCurrentPlayer().getNickName() + " will chose their support card";
            smNotifyCurrentPlayer message2 = new smNotifyCurrentPlayer(text2, game.getCurrentPlayer().getNickName());
            server.sendAll(gson.toJson(message2, smNotifyCurrentPlayer.class));
        }
    }

    private ArrayList<Integer> getUsedSupportCards(){
        ArrayList<Integer> supportCards= new ArrayList<>();
        for(Player player : game.getPlayers()){
            if(!player.equals(game.getCurrentPlayer()))
                if(player.getUsedSupportCard()!=null)
                    supportCards.add(player.getUsedSupportCard().getId());
        }
        return supportCards;
    }

    private void roundOrder(){
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
        game.setCurrentPlayer(game.getPlayers().get(0));
        for(Player player : roundOrder){
            nicks.add(player.getNickName());
        }
        String s="The players order for the current action phase and the next planning phase will be the following"+
                nicks;
        smNotifyPlayerOrder message = new smNotifyPlayerOrder(s,nicks);
        String json= gson.toJson(message,smNotifyPlayerOrder.class);
        server.sendAll(json);
        String text = game.getCurrentPlayer().getNickName() + " will chose which students to move to their dining hall";
        smNotifyCurrentPlayer message2 = new smNotifyCurrentPlayer(text, game.getCurrentPlayer().getNickName());
        server.sendAll(gson.toJson(message2, smNotifyCurrentPlayer.class));
        smNotifyResumeTurn message3 = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message3, smNotifyResumeTurn.class));
    }

    public void moveStudentsHtoI(Integer island, ArrayList<StudentColor> students){
        ServerMessage message;
        board.getIsland(island).addStudents(students);
        game.getCurrentPlayer().getBoard().removeStudentsFromHall(students);
        message = new smNotifyStudentsOnIsland(
                "Students on island "+ island+" have been updated",
                island,
                board.getIsland(island).getStudents());
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message,smNotifyStudentsOnIsland.class));
        message = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smNotifyResumeTurn.class));
    }

    public void moveStudentsHToD(ArrayList<StudentColor> students){
        String nickName = game.getCurrentPlayer().getNickName();
        game.getCurrentPlayer().getBoard().addStudentsToDiningHall(students);
        game.getCurrentPlayer().getBoard().removeStudentsFromHall(students);
        String s="Students in " + nickName + "'s dining hall have been updated";
        smNotifyStudentsInDiningHall message= new smNotifyStudentsInDiningHall(
                s,
                nickName,
                students,
                true);
        server.sendAllExceptPlayer(
                nickName,
                gson.toJson(message, smNotifyStudentsInDiningHall.class));
        String s2="Students in " + nickName + "'s hall have been updated";
        smNotifyStudentsInHall message2= new smNotifyStudentsInHall(
                s2,
                nickName,
                students,
                false);
        server.sendAllExceptPlayer(
                nickName,
                gson.toJson(message2, smNotifyStudentsInHall.class));
        if(game.isExpertMode()){
            if(game.getCurrentPlayer().getBoard().assignCoins()){
                String text = nickName + "'s coins have been increased";
                smNotifyCoins message3 = new smNotifyCoins(
                        text,
                        nickName,
                        game.getCurrentPlayer().getBoard().getCoins());
                server.sendAll(gson.toJson(message3,smNotifyCoins.class));
            }
        }
        assignTeachers();
    }

    private void assignTeachers(){
        String s="The new teachers are the following:\n";
        HashMap<StudentColor,String> roles= new HashMap<>();
        for(StudentColor color : StudentColor.values()) {
            Player previousTeacher = getTeacher(color);
            Player teacher = getTeacher(color);
            int maxStudents;
            if(teacher!=null)
                maxStudents = teacher.getBoard().countStudentColorInDH(color);
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
                                maxStudents=maxStudents+1;
                            }
                        }
                    }
                }
            }
            if(previousTeacher.equals(null))
                teacher.addRole(color);
            else if(!previousTeacher.equals(teacher)){
                previousTeacher.removeRole(color);
                teacher.addRole(color);
            }
            roles.put(color, teacher.getNickName());
            s=s+color+" teacher is: "+teacher.getNickName()+"\n";
        }
        game.getCurrentPlayer().setBonusToPromotion(false);
        smNotifyTeacherAssignment message=new smNotifyTeacherAssignment(s, roles);
        String json= gson.toJson(message,smNotifyTeacherAssignment.class);
        server.sendAll(json);
        String nick = game.getCurrentPlayer().getNickName();
        String text = nick + " will chose Mother Nature's movements";
        smNotifyCurrentPlayer message2 = new smNotifyCurrentPlayer(
                text,
                nick
        );
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message2, smNotifyCurrentPlayer.class));
    }

    private Player getTeacher(StudentColor color){
        Player teacher = null;
        for(Player player : game.getPlayers()){
            if(player.getRoles().contains(color))
                teacher=player;
        }
        return teacher;
    }

    public void moveMotherNature(int movements){
        board.moveMotherNature(movements);
        String nick = game.getCurrentPlayer().getNickName();
        int currentIsland = board.getMotherNature().getCurrentIsland() + 1;
        String text  = "Mother Nature is now visiting island " + currentIsland;
        smNotifyMotherPosition message = new smNotifyMotherPosition(text, currentIsland -1 );
        server.sendAllExceptPlayer( nick, gson.toJson(message, smNotifyMotherPosition.class));
        try {
            board.MajorityOnCurrentIsland(server);
        } catch (EndGameException e) {
            smNotify message2 = new smNotify("Game will end now because " + e.getMessage());
            server.sendAll(gson.toJson(gson.toJson(message2, smNotify.class)));
            gameResults();
            return;
        }
        text = game.getCurrentPlayer().getNickName() + " will chose a cloud";
        smNotifyCurrentPlayer message2 = new smNotifyCurrentPlayer(
                text,
                nick
        );
        server.sendAll(gson.toJson(gson.toJson(message2, smNotifyCurrentPlayer.class)));
    }

    public void setChosenCloud(int cloud){
        ServerMessage message;
        String nick = game.getCurrentPlayer().getNickName();
        String text = "Students in " + nick + "'s hall have been updated";
        ArrayList<StudentColor> students = board.takeStudents(cloud);
        game.getCurrentPlayer().getBoard().addStudentsToHall(students);
        text = nick + " has chosen cloud " + (cloud + 1);
        message = new smNotifyCloud(text, cloud);
        server.sendAllExceptPlayer(nick, gson.toJson(message, smNotifyCloud.class));
        message= new smNotifyStudentsInHall(
                text,
                nick,
                students,
                false);
        server.sendAllExceptPlayer(
                nick,
                gson.toJson(message, smNotifyStudentsInHall.class));
        if(!game.lastPlayerOfRound()){
            game.nextPlayer();
            smNotifyCurrentPlayer message3 = new smNotifyCurrentPlayer(
                    nick + " will chose a cloud",
                    nick);
            server.sendAll(gson.toJson(message3,smNotifyCurrentPlayer.class));
        }
        else{
            if(game.lastStudentDrawn() || game.lastSupportCardUsed()){
                smNotify message3 = new smNotify(
                        "The game has ended because this was the last round"
                );
                server.sendAll(gson.toJson(message3, smNotify.class));
            }
            else{
                refillClouds();
            }
        }
    }

    private void notifyRoundEnd(){
        if(game.lastStudentDrawn()||game.lastSupportCardUsed()){
            smNotify message = new smNotify("Game has ended. Please wait for game results");
            String json= gson.toJson(message,smNotify.class);
            server.sendAll(json);
            gameResults();
        }
        else {
            game.nextPlayer();
            for (Player player : game.getPlayers())
                player.clearUsedSupportCard();
            refillClouds();
        }

    }

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
        smNotifyResults message = new smNotifyResults(s,winners,losers);
        String json=gson.toJson(message,smNotifyResults.class);
        server.sendAll(json);
    }

    private Tower towerWithMoreTeachers(){
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

    public void refillCard(int card, int studentsToRefill){
        String text;
        if(!game.lastStudentDrawn()){
            ArrayList<StudentColor> students;
            try {
                 students = board.getBag().draw(studentsToRefill);
            } catch (LastStudentDrawnException e) {
                String text2 = "This will be the last round because the last student has been drawn";
                smNotify message = new smNotify(text2);
                server.sendAll(gson.toJson(message, smNotify.class));
                gameResults();
                return;
            }
            if(students.size()<studentsToRefill){
                text = "Only " + students.size() + " students have been refilled on card " + card;
            }
            else{
                text = "Students on card " + card + " have been refilled";
            }
            smNotifyStudentsOnCard message = new smNotifyStudentsOnCard(
                    text,
                    card,
                    students);
            server.sendAll(gson.toJson(message,smNotifyStudentsOnCard.class));
        }
        else{
            text = "Card " + card + " was not refilled because the are no more students";
            smNotify message = new smNotify(text);
            server.sendAll(gson.toJson(message, smNotify.class));
        }
        smNotifyResumeTurn message = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smNotifyResumeTurn.class));

    }

    public void setBonusToPromotion(){
        game.getCurrentPlayer().setBonusToPromotion(true);
        smNotifyResumeTurn message = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smNotifyResumeTurn.class));
    }

    public void setAdditionalInfluencePoints(){
        game.getCurrentPlayer().setAdditionalInfluencePoints(true);
        smNotifyResumeTurn message = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smNotifyResumeTurn.class));
    }

    public void setBlockOnIsland(int island){
        ServerMessage message;
        board.getIsland(island).addBlockCard();
        String text1= game.getCurrentPlayer().getNickName() + " has placed a block on island number " + island;
        message= new smNotifyBlockOnIsland(
                text1,
                island,
                true);
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message,smNotifyBlockOnIsland.class));
        message = new smNotifyBlocksOnCard(true);
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message,smNotifyBlocksOnCard.class));
        message = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message, smNotifyResumeTurn.class));
    }

    public void removeStudents(StudentColor color){
        ArrayList<StudentColor> students=new ArrayList<>();
        String s= "RED";
        students.add(StudentColor.valueOf(s));
        for(Player player : game.getPlayers()){
            students.clear();
            int studentRemoved= player.getBoard().removeStudents(color);
            for(int i=0; i<studentRemoved; i++){
                students.add(color);
            }
            String text = studentRemoved + color.toString();
            if(studentRemoved==1)
                text = text + " has";
            else
                text = text + " have";
            text = text + " form " + player.getNickName() + "'s hall";
            smNotifyStudentsInHall message = new smNotifyStudentsInHall(
                    text,
                    player.getNickName(),
                    students,
                    false);
            server.sendAll(gson.toJson(message, smNotifyStudentsInHall.class));
        }
        smNotifyResumeTurn message2 = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message2, smNotifyResumeTurn.class));
    }

    public void ignoreColor(StudentColor color){
        board.addIgnoredColor(color);
        String text = "For this turn " + color.toString().toLowerCase() + " students will not be counted in the assigment of influence points";
        smNotifyIgnoreColor message = new smNotifyIgnoreColor("text",color, true);
        server.sendAll(gson.toJson(message, smNotifyIgnoreColor.class));
        smNotifyResumeTurn message2 = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message2, smNotifyResumeTurn.class));
    }

    public void additionalMotherMovements(int movements){
        board.moveMotherNature(movements);
        int currentIsland = board.getMotherNature().getCurrentIsland() + 1;
        String text  = "Mother Nature has been moved to island " + currentIsland;
        smNotifyMotherPosition message = new smNotifyMotherPosition(text, currentIsland -1 );
        server.sendAllExceptPlayer(game.getCurrentPlayer().getNickName(), gson.toJson(message, smNotifyMotherPosition.class));
        smNotifyResumeTurn message2 = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message2, smNotifyResumeTurn.class));
    }

    public  void additionalMajority(int movements){
        try {
            board.MajorityOnIsland(movements, server);
        } catch (EndGameException e) {
            smNotify message2 = new smNotify("Game will end now because " + e.getMessage());
            gameResults();
            return;
        }
        smNotifyResumeTurn message2 = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message2, smNotifyResumeTurn.class));
    }

    public void moveStudentsDToH(ArrayList<StudentColor> students){
        Player player = game.getCurrentPlayer();
        player.getBoard().addStudentsToHall(students);
        player.getBoard().removeStudentsFromD(students);
        String text="Students in "+player.getNickName()+"'s dining hall have been updated";
        smNotifyStudentsInDiningHall message= new smNotifyStudentsInDiningHall(
                text,
                player.getNickName(),
                students,
                false);
        server.sendAllExceptPlayer(
                player.getNickName(),
                gson.toJson(message, smNotifyStudentsInDiningHall.class));
        String text2="Students in "+player.getNickName()+"'s hall have been updated";
        smNotifyStudentsInHall message2= new smNotifyStudentsInHall(
                text2,
                player.getNickName(),
                students,
                true);
        server.sendAllExceptPlayer(
                player.getNickName(),
                gson.toJson(message2, smNotifyStudentsInHall.class));

    }

    public void notifyUsedCharacterCard(String text, int card){
        String t = game.getCurrentPlayer().getNickName() + text;
        boolean increasedPrice=characterCardBoard.getCharacterCard(card).increasePrice();
        if(increasedPrice){
            t = t + " The card's price has been increased by one";
        }
        smNotifyUsedCharacterCard message = new  smNotifyUsedCharacterCard (
                t,
                game.getCurrentPlayer().getNickName(),
                card,
                increasedPrice);
        server.sendAllExceptPlayer(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message,smNotifyUsedCharacterCard.class));
    }

    public void resumeTurn(){
        ServerMessage message = new smNotifyResumeTurn();
        server.sendMessage(
                game.getCurrentPlayer().getNickName(),
                gson.toJson(message,smNotifyResumeTurn.class));
    }

}

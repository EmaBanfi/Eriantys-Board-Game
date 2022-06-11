package it.polimi.ingsw.network.server;

import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.network.server.model.*;
import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;
    Game game;
    @BeforeEach
    void setUp() {
        controller = new Controller(new DummyServer());
        controller.addPlayerToGame("Pablo");
        controller.addPlayerToGame("player2");
        controller.addPlayerToGame("player3");
        controller.addPlayerToGame("player4");
        game = controller.getGame();
        game.setCurrentPlayer(controller.getGame().getPlayers().get(0));
    }

    @AfterEach
    void tearDown() {
        controller = null;
        game = null;
    }

    @Test
    void setGameStatus(){
        controller.setGameStatus("expert", 4);
        assertTrue(game.isExpertMode());
        assertEquals(2, controller.getBoard().getTowers().size());
        assertEquals(3, controller.getBoard().getClouds().get(0).getMaxStudents());
    }
    @Test
    void addPlayerToGame() {
        assertTrue(controller.alreadyPresent("Pablo"));
        assertTrue(controller.alreadyPresent("player2"));
        assertTrue(controller.alreadyPresent("player3"));
        assertTrue(controller.alreadyPresent("player4"));
    }

    @Test
    void setTower() {
        controller.setGameStatus("normal", 4);
        controller.setTower("white");
        assertEquals(game.getPlayers().get(0).getTower(), controller.getBoard().getTower("white"));
    }

    /**
     * test case in which player2 use the same card as Pablo and they can use other cards
     */
    @Test
    void setUsedSupportCardCase1() {
        System.out.println(game.getCurrentPlayer().getNickName());
        controller.setUsedSupportCard(2);
        System.out.println(game.getCurrentPlayer().getNickName());
        controller.setUsedSupportCard(2);
        assertEquals(2, game.getPlayers().get(0).getUsedSupportCard().getId());
        assertNull(game.getCurrentPlayer().getUsedSupportCard());
    }

    /**
     * test case in which all players use the same support card and they can't use other cards
     */
    @Test
    void setUsedSupportCardCase2(){
        for(Player player : game.getPlayers()){
            player.getDeck().clear();
            player.getDeck().add(new SupportCard(1,1,1));

        }
        for(int i=0; i<4; i++){
            controller.setUsedSupportCard(1);
        }
        for(int i=0; i<4; i++ ){
            assertEquals(1+(i*0.1),game.getPlayers().get(i).getUsedSupportCard().getTurnOrder());
        }
    }

    @Test
    void moveStudentsHtoI() {
        controller.setGameStatus("normal", 4);
        game.getCurrentPlayer().getBoard().getHall().removeStudents(game.getCurrentPlayer().getBoard().getHall().getStudents());
        ArrayList<StudentColor> students = new ArrayList<>();
        students.add(StudentColor.RED);
        game.getCurrentPlayer().getBoard().addStudentsToHall(students);
        controller.moveStudentsHtoI(0, students);
        assertTrue(controller.getBoard().getIsland(0).getStudents().contains(StudentColor.RED));
        assertFalse(game.getCurrentPlayer().getBoard().getHall().getStudents().contains(StudentColor.RED));
    }

    @Test
    void moveStudentsHToD() {
        controller.setGameStatus("expert", 4);
        game.getCurrentPlayer().getBoard().removeStudentsFromHall(game.getCurrentPlayer().getBoard().getHall().getStudents());
        ArrayList<StudentColor> students = new ArrayList<>();
        for(int i =1; i<=3; i++) {
            students.add(StudentColor.RED);
        }
        game.getCurrentPlayer().getBoard().addStudentsToHall(students);
        controller.moveStudentsHToD(students);
        assertFalse(game.getCurrentPlayer().getBoard().getHall().getStudents().contains(StudentColor.RED));
        assertTrue(game.getCurrentPlayer().getBoard().getDiningHall().getStudents().contains(StudentColor.RED));
        assertEquals(2, game.getCurrentPlayer().getBoard().getCoins());
    }

    @Test
    void setChosenCloud() {
        controller.setGameStatus("normal", 4);
        ArrayList<StudentColor> students = new ArrayList<>(controller.getBoard().getClouds().get(0).getStudents());
        controller.setChosenCloud(0);
        for(StudentColor color : students){
            assertTrue(game.getPlayers().get(0).getBoard().getHall().getStudents().contains(color));
        }
        assertTrue(controller.getBoard().getClouds().get(0).getStudents().isEmpty());
    }


    @Test
    void setBonusToPromotion() {
        controller.setGameStatus("expert", 4);
        controller.setBonusToPromotion();
        assertTrue(game.getCurrentPlayer().hasBonusToPromotion());
        controller.assignTeachers();
        assertFalse(game.getCurrentPlayer().hasBonusToPromotion());
    }

    @Test
    void setAdditionalInfluencePoints() {
        controller.setGameStatus("expert",4);
        controller.setTower("WHITE");
        controller.setTower("WHITE");
        controller.setTower("BLACK");
        controller.setTower("BLACK");
        controller.setAdditionalInfluencePoints();
        assertTrue(game.getCurrentPlayer().hasAdditionalInfluencePoints());
        controller.moveMotherNature(2);
        assertFalse(game.getCurrentPlayer().hasAdditionalInfluencePoints());
    }

    /**
     * test case in which there is a block on the island and moveMotherNature is called
     * the majority should not be calculated and the block should be removed
     */
    @Test
    void setBlockOnIslandCase1() {
        controller.setGameStatus("expert",4);
        controller.setBlockOnIsland(0);
        assertTrue(controller.getBoard().getIsland(0).isBlockCard());
        controller.getBoard().getMotherNature().setCurrentIsland(0);
        controller.moveMotherNature(0);
        assertFalse(controller.getBoard().getIsland(0).isBlockCard());
    }

    /**
     * test case in which there is a block on the island but it should be ignored because additionalMajority is called
     * the majority should be calculated and the block should remain
     */
    @Test
    void setBlockOnIslandCase2() {
        controller.setGameStatus("expert",4);
        controller.setTower("WHITE");
        controller.setTower("WHITE");
        controller.setTower("BLACK");
        controller.setTower("BLACK");
        controller.setBlockOnIsland(0);
        assertTrue(controller.getBoard().getIsland(0).isBlockCard());
        controller.getBoard().getMotherNature().setCurrentIsland(0);
        controller.additionalMajority(0);
        assertTrue(controller.getBoard().getIsland(0).isBlockCard());
    }

    @Test
    void removeStudents() {
        controller.setGameStatus("expert",4);
        game.setLastStudentDrawn(true);
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.nextPlayer();
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.nextPlayer();
        game.getCurrentPlayer().getBoard().getDiningHall().addStudent(StudentColor.RED);
        game.nextPlayer();
        controller.removeStudents(StudentColor.RED);
        assertEquals(1,game.getPlayers().get(0).getBoard().getDiningHall().getStudents().
                stream().filter(x -> x.equals(StudentColor.RED)).count());
        assertEquals(0,game.getPlayers().get(1).getBoard().getDiningHall().getStudents().
                stream().filter(x -> x.equals(StudentColor.RED)).count());
        assertEquals(0,game.getPlayers().get(2).getBoard().getDiningHall().getStudents().
                stream().filter(x -> x.equals(StudentColor.RED)).count());
        assertEquals(0,game.getPlayers().get(3).getBoard().getDiningHall().getStudents().
                stream().filter(x -> x.equals(StudentColor.RED)).count());
        assertFalse(game.lastStudentDrawn());
    }

    @Test
    void ignoreColor() {
        controller.setGameStatus("expert",4);
        controller.ignoreColor(StudentColor.RED);
        assertEquals(StudentColor.RED,controller.getBoard().getIgnoredColor());
    }

    @Test
    void additionalMotherMovements() {
        controller.setGameStatus("expert",4);
        controller.getBoard().getMotherNature().setCurrentIsland(0);
        controller.additionalMotherMovements(5);
        assertEquals(5,controller.getBoard().getMotherNature().getCurrentIsland());
    }

    @Test
    void moveStudentsDToH() {
        Player player = game.getCurrentPlayer();
        ArrayList<StudentColor> students = new ArrayList<>();
        students.add(StudentColor.RED);
        player.getBoard().addStudentsToDiningHall(students);
        controller.moveStudentsDToH(students);
        assertTrue(player.getBoard().getDiningHall().getStudents().isEmpty());
        assertTrue(player.getBoard().getHall().getStudents().contains(StudentColor.RED));
    }

    @Test
    void notifyUsedCharacterCard() {
        controller.setGameStatus("expert", 4);
        ArrayList<Integer> availableCards = new ArrayList<>();
        for(CharacterCard card : controller.getCharacterCardBoard().getAvailableCards()){
            availableCards.add(card.getCardId());
        }
        int cardId;
        Random random = new Random();
        cardId= availableCards.get(random.nextInt(3));
        CharacterCard card = controller.getCharacterCardBoard().getCharacterCard(cardId);
        int price = card.getPrice();
        controller.notifyUsedCharacterCard(cardId);
        assertEquals(price+1,card.getPrice());
        controller.notifyUsedCharacterCard(cardId);
        assertEquals(price+1,card.getPrice());
    }

    /**
     * Expected result:
     * Pablo use card 1 -> he is the first of round
     * player2 use card 1. She can't use other cards -> turnOrder =1.1 -> she is the second
     * player3 try to use card 1 but she can use other cards, she will chose another card
     * player3 use card 6 -> she is the last
     * player 4 use card 2 -> he is the third
     */
    @Test
    void turnOrder(){
        for(Player player : game.getPlayers()){
            player.getDeck().clear();
        }
        game.getPlayers().get(0).getDeck().add(new SupportCard(1,1,1));
        game.getPlayers().get(1).getDeck().add(new SupportCard(1,1,1));
        game.getPlayers().get(2).getDeck().add(new SupportCard(1,1,1));
        game.getPlayers().get(2).getDeck().add(new SupportCard(6,3,6));
        game.getPlayers().get(3).getDeck().add(new SupportCard(2,1,2));
        controller.setUsedSupportCard(1);
        controller.setUsedSupportCard(1);
        controller.setUsedSupportCard(1);
        controller.setUsedSupportCard(6);
        controller.setUsedSupportCard(2);
        assertEquals("Pablo",game.getPlayers().get(0).getNickName());
        assertEquals("player2",game.getPlayers().get(1).getNickName());
        assertEquals("player4",game.getPlayers().get(2).getNickName());
        assertEquals("player3",game.getPlayers().get(3).getNickName());
    }


    @Test
    void switchCC7(){
        ArrayList <StudentColor> studentsToHall = new ArrayList<>();
        ArrayList<StudentColor> studentsToCC7 = new ArrayList<>();
        studentsToHall.add(StudentColor.RED);
        studentsToHall.add(StudentColor.RED);
        studentsToCC7.add(StudentColor.GREEN);
        game.getCurrentPlayer().getBoard().getHall().addStudent(StudentColor.GREEN);
        controller.switchCC7(studentsToHall,studentsToCC7);
        assertFalse(game.getCurrentPlayer().getBoard().getHall().getStudents().contains(StudentColor.GREEN));
        assertTrue(game.getCurrentPlayer().getBoard().getHall().getStudents().addAll(studentsToHall));
    }

    @Test
    void addStudentToD(){
        controller.setGameStatus("expert", 4);
        game.getCurrentPlayer().getBoard().getDiningHall().getStudents().clear();
        controller.addStudentToD(StudentColor.RED);
        assertTrue(game.getCurrentPlayer().getBoard().getDiningHall().getStudents().contains(StudentColor.RED));
    }

    @Test
    void addStudentToI(){
        controller.setGameStatus("expert",4);
        ArrayList <StudentColor> students = controller.getBoard().getIsland(0).getStudents();
        students.add(StudentColor.RED);
        controller.addStudentToIsland(0,StudentColor.RED);
        assertTrue(controller.getBoard().getIsland(0).getStudents().containsAll(students));
    }

    @Test
    void refillCard (){
        controller.setGameStatus("expert",4);
        Bag bag = controller.getBoard().getBag();
        controller.refillCard(4,4);
        assertEquals(96-4,bag.totalNumOfStudents());

    }

    @Test
    void TowerWithMoreTeachers(){
        controller.setGameStatus("normal", 4);
        controller.setTower("WHITE");
        controller.setTower("BLACK");
        controller.setTower("BLACK");
        controller.setTower("WHITE");
        Tower whiteTower = controller.getBoard().getTower("WHITE");
        Tower blackTower = controller.getBoard().getTower("BLACK");
        for(int i=0;i<5;i++){
            controller.getBoard().getIsland(0).setTower(whiteTower);
        }
        for(int i =6; i<12; i++ ){
            controller.getBoard().getIsland(0).setTower(blackTower);
        }
        game.getPlayers().get(0).addRole(StudentColor.RED);
        game.getPlayers().get(1).addRole(StudentColor.YELLOW);
        game.getPlayers().get(2).addRole(StudentColor.GREEN);
        game.getPlayers().get(3).addRole(StudentColor.BLUE);
        game.getPlayers().get(3).addRole(StudentColor.PURPLE);
        assertEquals(whiteTower,controller.towerWithMoreTeachers());
    }

    @Test
    void assignTeacher(){
        controller.setGameStatus("expert",4);
        game.getCurrentPlayer().setBonusToPromotion(true);
        game.getPlayers().get(0).getBoard().getDiningHall().addStudent(StudentColor.GREEN);
        game.getPlayers().get(0).getBoard().getDiningHall().addStudent(StudentColor.GREEN);
        game.getPlayers().get(0).getBoard().getDiningHall().addStudent(StudentColor.YELLOW);
        game.getPlayers().get(1).addRole(StudentColor.GREEN);
        game.getPlayers().get(1).getBoard().getDiningHall().addStudent(StudentColor.GREEN);
        game.getPlayers().get(2).addRole(StudentColor.YELLOW);
        game.getPlayers().get(2).getBoard().getDiningHall().addStudent(StudentColor.YELLOW);
        game.getPlayers().get(3).getBoard().getDiningHall().addStudent(StudentColor.PURPLE);
        controller.assignTeachers();
        assertTrue(game.getPlayers().get(0).getRoles().contains(StudentColor.GREEN));
        assertTrue(game.getPlayers().get(0).getRoles().contains(StudentColor.YELLOW));
        assertTrue(game.getPlayers().get(0).getRoles().contains(StudentColor.BLUE));
        assertTrue(game.getPlayers().get(0).getRoles().contains(StudentColor.RED));
        assertTrue(game.getPlayers().get(3).getRoles().contains(StudentColor.PURPLE));

    }
}
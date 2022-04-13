package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.EndGameException;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StudentColor;
import it.polimi.ingsw.model.Tower;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    GameBoard board;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Tower whiteTower;
    Tower blackTower;
    @BeforeEach
    void setUp() {
        board =new GameBoard(4);
        player1=new Player("player1");
        player2=new Player("player2");
        player3=new Player("player3");
        player4=new Player("player4");
        whiteTower= new Tower("white");
        blackTower= new Tower("black");
        board.getTowers().add(whiteTower);
        board.getTowers().add(blackTower);
    }


    @AfterEach
    void tearDown() {
        board=null;
        player1=null;
        player2=null;
        player3=null;
        player4=null;
        whiteTower=null;
        blackTower=null;
    }


    /**
     * Testing if tower.influencePoints is calculated properly in 4 players mode
     * if it works properly the tower influence points should be equal to the sum of its owners' influence points
     * blackTower should have more points so Island n°7 tower color should became black
     * and it should be merged with island n°8
     * * Testing if EndGameException is properly thrown
     * if island n°7 tower color becomes black the last blackTower should be withdrawn
     * and so an EndGameException should be thrown
     */
    @Test
    void testAssignInfluencePointsCase0(){
        board.setCurrentPlayer(player1);
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        board.getPlayers().add(player3);
        board.getPlayers().add(player4);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        player3.setTower(whiteTower);
        player4.setTower(blackTower);
        board.getTeachers().put(StudentColor.GREEN, player1);
        board.getTeachers().put(StudentColor.YELLOW, player2);
        board.getTeachers().put(StudentColor.PURPLE,player4);
        board.getIsland(7).setIgnoreTower(true);
        board.getIsland(7).setTower(whiteTower);
        board.getIsland(8).setTower(blackTower);
        try {
            blackTower.decreaseAvailableTowers(7);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        board.getIsland(7).removeStudents(board.getIsland(7).getStudents());
        board.getIsland(7).addStudent(StudentColor.YELLOW);
        board.getIsland(7).addStudent(StudentColor.GREEN);
        board.getIsland(7).addStudent(StudentColor.PURPLE);
        boolean error=false;
        try {
            board.assignInfluencePoints(7);
        } catch (EndGameException e) {
            e.printStackTrace();
            error=true;
        }
        assertEquals(whiteTower.getInfluencePoints(),player1.getInfluencePoints()+player3.getInfluencePoints());
        assertEquals(blackTower.getInfluencePoints(),player2.getInfluencePoints()+ player4.getInfluencePoints());
        assertEquals(blackTower,board.getIsland(7).getTower());
        assertEquals(11,board.getIslands().size());
        assertTrue(error);
    }

    /**
     * Testing if ignoredColor works properly
     * if green students are properly ignored then player2 will have more influence points.
     * Island n°6 tower color should became black and it should be merged with island n°7
     */
    @Test
    void testAssignInfluencePointsCase1() {
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        board.setCurrentPlayer(player1);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        board.getIsland(6).setTower(whiteTower);
        board.getIsland(7).setTower(blackTower);
        board.addIgnoredColor(StudentColor.GREEN);
        board.setTeacher(StudentColor.GREEN,player1);
        board.setTeacher(StudentColor.YELLOW,player2);
        board.setTeacher(StudentColor.RED,player1);
        board.getIsland(6).removeStudents(board.getIsland(6).getStudents());
        for(int i=0; i<3; i++) {
            board.getIsland(6).addStudent(StudentColor.GREEN);
            board.getIsland(6).addStudent(StudentColor.YELLOW);
        }
        board.getIsland(6).addStudent(StudentColor.RED);
        try {
            board.assignInfluencePoints(6);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        assertEquals(11, board.getIslands().size() );
        assertEquals(blackTower, board.getIsland(6).getTower());
        board.resetIgnoredColor();
    }

    /**
     * Testing if ignoreTowers works properly
     * if towers are considered player1 will have the same influence points of player2
     * so the color of island n°6 will stay white.
     * ignoreTower is set to true so island n° 6 tower color should became black
     * and it should be merged with island n°7
     */
    @Test
    void testAssignInfluencePointsCase2() {
        board.setCurrentPlayer(player1);
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        board.getIsland(6).setTower(whiteTower);
        board.getIsland(7).setTower(blackTower);
        board.getIsland(6).setIgnoreTower(true);
        board.setTeacher(StudentColor.GREEN,player1);
        board.setTeacher(StudentColor.YELLOW,player2);
        board.getIsland(6).removeStudents(board.getIsland(6).getStudents());
        board.getIsland(6).addStudent(StudentColor.GREEN);
        board.getIsland(6).addStudent(StudentColor.YELLOW);
        board.getIsland(6).addStudent(StudentColor.YELLOW);
        try {
            board.assignInfluencePoints(6);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        assertEquals(11, board.getIslands().size());
        assertEquals(blackTower,board.getIsland(6).getTower());
        board.getIsland(6).setIgnoreTower(false);
    }

    /**
     * Testing if player.additionalInfluencePoints works properly
     */
    @Test
    void testAssignInfluencePointsCase3() {
        board.setCurrentPlayer(player2);
        player2.setAdditionalInfluencePoints(true);
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        board.getIsland(6).setTower(whiteTower);
        board.getIsland(7).setTower(blackTower);
        board.setTeacher(StudentColor.GREEN,player1);
        board.setTeacher(StudentColor.YELLOW,player2);
        board.getIsland(6).removeStudents(board.getIsland(6).getStudents());
        board.getIsland(6).addStudent(StudentColor.GREEN);
        board.getIsland(6).addStudent(StudentColor.YELLOW);
        try {
            board.assignInfluencePoints(6);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        assertEquals(11, board.getIslands().size());
        assertEquals(blackTower,board.getIsland(6).getTower());
        player2.setAdditionalInfluencePoints(false);
    }

    /**
     * Testing if the number of islands decreases under three
     * Testing if adjacent island are properly calculated
     */
    @Test
    void testMajority() {
        for(int i=0; i<8; i++){
            board.getIslands().remove(0);
        }
        board.getIsland(2).setTower(whiteTower);
        board.getIsland(0).setTower(whiteTower);
        board.getIsland(3).setTower(blackTower);
        whiteTower.setInfluencePoints(8);
        blackTower.setInfluencePoints(5);

        try {
            board.majority(3);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        assertEquals(3,board.getIslands().size());
    }

    @Test
    void mergeIslands() {
        ArrayList<StudentColor> students= new ArrayList<>();
        students.addAll(board.getIsland(0).getStudents());
        students.addAll(board.getIsland(1).getStudents());
        try {
            board.mergeIslands(0, 1);
        }catch (EndGameException e) {
            e.printStackTrace();
        }
        assertEquals(board.getIsland(0).getStudents(), students);
        assertEquals(2,board.getIslands().get(0).getNumOfTowers());
        students=null;
    }

    @Test
    void testInitClouds(){
        assertEquals(3,board.getClouds().get(0).getMaxStudents());
    }
}
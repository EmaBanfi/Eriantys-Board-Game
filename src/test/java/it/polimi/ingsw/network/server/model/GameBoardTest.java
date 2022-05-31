package it.polimi.ingsw.network.server.model;

import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.network.server.DummyServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.model.GameBoard;
import it.polimi.ingsw.network.server.model.Player;
import it.polimi.ingsw.network.server.model.StudentColor;
import it.polimi.ingsw.network.server.model.Tower;
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
    Tower blackTower;
    Tower whiteTower;
    Server server;

    @BeforeEach
    void setUp() {
        server = new DummyServer();
        board =new GameBoard(4);
        player1=new Player("player1");
        player2=new Player("player2");
        player3=new Player("player3");
        player4=new Player("player4");
        whiteTower=board.getTower("WHITE");
        blackTower=board.getTower("BLACK");
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
        server = null;
    }


    /**
     * Testing if tower.influencePoints is calculated properly in 4 players mode
     * if it works properly the tower influence points should be equal to the sum of its owners' influence points
     * blackTower should have more points so Island n°7 tower color should become black,
     * and it should be merged with island n°8
     * * Testing if EndGameException is properly thrown
     * if island n°7 tower color becomes black the last blackTower should be withdrawn
     * and so an EndGameException should be thrown
     */
    @Test
    void testAssignInfluencePointsCase0(){
        System.out.println("Case 0");
        System.out.println(board.getIslands().size());
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        board.getPlayers().add(player3);
        board.getPlayers().add(player4);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        player3.setTower(whiteTower);
        player4.setTower(blackTower);
        player1.addRole(StudentColor.GREEN);
        player2.addRole(StudentColor.YELLOW);
        player4.addRole(StudentColor.PURPLE);
        board.getIsland(7).setIgnoreTower(true);
        board.setTower(whiteTower,7);
        board.setTower(blackTower,8);
        try {
            blackTower.decreaseAvailableTowers(7);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        board.getIsland(7).removeStudents(board.getIsland(7).getStudents());
        board.getIsland(7).addStudent(StudentColor.YELLOW);
        board.getIsland(7).addStudent(StudentColor.GREEN);
        board.getIsland(7).addStudent(StudentColor.PURPLE);
        board.getIsland(7).addStudent(StudentColor.PURPLE);
        boolean error=false;
        try {
            board.assignInfluencePoints(7, server,false, player1);
        } catch (EndGameException e) {
            error=true;
        }
        System.out.println(board.getIsland(7).getTower());
        assertEquals(whiteTower.getInfluencePoints(),player1.getInfluencePoints()+player3.getInfluencePoints());
        assertEquals(blackTower.getInfluencePoints(),player2.getInfluencePoints()+ player4.getInfluencePoints());
        System.out.println(board.getIsland(7).getTower());
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
        //System.out.println("case 1");
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        board.getIsland(6).setTower(whiteTower);
        board.getIsland(7).setTower(blackTower);
        board.addIgnoredColor(StudentColor.GREEN);
        player1.addRole(StudentColor.GREEN);
        player1.addRole(StudentColor.RED);
        player2.addRole(StudentColor.YELLOW);
        board.getIsland(6).removeStudents(board.getIsland(6).getStudents());
        for(int i=0; i<3; i++) {
            board.getIsland(6).addStudent(StudentColor.GREEN);
            board.getIsland(6).addStudent(StudentColor.YELLOW);
        }
        board.getIsland(6).addStudent(StudentColor.RED);
        System.out.println(board.getIslands().size());
        try {
            board.assignInfluencePoints(6, server, false, player1);
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
        //System.out.println("case 2");
        boolean endGameExceptionThrown=false;
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        board.getIsland(6).setTower(whiteTower);
        board.getIsland(7).setTower(blackTower);
        board.getIsland(6).setIgnoreTower(true);
        player1.addRole(StudentColor.GREEN);
        player2.addRole(StudentColor.YELLOW);
        board.getIsland(6).removeStudents(board.getIsland(6).getStudents());
        board.getIsland(6).addStudent(StudentColor.GREEN);
        board.getIsland(6).addStudent(StudentColor.YELLOW);
        board.getIsland(6).addStudent(StudentColor.YELLOW);
        try {
            board.assignInfluencePoints(6, server, false, player1);
        } catch (EndGameException e) {
            endGameExceptionThrown=true;
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
        boolean endGameExceptionThrown=false;
        //System.out.println("case 3");
        player2.setAdditionalInfluencePoints(true);
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        player1.setTower(whiteTower);
        player2.setTower(blackTower);
        board.getIsland(6).setTower(whiteTower);
        board.getIsland(7).setTower(blackTower);
        player1.addRole(StudentColor.GREEN);
        player2.addRole(StudentColor.YELLOW);
        board.getIsland(6).removeStudents(board.getIsland(6).getStudents());
        board.getIsland(6).addStudent(StudentColor.GREEN);
        board.getIsland(6).addStudent(StudentColor.YELLOW);
        try {
            board.assignInfluencePoints(6, server, false,player2);
        } catch (EndGameException e) {
            endGameExceptionThrown=true;
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
        boolean endGameExceptionThrown=false;
        board.getIslands().subList(0, 8).clear();
        board.getIsland(2).setTower(whiteTower);
        board.getIsland(0).setTower(whiteTower);
        board.getIsland(3).setTower(blackTower);
        whiteTower.setInfluencePoints(8);
        blackTower.setInfluencePoints(5);

        try {
            board.majority(3, server);
        } catch (EndGameException e) {
            endGameExceptionThrown=true;
        }
        assertEquals(3,board.getIslands().size());
        assertTrue(endGameExceptionThrown);
    }

    @Test
    void mergeIslands() {
        boolean endGameExceptionThrown=false;
        ArrayList<StudentColor> students= new ArrayList<>();
        students.addAll(board.getIsland(0).getStudents());
        students.addAll(board.getIsland(1).getStudents());
        try {
            board.mergeIslands(0, 1);
        } catch (EndGameException e) {
            endGameExceptionThrown=true;
        }
        assertEquals(board.getIsland(0).getStudents(), students);
        assertEquals(2,board.getIslands().get(0).getNumOfTowers());
    }

    @Test
    void testInitClouds(){
        assertEquals(3,board.getClouds().get(0).getMaxStudents());
    }
}
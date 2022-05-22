package it.polimi.ingsw.network.server.model;

import com.google.gson.Gson;
import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.server.Server;

import java.util.ArrayList;

public class GameBoard {
    private Gson gson;
    private final MotherNature motherNature;
    private ArrayList<Cloud> clouds;
    private final ArrayList<Island> islands;
    /**
     * This attribute is used to implement the effect of the CharacterCard n°9
     * The ignored color will not be considered in the assigment of influence points
     */
    private StudentColor ignoreColor;
    private Player currentPlayer;
    private final Bag bag;
    private ArrayList<Tower> towers;
    /**
     * This attribute is used to memorise which player is the teacher of each color
     */
    //private HashMap <StudentColor,Player> teachers;
    private ArrayList<Player> players;

    public GameBoard(int numberOfPlayers) {
        bag = new Bag();
        players= new ArrayList<>();
        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            islands.add(new Island());
        //initTeachers();
        motherNature = new MotherNature(islands);
        initIslands();
        initTowers(numberOfPlayers);
        initClouds(numberOfPlayers);
        ignoreColor = null;
        gson = new Gson();
    }

    /**
     * This method is used to initialise the Hashmap teachers
     */
    /*private void initTeachers(){
        teachers=new HashMap<>();
        for (StudentColor color: StudentColor.values()) {
            teachers.put(color, null);
        }
    }*/

    private void initTowers(int numOfTowers){
        towers=new ArrayList<>();
        if(numOfTowers==3){
            towers.add(new Tower("Gray"));
        }
        towers.add(new Tower("White"));
        towers.add(new Tower("Black"));
    }

    /**
     * This method is used to initialise the Arraylist clouds
     * @param numberOfPlayers is used to determine the number of students on each cloud
     */
    public void initClouds(int numberOfPlayers){
        clouds = new ArrayList<>();
        int maxStudents;
        if (numberOfPlayers % 2 == 0)
            maxStudents = 3;
        else
            maxStudents = 4;
        for (int i = 0; i < numberOfPlayers; i++) {
            clouds.add(new Cloud(maxStudents));
        }
        for(Cloud cloud : clouds){
            try {
                refillCloud(cloud);
            } catch (LastStudentDrawnException e) {
            }
        }
    }

    /**
     * This method is used to initialise the attribute islands and
     * to place the students on each island
     */
    public void initIslands(){
        for (int i = 0; i < 12; i++) {
            if (i != (motherNature.getCurrentIsland() + 6) % 12) {
                try {
                    islands.get(i).addStudents(bag.draw(2));
                } catch (LastStudentDrawnException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  void MajorityOnCurrentIsland(Server server) throws EndGameException {
        assignInfluencePoints(motherNature.getCurrentIsland(),server, true);
    }

    public void MajorityOnIsland(int movements, Server server) throws EndGameException {
        assignInfluencePoints(motherNature.getCurrentIsland() + movements, server, false);
    }

    /**
     * This method is used to assign influence points to players and towers
     * @param island index of the island which will be considered to calculate influence points
     * @throws EndGameException because it calls majority. this exception is handled by the gameController
     */
    public void assignInfluencePoints(int island, Server server, boolean ignoreBlock) throws EndGameException{
        ServerMessage message;
        String text = "";
        if(!islands.get(island).isBlockCard()&&(!ignoreBlock)) {
            Island currentIsland = islands.get(island);
            for (Player player : players) {
                player.setInfluencePoints(0);
            }
            for (Tower tower : towers) {
                tower.setInfluencePoints(0);
            }
            for (Player player : players) {
                if (player.hasAdditionalInfluencePoints()) {
                    currentPlayer.addInfluencePoints(2);
                    currentPlayer.setAdditionalInfluencePoints(false);
                    text = player.getNickName() + " gains 2 influence points as effect of character card 8 \n";
                }
                for (StudentColor teacher : player.getRoles()) {
                    if (!teacher.equals(ignoreColor)) {
                        int num = currentIsland.countStudentColor(teacher);
                        player.addInfluencePoints(num);
                        text = text +
                                player.getNickName() +
                                " gains " +
                                num +
                                " influence points because they are the " +
                                teacher.toString() + " teacher. \n";
                    } else {
                        resetIgnoredColor();
                        text = text +
                                teacher.toString() +
                                " students are set to be ignored so they don't provide influence points \n";
                    }
                }
            }
            for (Player player : players) {
                int num = player.getInfluencePoints();
                player.getTower().addInfluencePoints(num);
                text = text +
                        player.getTower().getTowerColor() +
                        " tower gains " +
                        num + " influence point from " +
                        player.getNickName() + "\n";
            }
            if (!currentIsland.isIgnoreTower()) {
                for (Tower tower : towers) {
                    int num = currentIsland.getNumOfTowers();
                    if (tower.equals(currentIsland.getTower())) {
                        tower.addInfluencePoints(num);
                        text = text +
                                tower.getTowerColor() + " tower " +
                                "gains " + num + " influence points " +
                                " form the towers on islands \n";
                    }
                }
            } else {
                currentIsland.setIgnoreTower(false);
                text = text +
                        "for this turn towers on islands don't provide influence points\n";
            }
            for (Tower tower : towers) {
                text = text +
                        tower.getTowerColor() + " tower has " +
                        tower.getInfluencePoints() + " influence points";

            }
            message = new smNotify(text);
            server.sendAll(gson.toJson(message, smNotify.class));
            majority(island, server);
        }
        else{
            text = "majority was not calculated on this island because there is a block.\n"
                    + "The block will now be removed";
            message = new smNotify(text);
            server.sendAll(gson.toJson(message, smNotify.class));
            message = new smNotifyBlocksOnCard(false);
            server.sendAll(gson.toJson(message, smNotifyBlocksOnCard.class));
        }
    }

    /**
     * This method is used to determine the tower of a certain island or group of islands
     * @param island index of the island whose tower must be determined
     * @throws EndGameException because it calls tower.decreaseAvailableTowers(). This exception is passed on to the calling method
     */
    public void majority(int island, Server server) throws EndGameException{
        int next, previous;
        boolean towerNotChanged = true;
        String text;
        ServerMessage message;
        if(island+1>=islands.size())
            next=0;
        else
            next=island+1;
        if(island-1<=0)
            previous=islands.size()-1;
        else
            previous= island-1;
        Tower newTower=islands.get(island).getTower();
        for(Tower tower: towers){
            if(tower.getInfluencePoints()>newTower.getInfluencePoints()) {
                newTower.increaseAvailableTowers(islands.get(island).getNumOfTowers());
                newTower = tower;
            }
            if (towerNotChanged)
                towerNotChanged= false;
        }
        if(!towerNotChanged) {
            text = "Island " + (island +1) + " tower changed from " +
                islands.get(island).getTower().getTowerColor() + " to " + newTower.getTowerColor();
            message = new smNotifyTowerColor(
                    text,
                    island,
                    newTower.getTowerColor()
            );
            server.sendAll(gson.toJson(message, smNotifyTowerColor.class));
            islands.get(island).setTower(newTower);
            //System.out.println("island n "+island+" tower color is "+islands.get(island).getTower().getTowerColor());
            if (islands.get(island).getTower()==islands.get(next).getTower()) {
                text = "island " + (previous + 1) + " has been merged in to island " + (island +1);
                message = new smNotifyMerge(
                        text,
                        island,
                        previous
                );
                server.sendAll(gson.toJson(message, smNotifyMerge.class));
                mergeIslands(island, next);
                newTower.decreaseAvailableTowers(islands.get(island).getNumOfTowers());
            }
            if (islands.get(island).getTower() == islands.get(previous).getTower()) {
                text = "island " + (next + 1) + " has been merged in to island " + (island +1);
                message = new smNotifyMerge(
                        text,
                        island,
                        next
                        );
                server.sendAll(gson.toJson(message, smNotifyMerge.class));
                mergeIslands(island, previous);
                newTower.decreaseAvailableTowers(islands.get(island).getNumOfTowers());
            }
            else{
                text = "There will be no merging because adjacent islands have tower of different colors than" +
                        " island " + (island +1 );
                message = new smNotify(text);
                server.sendAll(gson.toJson(message));
            }
        }
        else{
            text = "island " + (island+1) + " tower color has not changed";
            message = new smNotify(text);
            server.sendAll(gson.toJson(message, smNotify.class));
        }
    }

    /**
     * This method is used to merge island when they are controlled by the same island
     * @param first index of the island that will accept the students from the secondo island
     * @param second index of the island that will be merged in the first one and will be eliminated
     * @throws EndGameException if the number of the remaining island reaches 3
     */
    public void mergeIslands(int first, int second) throws EndGameException{
        islands.get(first).addStudents(islands.get(second).getStudents());
        islands.get(first).addTower(islands.get(second).getNumOfTowers());
        islands.remove(second);
        if(islands.size()==3)throw new EndGameException("Only three islands remaining");
    }

    public void addIgnoredColor(StudentColor color){
        ignoreColor = color;
    }

    public void resetIgnoredColor(){
        ignoreColor = null;
    }

    public StudentColor getIgnoredColor() {
        return ignoreColor;
    }

    /**
     * This method is used to take the students from a certain cloud
     * @param cloud index of the cloud from which students must be taken
     */
    public ArrayList<StudentColor> getStudentsFromCloud(int cloud){
        ArrayList<StudentColor> students = new ArrayList<>(clouds.get(cloud).getStudents());
        clouds.get(cloud).getStudents().clear();
        return students;
    }

    /**
     * this method refills the specified cloud
     * @param cloud indicates the cloud to be refilled
     * @throws LastStudentDrawnException because it calls bag.draw(). this exception will be handled by GameController
     */
    public void refillCloud(Cloud cloud) throws LastStudentDrawnException{
        cloud.addStudents(bag.draw(cloud.getMaxStudents()));
    }

    public ArrayList<StudentColor> takeStudents(int cloud){
        return clouds.get(cloud).getStudents();
    }

    public void moveMotherNature(int movements){
        motherNature.move(movements);
    }


    public void setTower(Tower tower, int island){
        islands.get(island).setTower(tower);
    }

    public ArrayList<Tower> getTowers(){
        return towers;
    }

    public Bag getBag() {
        return bag;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public ArrayList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature(){
        return motherNature;
    }

    public Island getIsland(int island){
        return islands.get(island);
    }

    public ArrayList<Cloud> getClouds(){
        return clouds;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Tower getTower(String towerColor){
        Tower tower=null;
        for(Tower t: towers){
            if(t.getTowerColor().equals(towerColor))
                tower=t;
        }
        return tower;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players=players;
    }

    public int getCloudNumber(Cloud cloud){
        int num = clouds.indexOf(cloud)+1;
        return  num;
    }
}

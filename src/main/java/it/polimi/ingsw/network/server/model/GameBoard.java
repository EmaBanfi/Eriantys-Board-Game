package it.polimi.ingsw.network.server.model;

import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {
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
    private HashMap <StudentColor,Player> teachers;
    private final ArrayList<Player> players;

    public GameBoard(int numberOfPlayers) {
        bag = new Bag();
        players= new ArrayList<>();
        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            islands.add(new Island());
        initTeachers();
        motherNature = new MotherNature(islands);
        initIslands();
        initTowers(numberOfPlayers);
        initClouds(numberOfPlayers);
        ignoreColor = null;
    }

    /**
     * This method is used to initialise the Hashmap teachers
     */
    private void initTeachers(){
        teachers=new HashMap<>();
        for (StudentColor color: StudentColor.values()) {
            teachers.put(color, null);
        }
    }

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

    /**
     * This method is used to assign influence points to players and towers
     * @param island index of the island which will be considered to calculate influence points
     * @throws EndGameException because it calls majority. this exception is handled by the gameController
     */
    public void assignInfluencePoints(int island) throws EndGameException{
        Island currentIsland= islands.get(island);
        for(Player player: players){
            player.setInfluencePoints(0);
        }
        for(Tower tower: towers){
            tower.setInfluencePoints(0);
        }
        if(currentPlayer.hasAdditionalInfluencePoints()){
            currentPlayer.addInfluencePoints(2);
        }
        for (StudentColor color : StudentColor.values()) {
            if (ignoreColor != color) {
                if(teachers.get(color)!=null){
                    teachers.get(color).addInfluencePoints(currentIsland.countStudentColor(color));
                    //System.out.println(teachers.get(color).getNickName() +" gains "+ currentIsland.countStudentColor(color)+" influence points from "+ currentIsland.countStudentColor(color) +" "+color +" students");
                }
            }
        }
        if (!currentIsland.isIgnoreTower()) {
            for(Tower tower: towers){
                if(tower.equals(currentIsland.getTower())){
                    tower.addInfluencePoints(currentIsland.getNumOfTowers());
                    //System.out.println(tower.getTowerColor() + " gains "+ currentIsland.getNumOfTowers()+ " influence points because he owns the current tower");
                }
            }
        }
        for (Player player: players){
            //System.out.println(player.getNickName()+" has "+player.getInfluencePoints()+" influence points");
            player.getTower().addInfluencePoints(player.getInfluencePoints());
        }
        //for(Tower tower: towers)
            //System.out.println(tower.getTowerColor()+" tower has "+ tower.getInfluencePoints()+ " influence points");
        majority(island);
    }

    /**
     * This method is used to determine the tower of a certain island or group of islands
     * @param island index of the island whose tower must be determined
     * @throws EndGameException because it calls tower.decreaseAvailableTowers(). This exception is passed on to the calling method
     */
    public void majority(int island) throws EndGameException{
        if(!islands.get(island).isBlockCard()) {
            int next, previous;
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
            }
            islands.get(island).setTower(newTower);
            //System.out.println("island n "+island+" tower color is "+islands.get(island).getTower().getTowerColor());
            if(islands.get(next).getTower()==islands.get(island).getTower())
                mergeIslands(island,next);
            if(islands.get(island).getTower()==islands.get(previous).getTower())
                mergeIslands(island,previous);
            newTower.decreaseAvailableTowers(islands.get(island).getNumOfTowers());
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

    public void addStudentsToIsland(HashMap <Integer, ArrayList<StudentColor>> studentsToAdd){
        for (Integer index: studentsToAdd.keySet()) {
            islands.get(index).addStudents(studentsToAdd.get(index));
        }
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

    public HashMap<StudentColor, Player> getTeachers() {
        return teachers;
    }

    public void setTeacher(StudentColor teacher, Player player) {
        teachers.put(teacher,player);
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
}

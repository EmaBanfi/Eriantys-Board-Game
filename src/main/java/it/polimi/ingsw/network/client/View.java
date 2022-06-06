package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;
import java.util.HashMap;

public interface View {

    /**
     * get the player
     * @return the player
     */
    PlayerView getPlayer();

    /**
     * get the player by nickname
     * @return the player
     */
    PlayerView getPlayerByNick(String nick);


    private void initAvailableDecks() {}

    private void initAvailableTowers() {}

    /**
     * communicate to the player to insert the nickname. Called by message.
     */
    void askNickName();

    /**
     * ask to the player if he want to choose a character card. Called by method.
     */
    void askActivateCharacterCard();

    /**
     * communicate to the player to choose a character card. Called by method.
     */
    void askCharacterCard();

    /**
     * choose the numOfPlayers and mode (only to the first player connected). Called by message.
     */
    void askSetGameStatus();

    /**
     * ask to set a color for the tower (the controller will notify if is correctly set). Called by method.
     */
    void askTower();

    /**
     * ask to choose a deck (the controller will do a check). Called by method.
     */
    void askDeck();

    /**
     * ask to choose a support card (or nothing). Called by method.
     */
    void askSupportCard();

    /**
     * ask to the player which students wants to move from hall to dining hall. Called by method.
     */
    void askMoveStudentsHToD();

    /**
     * ask to the player which students wants to move from hall to island (single or multiple). Called by method.
     */
    void askMoveStudentsHToI();

    /**
     * ask to the player from which cloud wants to take the students. Called by method.
     */
    void askCloud();

    /**
     * ask where he wants to put mother nature (the controller will do a check). Called by method.
     */
    void askMotherNatureMovements();

    /**
     * show a message (string) on the view screen. Called by message.
     * @param message the message to print.
     */
    void showString(String message);

    /**
     * show the character card used by a player and his name. Called by message.
     * @param id id of the character card used.
     */
    void showCharacterCard(int id);

    /**
     * show the support card used by a player and his name. Called by message
     * @param id id of the support card used.
     */
    void showSupportCard(int id);

    /**
     * show on the view screen which support card is using during that turn. Called by method.
     */
    void updateUsedSupportCard(int id);

    /**
     * decrease the numbers of the remaining support cards. Called by method.
     */
    void updateAvailableSupportCards();

    /**
     * show on screen the price of a specific character card. Called by message.
     * @param id id of the character card to increase the price
     */
    void updateCharacterCardPrice(int id);

    /**
     * show the new position of mother nature (when other players changes it). Called by message.
     * @param island the island where mother nature is now positioned.
     */
    void updateMotherPosition(int island);

    /**
     * show the new color of the island for a tower. Called by message.
     * @param tower the color of the tower.
     */
    void updateTowerColor(String tower);

    /**
     * merge two islands. Called by message.
     * @param toBeMerged the island that is merged.
     * @param mergeTo the island that remain.
     */
    void mergeIslands(int toBeMerged, int mergeTo);

    /**
     * show the added students on a specific island. Called by message.
     * @param island chosen island
     * @param students students to add
     */
    void addStudentsOnIsland(int island, ArrayList<StudentColor> students);

    /**
     * show the added students in the dining hall of the specified player. Called by message
     * @param students the students to add
     * @param nick the nickname of the player
     */
    void addStudentToPlayerD(String nick, ArrayList<StudentColor> students);

    /**
     * remove the students from the dining hall of a specified player. Called by message.
     * @param nick the nickname of the player
     * @param students the students to remove
     */
    void removeStudentsFromPlayerD(String nick, ArrayList<StudentColor> students);

    /**
     * show the winners and the losers. Called by message.
     * @param winners winners
     * @param losers losers
     */
    void showGameResults(ArrayList<String> winners, ArrayList<String> losers);

    /**
     * set additional turn order for a used support card.
     * @param id id of the support card
     * @param additionalTurnOrder additionalTurnOrder of the support card.
     */
    void setAdditionalTurnOrder(int id, double additionalTurnOrder);

    /**
     * updates the students on card
     * @param id id of the character card
     * @param students students to manage
     * @param add add the block card if it's true, else remove
     */
    void updateStudentsOnCard(int id, ArrayList<StudentColor> students, boolean add);

    /**
     * update the amount of coins that the player has. Called by message.
     * @param coin the updated coins
     */
    void updatePlayerCoins(int coin);

    /**
     * updates to the other players (not the first one) the status of the game
     * @param numOfPlayers the number of players for that match
     * @param mode the mode of the match
     */
    void updateGameStatus(int numOfPlayers, String mode);

    /**
     * update the current player
     * @param currentPlayer nickname of the current player
     */
    void updateCurrentPlayer(String currentPlayer);


    /**
     *  keep track of the status of the turn
     */
    void resumeFrom();

    /**
     * updates the block cards on the CC 5
     * @param add add the block card if it's true, else remove
     */
    void updateBlockOnCard(boolean add);

    /**
     * updates the clouds already chosen
     * @param cloud the last cloud chosen
     */
    void updateEmptyCloud(int cloud);

    /**
     * updates the tower on a specific island
     * @param island the specific island
     * @param tower the new tower color
     */
    void updateTowerOnIsland(int island, String tower);

    /**
     * add students on cloud.
     * @param cloud cloud to fill
     * @param students students to add
     */
    void addStudentsOnCloud(int cloud, ArrayList<StudentColor> students);

    /**
     *  updates which player is a teacher
     * @param roles hashmap of the
     */
    void updateTeacher(HashMap<StudentColor, String> roles);

    /**
     *  show the deck chosen by the current player.
     * @param deck deck chosen by the current player.
     */
    void setPlayerDeck(String deck);

    /**
     * show the support card used by the current player.
     * @param id id of the support card.
     */
    void setSupportCard(int id);

    /**
     * block an island
     * @param island island to block
     */
    void blockIsland(int island);

    /**
     * unblock an island
     * @param island island to unblock
     */
    void unlockIsland(int island);

    /**
     * add students to the player hall
     * @param students students to add
     */
    void addStudentsToHall(ArrayList<StudentColor> students);

    /**
     * remove students to the player hall
     * @param students students to remove
     */
    void removeStudentsFromHall(ArrayList<StudentColor> students);

    /**
     * to get the current player nickname
     * @return current player nickname
     */
    String getCurrentPlayer();

    /**
     * to get the available Island
     *
     * @return ArrayList of Island
     */
    ArrayList<IslandView> getAvailableIslands();

    /**
     * to get a Client object
     * @return Client object
     */
    Client getClient();

    /**
     * used to create available cards
     * @param card card to add
     */
    void addAvailableCard(int card);

    void showIslands();

    int askIsland(boolean show);

    /**
     * after the view received the game results, the player will communicate the disconnection from the server
     * The player have to insert anything and when he presses enter the game will be closed, and he will be disconnected
     * from the server
     */
    void disconnectFromServer();

    /**
     * this method is used to initialise the players arraylist
     * @param players nick of all the players in the match
     */
    void addPlayers(ArrayList<String> players);
}

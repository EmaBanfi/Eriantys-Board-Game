package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class View implements Runnable {

    @Override
    public void run() {
    }

    public abstract PlayerView getPlayer();

    public abstract PlayerView getPlayerByName(String nick);

    private void initAvailableDecks(){}

    public abstract void initAvailableTowers();

    /**
     * communicate to the client to insert the nickname. Called by message
     */
    public abstract void askNickName();

    /**
     * ask to the client if he want to choose a character card. Called by method
     */
    public abstract void askActivateCharacterCard();

    /**
     * communicate to the client to choose a character card. Called by method
     */
    public abstract void askCharacterCard();

    /**
     * choose the numOfPlayers and mode. (only to the first player connected). Called by message
     */
    public abstract void askSetGameStatus();

    /**
     * ask to set a color for the tower (the controller will notify if is correctly setted). Called by method
     */
    public abstract void askTower();

    /**
     * ask to choose a deck (the controller will do a check). Called by method
     */
    public abstract void askDeck();

    /**
     * ask to choose a support card (or nothing). Called by method
     */
    public abstract void askSupportCard();

    /**
     * ask to the client which students wants to move from H to D. Called by method
     */
    public abstract void askMoveStudentsHToD();

    /**
     * ask to the client which students wants to move from H to I (single or multiple). Called by method
     */
    public abstract void askMoveStudentsHToI();

    /**
     * ask to the client from which C wants to take the students. Called by method
     */
    public abstract void askCloud();

    /**
     * ask where he wants to put mother nature (the controller will do a check). Called by method
     */
    public abstract void askMotherNatureMovements();

    /**
     * show a message (string) on the client screen. Called by message
     * @param message
     */
    public abstract void showString(String message);

    /**
     * show the character card used by a player and his name. Called by message
     * @param id
     */
    public abstract void showCharacterCard(int id);

    /**
     * show the support card used by a player and his name. Called by message
     * @param id
     */
    public abstract void showSupportCard(int id);

    /**
     * show on the client screen which support card is using during that turn. Called by method
     */
    public abstract void updateUsedSupportCard();

    /**
     * show the available support card to the client. Called by method
     */
    public abstract void updateAvailableSupportCards();

    /**
     * show on screen the price of a specific character card. Called by message
     * @param id
     */
    public abstract void updateCharacterCardPrice(int id);

    /**
     * show the new position of mother nature (when other players changes it). Called by message
     * @param island
     */
    public abstract void updateMotherPosition(int island);

    /**
     * show the new color of the island for a tower. Called by message
     * @param tower
     */
    public abstract void updateTowerColor(String tower);

    /**
     * merge two islands. Called by message
     * @param mergeTo
     * @param toBeMerged
     */
    public abstract void mergeIslands(int toBeMerged, int mergeTo);

    /**
     * show the added students on a specific island. Called by message
     * @param island
     * @param students
     */
    public abstract void addStudentsOnIsland(int island, ArrayList<StudentColor> students);

    /**
     * show the added students in the dining hall of the specified player. Called by message
     * @param students
     * @param nick
     */
    public abstract void addStudentToPlayerD(String nick, ArrayList<StudentColor> students);

    /**
     * show the winners and the losers. Called by message
     *
     * @param winners
     * @param losers
     */
    public abstract void showGameResults(ArrayList<String> winners, ArrayList<String> losers);

    /**
     * remove the students from the dining hall of a specified player. Called by message
     * @param nick
     * @param students
     */
    public abstract void removeStudentsFromPlayerD(String nick, ArrayList<StudentColor> students);

    /**
     * update the hall of a specified player. Called by message
     * @param students
     * @param nick
     */
    public abstract void updatePlayerHall(String nick, ArrayList<StudentColor> students);

    /**
     * update the amount of coins that the player has. Called by message
     * @param coin
     */
    public abstract void updatePlayerCoins(int coin);

    public abstract void updateGameStatus(int numOfPlayers, String mode);

    public abstract void updateCurrentPlayer(String currentPlayer);

    public abstract void updatePlayerOrder(ArrayList<String> playerOrder);

    /**
     *  keep track of the status of the turn
     */
    public abstract void resumeFrom();

    /**
     * updates the clouds already chosen
     * @param cloud
     */
    public abstract void updateEmptyCloud(int cloud);

    /**
     * updates the ignored color given by the effect of the character card
     * @param color
     */
    public abstract void updateIgnoredColor(StudentColor color);

    /**
     * updates the tower on an island
     * @param island
     * @param tower
     */
    public abstract void updateTowerOnIsland(int island, String tower);

    /**
     * updates the student on the character card
     * @param students
     * @param cardId
     */
    public abstract void updatesStudentsOnCard(int cardId, ArrayList<StudentColor> students);

    /**
     * add students on cloud
     * @param cloud
     * @param students
     */
    public abstract void addStudentsOnCloud(int cloud, ArrayList<StudentColor> students);

    /**
     * remove students from cloud
     * @param students
     */
    public abstract void removeStudentsFromCloud(ArrayList<StudentColor> students);

    /**
     *  updates which player is a teacher
     * @param roles
     */
    public abstract void updateTeacher(HashMap<StudentColor, String> roles);

    /**
     *  show the deck chosen by the current player
     */
    public abstract void setPlayerDeck(String deck);

    /**
     * show the support card used by the current player
     */
    public abstract void setSupportCard();

    /**
     * remove a block card from the character card 5
     */
    public abstract void removeBlockOnCard();

    /**
     * add a block card from the character card 5
     */
    public abstract void addBlockOnCard();

    /**
     * block an island
     * @param island island to block
     */
    public abstract void blockIsland(int island);

    /**
     * unblock an island
     * @param island island to unblock
     */
    public abstract void unlockIsland(int island);

    /**
     * ignore the tower for the calculation of the influence
     * @param island island where the tower must be ignored
     */
    public abstract void ignoreTower(int island);

    /**
     * consider the tower for the calculation of the influence
     * @param island island where the tower must be considered
     */
    public abstract void notIgnoreTower(int island);

    /**
     * update the color ignored during the influence calculation
     * @param color the color to ignore
     */
    public abstract void ignoreColor(StudentColor color);

    /**
     * update the color not ignored during the influence calculation
     * @param color the color not to ignore
     */
    public abstract void notIgnoreColor(StudentColor color);

    /**
     * update the available character cards
     * @param availableCharacterCards list of the available character cards
     */
    public abstract void updateCharacterCards(ArrayList<CharacterCard> availableCharacterCards);

    /**
     * set the variable numOfPlayers
     * @param numOfPlayers value to put in numOfPlayers
     */
    public abstract void setNumOfPlayers(int numOfPlayers);

    /**
     * set the variable mode
     * @param mode string that indicate the mode of the game
     */
    public void setMode(String mode) {
    }
}

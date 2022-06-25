package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;
import java.util.HashMap;

public interface View {

    /**
     * @return the viewController
     */
    ViewController getViewController();

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

    ArrayList<StudentColor> askStudentsFromHall(int numOfStudents, boolean showHall);

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
     * show on screen the price of a specific character card. Called by message.
     * @param id id of the character card to increase the price
     */
    void updateCharacterCardPrice(int id);


    /**
     * show the winners and the losers. Called by message.
     * @param winners winners
     * @param losers losers
     */
    void showGameResults(ArrayList<String> winners, ArrayList<String> losers);

    /**
     * updates the students on card
     * @param id id of the character card
     * @param students students to manage
     * @param add add the block card if it's true, else remove
     */
    void updateStudentsOnCard(int id, ArrayList<StudentColor> students, boolean add);


    /**
     * updates the block cards on the CC 5
     * @param add add the block card if it's true, else remove
     */
    void updateBlockOnCard(boolean add);


    /**
     * to get a Client object
     * @return Client object
     */
    Client getClient();

    /**
     * used to create available cards
     * @param card card to add
     */
    void addAvailableCC(int card);

    /**
     * after the view received the game results, the player will communicate the disconnection from the server
     * The player have to insert anything and when he presses enter the game will be closed, and he will be disconnected
     * from the server
     */
    void disconnectFromServer();

    void closeGame();

}

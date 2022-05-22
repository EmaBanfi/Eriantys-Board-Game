package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class View implements Runnable {

    private void initAvailableDecks(){
        }

        public abstract void initAvailableTowers();

        @Override
        public void run() {
        }

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
         * @param nick
         */
        public abstract void updateTowerColor(String tower, String nick);

        /**
         * show the island blocked. Called by message
         * @param blocked
         * @param island
         */
        public abstract void updateBlock(int island, boolean blocked);

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
         * @param winners
         * @param losers
         * @param numOfPlayers
         */
        public abstract void showGameResults(ArrayList<String> winners, ArrayList<String> losers, int numOfPlayers);

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
         * @param nick
         * @param coin
         */
        public abstract void updatePlayerCoins(String nick, int coin);

        public abstract void updateGameStatus(int numOfPlayers, String mode);

        public abstract void updateCurrentPlayer(String currentPlayer);

        public abstract void updatePlayerOrder(ArrayList<String> playerOrder);

        /**
         *  keep track of the status of the turn
         */
        public abstract void resumeFrom();

        /**
         * updates the blockCard effect of the character card
         * @param block
         */
        public abstract void updateBlockOnCard(int block);

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
         * updates the ignored Tower given by the effecto of the character card
         * @param island
         * @param ignored
         */
        public abstract void updatetIgnoredTower(int island, boolean ignored);

        /**
         * updates the tower on an island
         * @param island
         * @param tower
         */
        public abstract void updaeTowerOnIsland(int island, String tower);

        /**
         * updates the student on the character card
         * @param students
         * @param cardId
         */
        public abstract void updatesStudentsOnCard(int cardId, ArrayList<StudentColor> students);

        /**
         * add students on cloud
         * @param students
         */
        public abstract void addStudentsOnCloud(ArrayList<StudentColor> students);

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
}

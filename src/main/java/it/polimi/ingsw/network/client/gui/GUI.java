package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.*;
import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.client.gui.controllers.NicknameController;
import it.polimi.ingsw.network.server.model.Island;
import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class GUI extends Application implements View {

    private Parent root;
    private static Stage guiStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        setStage(stage);

        stage.setTitle("Eriantys");
        stage.show();

        String message = "Insert nickname:";

        NicknameController nicknameController = new NicknameController(guiStage);
        nicknameController.initScene(message);
    }

    private void setStage(Stage stage) {
        guiStage = stage;
    }


    @Override
    public PlayerView getPlayer() {
        return null;
    }

    @Override
    public PlayerView getPlayerByNick(String nick) {
        return null;
    }

    @Override
    public void askNickName() {

    }

    @Override
    public void askActivateCharacterCard() {

    }

    @Override
    public void askCharacterCard() {

    }

    @Override
    public void askSetGameStatus() {

    }

    @Override
    public void askTower() {

    }

    @Override
    public void askDeck() {

    }

    @Override
    public void askSupportCard() {

    }

    @Override
    public void askMoveStudentsHToD() {

    }

    @Override
    public void askMoveStudentsHToI() {

    }

    @Override
    public void askCloud() {

    }

    @Override
    public void askMotherNatureMovements() {

    }

    @Override
    public void showString(String message) {

    }

    @Override
    public void showCharacterCard(int id) {

    }

    @Override
    public void showSupportCard(int id) {

    }

    @Override
    public void updateUsedSupportCard(int id) {

    }

    @Override
    public void updateAvailableSupportCards() {

    }

    @Override
    public void updateCharacterCardPrice(int id) {

    }

    @Override
    public void updateMotherPosition(int island) {

    }

    @Override
    public void updateTowerColor(String tower) {

    }

    @Override
    public void mergeIslands(int toBeMerged, int mergeTo) {

    }

    @Override
    public void addStudentsOnIsland(int island, ArrayList<StudentColor> students) {

    }

    @Override
    public void addStudentToPlayerD(String nick, ArrayList<StudentColor> students) {

    }

    @Override
    public void removeStudentsFromPlayerD(String nick, ArrayList<StudentColor> students) {

    }

    @Override
    public void showGameResults(ArrayList<String> winners, ArrayList<String> losers) {

    }

    @Override
    public void setAdditionalTurnOrder(int id, double additionalTurnOrder) {

    }

    @Override
    public void updatePlayerCoins(int coin) {

    }

    @Override
    public void updateGameStatus(int numOfPlayers, String mode) {

    }

    @Override
    public void updateCurrentPlayer(String currentPlayer) {

    }

    @Override
    public void updatePlayerOrder(ArrayList<String> playerOrder) {

    }

    @Override
    public void resumeFrom() {

    }

    @Override
    public void updateEmptyCloud(int cloud) {

    }

    @Override
    public void updateTowerOnIsland(int island, String tower) {

    }

    @Override
    public void addStudentsOnCloud(int cloud, ArrayList<StudentColor> students) {

    }

    @Override
    public void removeStudentsFromCloud(int cloud, ArrayList<StudentColor> students) {

    }

    @Override
    public void updateTeacher(HashMap<StudentColor, String> roles) {

    }

    @Override
    public void setPlayerDeck(String deck) {

    }

    @Override
    public void setSupportCard(int id) {

    }

    @Override
    public void removeBlockOnCard() {

    }

    @Override
    public void addBlockOnCard() {

    }

    @Override
    public void blockIsland(int island) {

    }

    @Override
    public void unlockIsland(int island) {

    }

    @Override
    public void addStudentsToHall(ArrayList<StudentColor> students) {

    }

    @Override
    public void removeStudentsFromHall(ArrayList<StudentColor> students) {

    }

    @Override
    public void addStudentsOnCard(int cardId, ArrayList<StudentColor> students) {

    }

    @Override
    public void removeStudentsFromCard(int cardId, ArrayList<StudentColor> students) {

    }

    @Override
    public String getCurrentPlayer() {
        return null;
    }

    @Override
    public ArrayList<IslandView> getAvailableIslands() {
        return null;
    }

    @Override
    public Client getClient() {
        return null;
    }

    @Override
    public void addAvailableCard(int card) {

    }

    @Override
    public int getChosenIsland() {
        return 0;
    }

    @Override
    public void disconnectFromServer() {

    }
}
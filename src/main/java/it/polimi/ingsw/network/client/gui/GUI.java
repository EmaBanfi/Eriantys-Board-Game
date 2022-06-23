package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.client.gui.controllers.GenericController;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GUI extends Application implements View {

    private final String introOfTheGame = "Intro.fxml";
    private final String startOfTheGame = "SetNickname.fxml";
    private final String setGameStatus = "SetGameStatus.fxml";
    private final HashMap<String, Scene> scenes = new HashMap<>();
    private final HashMap<String, GenericController> controllers = new HashMap<>();
    private Scene currentScene;
    private Stage stage;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
        client.receive();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        setup();
        this.stage = stage;
        run();
    }

    public void setup() {
        ArrayList<String> fxmList = new ArrayList<>(Arrays.asList(introOfTheGame, startOfTheGame, setGameStatus));

        for (String path : fxmList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));

            try {
                scenes.put(path, new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            GenericController controller = loader.getController();
            controller.setGui(this);
            controllers.put(path, controller);
        }

        currentScene = scenes.get(introOfTheGame);
    }

    public void changeStage(String newScene) {
        currentScene = scenes.get(newScene);
        stage.hide();
        stage.setScene(currentScene);
        stage.show();
    }

    public void run() {
        stage.setTitle("Eriantys");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/eriantys.jpg")));
        stage.show();
    }

    @Override
    public PlayerView getMainPlayer() {
        return null;
    }

    @Override
    public PlayerView getPlayerByNick(String nick) {
        return null;
    }

    @Override
    public void askNickName() {
        changeStage("SetNickname.fxml");
    }

    @Override
    public void askActivateCharacterCard() {

    }

    @Override
    public void askCharacterCard() {

    }

    @Override
    public void askSetGameStatus() {
        changeStage("SetGameStatus.fxml");
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
    public ArrayList<StudentColor> askStudentsFromHall(int numOfStudents, boolean showHall) {
        return null;
    }

    @Override
    public void askCloud() {

    }

    @Override
    public void askMotherNatureMovements() {

    }

    @Override
    public void showString(String message) {
        System.out.println("Show String: " + message);
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
    public void updateStudentsOnCard(int id, ArrayList<StudentColor> students, boolean add) {

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
    public void resumeFrom() {

    }

    @Override
    public void updateBlockOnCard(boolean add) {

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
    public void updateTeacher(HashMap<StudentColor, String> roles) {

    }

    @Override
    public void setPlayerDeck(String deck) {

    }

    @Override
    public void setSupportCard(int id) {

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
    public String getCurrentPlayer() {
        return null;
    }

    @Override
    public ArrayList<IslandView> getAvailableIslands() {
        return null;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void addAvailableCC(int card) {

    }

    @Override
    public void disconnectFromServer() {

    }

    @Override
    public void closeGame() {

    }

    @Override
    public void addPlayers(ArrayList<String> players) {

    }

    @Override
    public void removeFromPlayerHall(String nick, ArrayList<StudentColor> students) {

    }
}
package it.polimi.ingsw.network.client.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.client.ViewController;
import it.polimi.ingsw.network.client.clientModel.MotherNatureView;
import it.polimi.ingsw.network.client.gui.controllers.GenericController;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GUI extends Application implements View {

    private final String setIp = "SetIp.fxml";
    private final String setNickname = "SetNickname.fxml";
    private final String setGameStatus = "SetGameStatus.fxml";
    private final String showIslands = "Islands.fxml";
    private Scene currentScene;
    private Stage stage;
    private Client client;
    private final Gson gson = new Gson();
    private ViewController viewController;
    private ScenesDeck scenesDeck;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        viewController = new ViewController(this);
        scenesDeck = new ScenesDeck(this);
        this.stage = stage;
        startGame();
    }

    public void updateSceneOnStage(String scene) {
        currentScene = scenesDeck.getSceneManager(scene).getScene();
        Platform.runLater(() -> {
            stage.setScene(currentScene);
            stage.show();
        });
    }

    public void startGame() {
        stage.setTitle("Eriantys");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/eriantys.jpg")));
        updateSceneOnStage(setIp);
        stage.show();
    }

    public void setClient(String ip) {
        client = new Client(this, ip);
        client.start();
    }

    public Gson getGson() {
        return gson;
    }


    @Override
    public void askNickName() {
        updateSceneOnStage("SetNickname.fxml");
    }

    @Override
    public void askActivateCharacterCard() {

    }

    @Override
    public void askCharacterCard() {

    }

    @Override
    public ViewController getViewController(){return viewController;}

    @Override
    public void askSetGameStatus() {
        updateSceneOnStage("SetGameStatus.fxml");
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
        Platform.runLater(() -> AlertBox.display("Message from server", message));
    }

    @Override
    public void showCharacterCard(int id) {

    }

    @Override
    public void showSupportCard(int id) {

    }

    @Override
    public void updateCharacterCardPrice(int id) {

    }


    @Override
    public void showGameResults(ArrayList<String> winners, ArrayList<String> losers) {

    }


    @Override
    public void updateStudentsOnCard(int id, ArrayList<StudentColor> students, boolean add) {

    }


    @Override
    public void updateBlockOnCard(boolean add) {

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


    public MotherNatureView getMotherNature() {
        return viewController.getMotherNature();
    }

    public String getTeacherOfColor(StudentColor color) {
        return viewController.getTeacherOfColor(color);
    }
}
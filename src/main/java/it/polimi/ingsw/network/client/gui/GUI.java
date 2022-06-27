package it.polimi.ingsw.network.client.gui;

import com.google.gson.Gson;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.client.ViewController;
import it.polimi.ingsw.network.client.clientModel.MotherNatureView;
import it.polimi.ingsw.network.client.clientModel.Phase;
import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GUI extends Application implements View {

    private final String setIp = "SetIp.fxml";
    private final String setNickname = "SetNickname.fxml";
    private final String setGameStatus = "SetGameStatus.fxml";
    private final String showIslands = "ShowIslands.fxml";
    private final String setTower = "SetTower.fxml";
    private final String setDeck = "SetDeck.fxml";
    private final String wantHToI = "hToI/WantHToI.fxml";

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
        this.stage.setMinWidth(1200);
        this.stage.setMinHeight(800);
        this.stage.setResizable(false);
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
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/eriantys.jpg"))));
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
        viewController.setResumeFrom(Phase.CHOOSE_TOWER);

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
        viewController.setResumeFrom(Phase.CHOOSE_DECK);

        scenesDeck.getSceneManager(setTower).getController().update();
        updateSceneOnStage("SetTower.fxml");
    }

    @Override
    public void askDeck() {
        viewController.setResumeFrom(Phase.CHOOSE_SUPPORT_CARD);

        scenesDeck.getSceneManager(setDeck).getController().update();
        updateSceneOnStage("SetDeck.fxml");
    }

    @Override
    public void askSupportCard() {
        viewController.setResumeFrom(Phase.CHOOSE_STUDENTS_TO_ISLAND);
    }

    @Override
    public void askMoveStudentsHToD() {

    }

    @Override
    public void askMoveStudentsHToI() {
        viewController.setResumeFrom(Phase.CHOOSE_MOTHER_MOVEMENTS);

        updateSceneOnStage(wantHToI);
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
package it.polimi.ingsw.network.client.gui;

import com.google.gson.Gson;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.client.ViewController;
import it.polimi.ingsw.network.client.clientModel.MotherNatureView;
import it.polimi.ingsw.network.client.clientModel.Phase;
import it.polimi.ingsw.network.client.gui.controllers.GenericController;
import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GUI extends Application implements View {

    private final String notify = "Notify.fxml";
    private final String setIp = "SetIp.fxml";
    private final String setNickname = "SetNickname.fxml";
    private final String setGameStatus = "SetGameStatus.fxml";
    private final String showIslands = "ShowIslands.fxml";
    private final String setTower = "SetTower.fxml";
    private final String setDeck = "SetDeck.fxml";
    private final String wantHToI = "hToI/WantHToI.fxml";
    private final String moveHToI = "hToI/MoveHToI.fxml";
    private final String askSupportCard = "AskSupportCard.fxml";
    private final String moveHToD = "MoveHToD.fxml";
    private final String moveMother = "MoveMother.fxml";
    private final String chooseCloud = "ChooseCloud.fxml";
    private final String endGame = "EndGame.fxml";
    private final String gameBoard = "GameBoard.fxml";
    private final String closeGame = "CloseGame.fxml";

    private GenericController gameBoardController;
    private Scene currentScene;
    private Stage stage;
    private Client client;
    private final Gson gson = new Gson();
    private ViewController viewController;
    private ScenesDeck scenesDeck;
    private ArrayList<String> winners = new ArrayList<>();
    private ArrayList<String> losers = new ArrayList<>();
    private ArrayList<String> notifies = new ArrayList<>();
    private Stage window = new Stage();
    private GenericController notifyController;
    private boolean gBInitialized = false;

    private final ArrayList<CharacterCardGUI> availableCC = new ArrayList<>();
    private final ArrayList<Integer> usableCC = new ArrayList<>();
    private final CharacterCardCreatorGUI ccc = new CharacterCardCreatorGUI();
    private boolean usedCharacterCard;
    private Stage stageCC = new Stage();

    public static void main(String[] args) {
        launch();
    }

    public void setWinners(ArrayList<String> winners){
        this.winners.addAll(winners);
    }

    public void setLosers(ArrayList<String> losers){
        this.losers.addAll(losers);
    }

    public ArrayList<String> getWinners(){
        return winners;
    }

    public ArrayList<String> getLosers(){
        return losers;
    }

    @Override
    public void start(Stage stage) throws IOException {
        viewController = new ViewController(this);
        scenesDeck = new ScenesDeck(this);
        //window.initModality(Modality.APPLICATION_MODAL);
        //notifyController = getSceneManager(notify).getController();
        //notifyController.initialise();
        this.stage = stage;
        this.stage.setMinWidth(1200);
        this.stage.setMinHeight(800);
        this.stage.setResizable(false);


        this.stageCC.setMinWidth(1200);
        this.stageCC.setMinHeight(800);
        this.stageCC.setResizable(false);

        startGame();
    }

    public void updateSceneOnStage(String scene) {
        currentScene = scenesDeck.getSceneManager(scene).getScene();
        Platform.runLater(() -> {
            stage.setScene(currentScene);
            stage.show();
        });
    }

    public void updateSceneOnStageOnlyForCC(String scene) {
        currentScene = scenesDeck.getSceneManager(scene).getScene();
        Platform.runLater(() -> {
            stageCC.setScene(currentScene);
            stageCC.show();
        });
    }

    public Stage getStageCC() {
        return stageCC;
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

    private void initialiseGameBoardScene(){
        gameBoardController = getSceneManager(gameBoard).getController();
        gameBoardController.initialise();

        Platform.runLater(() -> {
            Stage stage1 = new Stage();
            stage1.setMinWidth(1200);
            stage1.setMinHeight(800);
            stage1.setResizable(false);
            stage1.setScene(getSceneManager(gameBoard).getScene());
            stage1.show();
        });
    }

    @Override
    public void askNickName() {
        viewController.setResumeFrom(Phase.CHOOSE_TOWER);

        updateSceneOnStage(setNickname);
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
        updateSceneOnStage(setGameStatus);
    }

    @Override
    public void askTower() {
        viewController.setResumeFrom(Phase.CHOOSE_DECK);
        scenesDeck.getSceneManager(setTower).getController().update();
        updateSceneOnStage(setTower);
    }

    @Override
    public void askDeck() {
        viewController.setResumeFrom(Phase.CHOOSE_SUPPORT_CARD);

        gameBoardController.update(viewController.getMainPlayer().getNickname(), ValueToUpdate.TOWER);

        scenesDeck.getSceneManager(setDeck).getController().update();
        updateSceneOnStage(setDeck);
    }

    @Override
    public void askSupportCard() {
        viewController.setResumeFrom(Phase.CHOOSE_STUDENTS_TO_ISLAND);
        scenesDeck.getSceneManager(askSupportCard).getController().update();
        updateSceneOnStage(askSupportCard);
    }

    @Override
    public void askMoveStudentsHToD() {
        viewController.setResumeFrom(Phase.CHOOSE_MOTHER_MOVEMENTS);

        scenesDeck.getSceneManager(moveHToD).getController().update();
        updateSceneOnStage(moveHToD);
    }

    @Override
    public void askMoveStudentsHToI() {
        viewController.setResumeFrom(Phase.CHOOSE_STUDENTS_TO_DINING_HALL);

        updateSceneOnStage(wantHToI);
    }

    @Override
    public ArrayList<StudentColor> askStudentsFromHall(int numOfStudents, boolean showHall) {
        return null;
    }

    @Override
    public void askCloud() {
        viewController.setResumeFrom(Phase.CHOOSE_SUPPORT_CARD);

        getSceneManager(chooseCloud).getController().update();
        updateSceneOnStage(chooseCloud);
    }

    @Override
    public void askMotherNatureMovements() {
        viewController.setResumeFrom(Phase.CHOOSE_CLOUDS);

        getSceneManager(moveMother).getController().update();
        updateSceneOnStage(moveMother);
    }

    @Override
    public void showString(String message) {
        Platform.runLater(() -> AlertBox.display("Message from server", message));
        /*
        notifies.add(message);

        if (!window.isShowing()) {
            Platform.runLater(() -> {
                window.setScene(getSceneManager(notify).getScene());
                window.showAndWait();
            });
        }

         */
    }

    public ArrayList<String> getNotifies() {
        return notifies;
    }

    public void emptyNotifies() {
        notifies.clear();
    }

    public void closeNotifyStage() {
        window.close();
    }

    @Override
    public void showCharacterCard(int id) {

    }

    @Override
    public void showSupportCard(int id) {
        gameBoardController.update(viewController.getCurrentPlayer(), ValueToUpdate.CARD);
    }

    @Override
    public void updateCharacterCardPrice(int id) {
        getCharacterCardById(id).increasePrice();
    }


    @Override
    public void showGameResults(ArrayList<String> winners, ArrayList<String> losers) {
        this.winners.addAll(winners);
        this.losers.addAll(losers);
        scenesDeck.getSceneManager(endGame).getController().update();
        updateSceneOnStage(endGame);
        systemExit(0);
    }


    @Override
    public void updateStudentsOnCard(int id, ArrayList<StudentColor> students, boolean add) {

    }


    @Override
    public void updateBlockOnCard(boolean add) {
        getCharacterCardById(5).updateAvailableBlockCards(add);
    }

    @Override
    public void updatePlayerCoins(int coins) {
        viewController.updatePlayerCoins(coins);
        gameBoardController.update(viewController.getCurrentPlayer(), ValueToUpdate.COINS);
    }


    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void addAvailableCC(int card) {
        availableCC.add(ccc.createCard(card, this));
    }

    @Override
    public void disconnectFromServer() {

    }

    @Override
    public void closeGame(String message, int exit) {
        scenesDeck.getSceneManager(closeGame).getController().setInput(message);
        scenesDeck.getSceneManager(closeGame).getController().setInput(exit);
        updateSceneOnStage(closeGame);
    }

    public void systemExit(int exit) {
        System.exit(exit);
    }

    @Override
    public void addStudentToPlayerD(String playerNick, ArrayList<StudentColor> students) {
        viewController.addStudentToPlayerD(playerNick,students);
        gameBoardController.update(playerNick, ValueToUpdate.DINING);
    }

    @Override
    public void removeStudentsFromPlayerD(String playerNick, ArrayList<StudentColor> students) {
        viewController.removeStudentsFromPlayerD(playerNick, students);
        gameBoardController.update(playerNick, ValueToUpdate.DINING);
    }

    @Override
    public void addStudentsToHall(ArrayList<StudentColor> students) {
        if (!gBInitialized) {
            initialiseGameBoardScene();
            gBInitialized = true;
        }

        viewController.addStudentsToHall(students);
        gameBoardController.update(viewController.getCurrentPlayer(), ValueToUpdate.HALL);

        if(viewController.getMainPlayer().getTower()!=null)
            gameBoardController.update(viewController.getMainPlayer().getNickname(), ValueToUpdate.TOWER);
    }

    @Override
    public void removeFromPlayerHall(String nick, ArrayList<StudentColor> students) {
        viewController.removeFromPlayerHall(nick,students);
        gameBoardController.update(nick,ValueToUpdate.HALL);
    }

    @Override
    public void removeStudentsFromHall(ArrayList<StudentColor> students) {
        viewController.removeStudentsFromHall(students);
        gameBoardController.update(viewController.getCurrentPlayer(), ValueToUpdate.HALL);
    }

    @Override
    public void updateTeacher(HashMap<StudentColor, String> roles) {
        viewController.updateTeacher(roles);
        gameBoardController.update(null, ValueToUpdate.TEACHERS);
    }

    @Override
    public void setPlayerDeck(String deck) {
        viewController.setPlayerDeck(deck);
        gameBoardController.update(viewController.getCurrentPlayer(), ValueToUpdate.DECK);
    }

    @Override
    public void updateTowerColor(String tower) {
        viewController.updateTowerColor(tower);

        gameBoardController.update(viewController.getCurrentPlayer(), ValueToUpdate.TOWER);
    }

    @Override
    public void updateGameStatus(int numOfPlayers, String mode) {
        viewController.updateGameStatus(numOfPlayers, mode);
    }

    public SceneManager getSceneManager(String sceneName){
        return scenesDeck.getSceneManager(sceneName);
    }


    public MotherNatureView getMotherNature() {
        return viewController.getMotherNature();
    }

    public String getTeacherOfColor(StudentColor color) {
        return viewController.getTeacherOfColor(color);
    }

    public void updateGameBoard(ValueToUpdate value) {
        gameBoardController.update(viewController.getMainPlayer().getNickname(), value);
    }

    public void updateGameBoardByNick(String nick, ValueToUpdate value) {
        gameBoardController.update(nick, value);
    }

    public void updateUsableCC(){
        usableCC.clear();
        for (CharacterCardGUI card : availableCC) {
            if (card.checkCCPrecondition()) {
                usableCC.add(card.getCardId());
            }
        }
    }

    public ArrayList<CharacterCardGUI> getAvailableCC(){return availableCC;}

    public ArrayList<Integer> getUsableCC() {
        return usableCC;
    }

    public CharacterCardGUI getCharacterCardById(int id) {
        for (CharacterCardGUI card : availableCC)
            if(card.getCardId()==id)
                return card;

        return null;
    }
}
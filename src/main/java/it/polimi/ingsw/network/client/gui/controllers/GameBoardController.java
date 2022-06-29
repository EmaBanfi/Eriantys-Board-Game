package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.ViewController;
import it.polimi.ingsw.network.client.gui.PlayerGui;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoardController extends GenericController{




    @FXML
    private Label pl1Tower;
    @FXML
    private Label pl1Deck;
    @FXML
    private Label pl1Remaining;
    @FXML
    private Label pl1Teachers;
    @FXML
    private Label pl1Hall;
    @FXML
    private Label pl1Dining;
    @FXML
    private Label pl1Coins;
    @FXML
    private ImageView pl1Card;


    @FXML
    private Tab player2Tab;
    @FXML
    private Label pl2Tower;
    @FXML
    private Label pl2Deck;
    @FXML
    private Label pl2Remaining;
    @FXML
    private Label pl2Teachers;
    @FXML
    private Label pl2Hall;
    @FXML
    private Label pl2Dining;
    @FXML
    private Label pl2Coins;
    @FXML
    private ImageView pl2Card;


    @FXML
    private Tab player3Tab;
    @FXML
    private Label pl3Tower;
    @FXML
    private Label pl3Deck;
    @FXML
    private Label pl3Remaining;
    @FXML
    private Label pl3Teachers;
    @FXML
    private Label pl3Hall;
    @FXML
    private Label pl3Dining;
    @FXML
    private Label pl3Coins;
    @FXML
    private ImageView pl3Card;


    @FXML
    private Tab player4Tab;
    @FXML
    private Label pl4Tower;
    @FXML
    private Label pl4Deck;
    @FXML
    private Label pl4Remaining;
    @FXML
    private Label pl4Teachers;
    @FXML
    private Label pl4Hall;
    @FXML
    private Label pl4Dining;
    @FXML
    private Label pl4Coins;
    @FXML
    private ImageView pl4Card;

    @FXML
    private TabPane playersPane;

    private ViewController viewController;

    private HashMap<String,PlayerGui> players;

    @Override
    public void initialise(){
        players = new HashMap<>();
        viewController = getGui().getViewController();
        int numOfPlayers = viewController.getNumOfPlayers();
        String mode = getGui().getViewController().getMode();

        if(mode.equals("normal")){
            pl1Coins.setVisible(false);
            pl2Coins.setVisible(false);
            pl3Coins.setVisible(false);
            pl4Coins.setVisible(false);
        }

        players.put(
                viewController.getMainPlayer().getNickname(),
                new PlayerGui(
                        pl1Tower,
                        pl1Deck,
                        pl1Remaining,
                        pl1Teachers,
                        pl1Hall,
                        pl1Dining,
                        pl1Coins,
                        pl1Card,
                        viewController.getMainPlayer()
                )
        );

        player2Tab.setText(viewController.getNickByIndex(2));
        players.put(
                viewController.getNickByIndex(2),
                new PlayerGui(
                        pl2Tower,
                        pl2Deck,
                        pl2Remaining,
                        pl2Teachers,
                        pl2Hall,
                        pl2Dining,
                        pl2Coins,
                        pl2Card,
                        viewController.getPlayerByIndex(2)
                )
        );

        if(numOfPlayers>=3){
            player3Tab.setText(viewController.getNickByIndex(3));
            players.put(
                    viewController.getNickByIndex(3),
                    new PlayerGui(
                            pl3Tower,
                            pl3Deck,
                            pl3Remaining,
                            pl3Teachers,
                            pl3Hall,
                            pl3Dining,
                            pl3Coins,
                            pl3Card,
                            viewController.getPlayerByIndex(3)
                    )
            );

            if(numOfPlayers==4){
                player3Tab.setText(viewController.getNickByIndex(4));
                players.put(
                        viewController.getNickByIndex(4),
                        new PlayerGui(
                                pl4Tower,
                                pl4Deck,
                                pl4Remaining,
                                pl4Teachers,
                                pl4Hall,
                                pl4Dining,
                                pl4Coins,
                                pl4Card,
                                viewController.getPlayerByIndex(4)
                        )
                );
            }
            else{
                playersPane.getTabs().remove(3);
            }
        }
        else {
            playersPane.getTabs().remove(3);
            playersPane.getTabs().remove(2);
        }

    }

    @Override
    public void update(String nick, ValueToUpdate value){
        switch (value){
            case CARD -> players.get(nick).updateCard();
            case TEACHERS ->  updateTeachers();
            case HALL -> players.get(nick).updateHall();
            case DINING -> players.get(nick).updateDiningHall();
            case COINS -> players.get(nick).updateCoins();
            case DECK -> players.get(nick).initDeck();
            case TOWER -> players.get(nick).initTower();
        }
    }

    public void updateTeachers(){
        StringBuilder text ;
        HashMap<String, ArrayList<StudentColor>> roles = new HashMap<>();
        for(String nick: players.keySet()){
            roles.put(nick, new ArrayList<>());
        }
        String teacher;
        for (StudentColor color: StudentColor.values()){
            teacher = viewController.getTeacherOfColor(color);
            if(!teacher.equals("yet to be decided"))
                roles.get(teacher).add(color);
        }
        for(String nick : players.keySet()){
            text = new StringBuilder();
            text.append("Teachers controlled: ");
            boolean first = true;
            for(StudentColor color: roles.get(nick)){
                if(first)
                    first=false;
                else
                    text.append(",");
                text.append(" ").append(color.toString());
            }
            players.get(nick).updateTeachers(text.toString());
        }
    }
}


package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.client.clientModel.MotherNatureView;

import it.polimi.ingsw.network.client.gui.IslandGui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;



public class ShowIslandsController extends GenericController{

    @FXML private AnchorPane island1;
    @FXML private Label blue1;
    @FXML private Label purple1;
    @FXML private Label yellow1;
    @FXML private Label red1;
    @FXML private Label green1;
    @FXML private ImageView tower1;
    @FXML private ImageView block1;
    @FXML private ImageView nature1;

   @FXML private AnchorPane island2;
   @FXML private Label blue2;
   @FXML private Label purple2;
   @FXML private Label yellow2;
   @FXML private Label red2;
   @FXML private Label green2;
   @FXML private ImageView tower2;
   @FXML private ImageView block2;
   @FXML private ImageView nature2;

    @FXML private AnchorPane island3;
    @FXML private Label blue3;
   @FXML private Label purple3;
   @FXML private Label yellow3;
   @FXML private Label red3;
   @FXML private Label green3;
   @FXML private ImageView tower3;
   @FXML private ImageView block3;
   @FXML private ImageView nature3;

    @FXML private AnchorPane island4;
    @FXML private Label blue4;
   @FXML private Label purple4;
   @FXML private Label yellow4;
   @FXML private Label red4;
   @FXML private Label green4;
   @FXML private ImageView tower4;
   @FXML private ImageView block4;
   @FXML private ImageView nature4;

    @FXML private AnchorPane island5;
    @FXML private Label blue5;
   @FXML private Label purple5;
   @FXML private Label yellow5;
   @FXML private Label red5;
   @FXML private Label green5;
   @FXML private ImageView tower5;
   @FXML private ImageView block5;
   @FXML private ImageView nature5;

    @FXML private AnchorPane island6;
    @FXML private Label blue6;
    @FXML private Label purple6;
    @FXML private Label yellow6;
    @FXML private Label red6;
    @FXML private Label green6;
    @FXML private ImageView tower6;
    @FXML private ImageView block6;
    @FXML private ImageView nature6;

    @FXML private AnchorPane island7;
    @FXML private Label blue7;
    @FXML private Label purple7;
    @FXML private Label yellow7;
    @FXML private Label red7;
    @FXML private Label green7;
    @FXML private ImageView tower7;
    @FXML private ImageView block7;
    @FXML private ImageView nature7;

    @FXML private AnchorPane island8;
    @FXML private Label blue8;
    @FXML private Label purple8;
    @FXML private Label yellow8;
    @FXML private Label red8;
    @FXML private Label green8;
    @FXML private ImageView tower8;
    @FXML private ImageView block8;
    @FXML private ImageView nature8;

    @FXML private AnchorPane island9;
    @FXML private Label blue9;
    @FXML private Label purple9;
    @FXML private Label yellow9;
    @FXML private Label red9;
    @FXML private Label green9;
    @FXML private ImageView tower9;
    @FXML private ImageView block9;
    @FXML private ImageView nature9;
    
    @FXML private AnchorPane island10;
    @FXML private Label blue10;
    @FXML private Label purple10;
    @FXML private Label yellow10;
    @FXML private Label red10;
    @FXML private Label green10;
    @FXML private ImageView tower10;
    @FXML private ImageView block10;
    @FXML private ImageView nature10;
    
    @FXML private AnchorPane island11;
    @FXML private Label blue11;
    @FXML private Label purple11;
    @FXML private Label yellow11;
    @FXML private Label red11;
    @FXML private Label green11;
    @FXML private ImageView tower11;
    @FXML private ImageView block11;
    @FXML private ImageView nature11;
    
    @FXML private AnchorPane island12;
    @FXML private Label blue12;
    @FXML private Label purple12;
    @FXML private Label yellow12;
    @FXML private Label red12;
    @FXML private Label green12;
    @FXML private ImageView tower12;
    @FXML private ImageView block12;
    @FXML private ImageView nature12;


    private ArrayList<IslandView> islands;
    private MotherNatureView mother;
    private ArrayList<AnchorPane> islandsPanes;
    private ArrayList<IslandGui> guiIslands;


    @Override
    public  void initialise(){
        islandsPanes = new ArrayList<>();
        islandsPanes.add(island1);
        islandsPanes.add(island2);
        islandsPanes.add(island3);
        islandsPanes.add(island4);
        islandsPanes.add(island5);
        islandsPanes.add(island6);
        islandsPanes.add(island7);
        islandsPanes.add(island8);
        islandsPanes.add( island9);
        islandsPanes.add(island10);
        islandsPanes.add(island11);
        islandsPanes.add(island12);

        islands = getGui().getViewController().getAvailableIslands();
        mother = getGui().getMotherNature();

        guiIslands = new ArrayList<>();
        guiIslands.add(
            new IslandGui(
                    islands.get(0),
                    blue1,
                    purple1,
                    yellow1,
                    red1,
                    green1,
                    tower1,
                    block1,
                    nature1
            )
        );
        guiIslands.add(
                new IslandGui(
                        islands.get(1),
                        blue2,
                        purple2,
                        yellow2,
                        red2,
                        green2,
                        tower2,
                        block2,
                        nature2
                )
        );
        guiIslands.add(
                new IslandGui(
                        islands.get(2),
                        blue3,
                        purple3,
                        yellow3,
                        red3,
                        green3,
                        tower3,
                        block3,
                        nature3
                )
        );
        guiIslands.add(
                new IslandGui(
                        islands.get(3),
                        blue4,
                        purple4,
                        yellow4,
                        red4,
                        green4,
                        tower4,
                        block4,
                        nature4
                )
        );
        guiIslands.add(
                new IslandGui(
                        islands.get(4),
                        blue5,
                        purple5,
                        yellow5,
                        red5,
                        green5,
                        tower5,
                        block5,
                        nature5
                )
        );
        guiIslands.add(
                new IslandGui(
                        islands.get(5),
                        blue6,
                        purple6,
                        yellow6,
                        red6,
                        green6,
                        tower6,
                        block6,
                        nature6
                )
        );
        guiIslands.add(
            new IslandGui(
                islands.get(6),
                blue7,
                purple7,
                yellow7,
                red7,
                green7,
                tower7,
                block7,
                nature7
            )
        );
        guiIslands.add(
            new IslandGui(
                islands.get(7),
                blue8,
                purple8,
                yellow8,
                red8,
                green8,
                tower8,
                block8,
                nature8
            )
        );
        guiIslands.add(
            new IslandGui(
                islands.get(8),
                blue9,
                purple9,
                yellow9,
                red9,
                green9,
                tower9,
                block9,
                nature9
            )
        );
        guiIslands.add(
            new IslandGui(
                islands.get(9),
                blue10,
                purple10,
                yellow10,
                red10,
                green10,
                tower10,
                block10,
                nature10
            )
        );guiIslands.add(
            new IslandGui(
                islands.get(10),
                blue11,
                purple11,
                yellow11,
                red11,
                green11,
                tower11,
                block11,
                nature11
            )
        );
        guiIslands.add(
            new IslandGui(
                islands.get(11),
                blue12,
                purple12,
                yellow12,
                red12,
                green12,
                tower12,
                block12,
                nature12
            )
        );
    }

    @Override
    public void update(){
        getGui().showIslands();
        int islandSize = islandsPanes.size();
        while (islandSize>islands.size()){
            islandsPanes.remove(islandSize-1);
            guiIslands.remove(islandSize-1);
            islandSize--;
        }
        for(int i=0; i<islands.size(); i++){
            guiIslands.get(i).setMotherNature(mother.getCurrentIsland()==i);
            guiIslands.get(i).updateIsland();
        }
    }
}

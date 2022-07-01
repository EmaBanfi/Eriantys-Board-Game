package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Objects;

public class IslandGui {

    private Label blueStudents;
    private Label purpleStudents;
    private Label yellowStudents;
    private Label redStudents;
    private Label greenStudents;
    private ImageView tower;
    private ImageView block;
    private ImageView motherNature;
    private String towerColor;
    private HashMap<StudentColor,Label> students;
    private IslandView islandView;


    public IslandGui(IslandView islandView, Label blueStudents, Label purpleStudents, Label yellowStudents, Label redStudents, Label greenStudents, ImageView tower, ImageView block, ImageView motherNature) {
        this.islandView = islandView;
        this.blueStudents = blueStudents;
        this.purpleStudents = purpleStudents;
        this.yellowStudents = yellowStudents;
        this.redStudents = redStudents;
        this.greenStudents = greenStudents;
        this.tower = tower;
        this.block = block;
        this.motherNature = motherNature;
        towerColor = null;
        students = new HashMap<>();
        students.put(StudentColor.BLUE, blueStudents);
        students.put(StudentColor.PURPLE, purpleStudents);
        students.put(StudentColor.YELLOW, yellowStudents);
        students.put(StudentColor.RED, redStudents);
        students.put(StudentColor.GREEN, greenStudents);
    }

    private void setBlockOnCard(){
        boolean blockOnIsland = islandView.getBlockCard();
        if(block.isVisible()!=blockOnIsland) {
            Platform.runLater(()-> block.setVisible(blockOnIsland));
        }
    }

    public void setMotherNature(boolean motherIsHere){
        if(motherNature.isVisible()!=motherIsHere) {
            Platform.runLater(()-> motherNature.setVisible(motherIsHere));
        }
    }

    private void updateStudents(){
        Platform.runLater(()->{
            for(StudentColor color: StudentColor.values() ){
                students.get(color).setText(""+islandView.getStudentsByColor(color));
            }
        });
    }

    private void updateTower(){
        String tc = islandView.getTower();
        if(tc!=null) {
            if (this.towerColor == null || !towerColor.equals(tc)) {
                this.towerColor = tc;
                Platform.runLater(() -> tower.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/towers/" + towerColor.toLowerCase() + ".png")))));
            }
        }
    }

    public void updateIsland(){
        updateStudents();
        updateTower();
        setBlockOnCard();
    }


}

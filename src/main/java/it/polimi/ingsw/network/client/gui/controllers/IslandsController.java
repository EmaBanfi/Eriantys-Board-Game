package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.ViewController;
import it.polimi.ingsw.network.client.clientModel.IslandView;
import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;


public class IslandsController extends GenericController{

    @FXML
    private Label islands;

    @Override
    public void setInput(Integer value) {
        showIslands(value);
    }

    public void showIslands(Integer range){
        GUI gui = getGui();
        int numOfIslands = gui.getViewController().getNumOfAvailableIslands();
        StringBuilder text = new StringBuilder();
        ViewController c = gui.getViewController();
        text.append("Mother Nature is on island ").append(c.getMotherPosition() + 1).append("\n");
        text.append(c.getMainPlayer().getNickname()).append("'s tower is ").append(c.getMainPlayer().getTower()).append("\n");
        for(PlayerView player : c.getPlayers()){
            text.append(player.getNickname()).append("'s tower is ").append(player.getTower()).append("\n");
        }
        System.out.println();
        for(StudentColor color: StudentColor.values()){
            text.append("The ").append(color.toString().toLowerCase()).append(" teacher is ");
            text.append(c.getTeacherOfColor(color));
            text.append("\n");
        }
        int from = 0;
        int to = c.getAvailableIslands().size();

        if (range != null) {
            from = c.getMotherPosition();
            if (from + range< numOfIslands) {
                to = from + range + 1;
                setRange(from, to, c.getAvailableIslands(),text, true);
            }
            else {
                setRange(from, to, c.getAvailableIslands(),text, false);
                setRange(0, (from + range) % numOfIslands+1, c.getAvailableIslands(), text, true);
            }
        }
        else {
            setRange(from,to, c.getAvailableIslands(), text,true);
        }
    }

    /**
     * set the values to pass to showIsland() so that only 5 island maxRange island are shown on the same line
     * @param from first island to be shown
     * @param to first island that will not be shown
     */
    private void setRange(int from, int to, ArrayList<IslandView> availableIslands, StringBuilder text, boolean show){
        int maxRange=5;
        while(to-from>maxRange){
            showRange(from,from+maxRange, availableIslands,text, false);
            from=from+maxRange;
        }
        showRange(from,to, availableIslands,text,show);
    }

    private void showRange(int from, int to, ArrayList<IslandView> availableIslands,StringBuilder text, boolean show) {
        int numOfSpaces=9;
        int maxSegmentLength=23;
        String segment;
        for(int i= from; i <to; i++) {
            segment = "Students on island " + (i+1);
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        text.append("\n");
        for(StudentColor color: StudentColor.values()) {
            text=new StringBuilder();
            for (int i = from; i < to; i++) {
                segment = color.toString().toLowerCase() + " students: " + availableIslands.get(i).getStudentsByColor(color);
                text.append(segment);
                text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
            }
            text.append("\n");
        }
        for(int i= from; i < to; i++){
            segment="Block on island: " + availableIslands.get(i).getBlockCard();
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        text.append("\n");
        for(int i= from; i <to; i++){
            if (availableIslands.get(i).getTower() == null)
                segment="No tower on island";
            else
                segment="Tower on island: " + availableIslands.get(i).getTower();
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        text.append("\n");
        for(int i= from; i < to; i++){
            if(availableIslands.get(i).getTower()!=null)
                segment="Num of towers: " + availableIslands.get(i).getNumOfTowers();
            else
                segment="";
            text.append(segment);
            text.append(" ".repeat(Math.max(0, (numOfSpaces + (maxSegmentLength - segment.length())) - 1)));
        }
        text.append("\n");

        if(show)
            islands.setText(text.toString());
    }



}

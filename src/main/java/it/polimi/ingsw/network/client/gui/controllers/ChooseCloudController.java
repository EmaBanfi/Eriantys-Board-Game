package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmCloud;

import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class ChooseCloudController extends GenericController {

    @FXML
    private ImageView cloud00;
    @FXML
    private ImageView image0Pos00;
    @FXML
    private ImageView image0Pos10;
    @FXML
    private ImageView image0Pos01;
    @FXML
    private ImageView image0Pos11;
    @FXML
    private Button cloudButton00;

    @FXML
    private ImageView cloud10;
    @FXML
    private ImageView image1Pos00;
    @FXML
    private ImageView image1Pos10;
    @FXML
    private ImageView image1Pos01;
    @FXML
    private ImageView image1Pos11;
    @FXML
    private Button cloudButton10;

    @FXML
    private ImageView cloud01;
    @FXML
    private VBox vBox01;
    @FXML
    private ImageView image2Pos00;
    @FXML
    private ImageView image2Pos10;
    @FXML
    private ImageView image2Pos01;
    @FXML
    private ImageView image2Pos11;
    @FXML
    private Button cloudButton01;

    @FXML
    private ImageView cloud11;
    @FXML
    private VBox vBox11;
    @FXML
    private ImageView image3Pos00;
    @FXML
    private ImageView image3Pos10;
    @FXML
    private ImageView image3Pos01;
    @FXML
    private ImageView image3Pos11;
    @FXML
    private Button cloudButton11;

    private ArrayList<StudentColor> students = new ArrayList<>();
    private String pathToStudentImage = "/images/student/";


    @Override
    public void update() {
        int numOfPlayers = getGui().getViewController().getNumOfPlayers();
        if (numOfPlayers >= 3) {
            cloud01.setVisible(true);
            vBox01.setVisible(true);
            cloudButton01.setVisible(true);
        }

        if (numOfPlayers == 4) {
            cloud11.setVisible(true);
            vBox11.setVisible(true);
            cloudButton11.setVisible(true);
        }

        if (numOfPlayers == 3) {
            fillCloud(0, image0Pos00, image0Pos10, image0Pos01, image0Pos11);
            fillCloud(1, image1Pos00, image1Pos10, image1Pos01, image1Pos11);
            fillCloud(2, image2Pos00, image2Pos10, image2Pos01, image2Pos11);
        }
        else {
            fillCloud(0, image0Pos00, image0Pos10, image0Pos01);
            fillCloud(1, image1Pos00, image1Pos10, image1Pos01);
            if (numOfPlayers == 4) {
                fillCloud(2, image2Pos00, image2Pos10, image2Pos01, image2Pos11);
                fillCloud(3, image3Pos00, image3Pos10, image3Pos01, image3Pos11);
            }
        }
    }

    private void fillCloud(int cloud, ImageView image00, ImageView image10, ImageView image01) {
        students.addAll(getGui().getViewController().getAvailableClouds().get(cloud).getStudents());

        image00.setImage(new Image(getClass().getResource(pathToStudentImage + students.get(0).toString().toLowerCase() + ".png").toExternalForm()));
        students.remove(0);

        image10.setImage(new Image(getClass().getResource(pathToStudentImage + students.get(0).toString().toLowerCase() + ".png").toExternalForm()));
        students.remove(0);

        image01.setImage(new Image(getClass().getResource(pathToStudentImage + students.get(0).toString().toLowerCase() + ".png").toExternalForm()));
        students.remove(0);
    }

    private void fillCloud(int cloud, ImageView image00, ImageView image10, ImageView image01, ImageView image11) {
        fillCloud(cloud, image00, image10, image01);

        image11.setImage(new Image(getClass().getResource(pathToStudentImage + students.get(0).toString().toLowerCase() + ".png").toExternalForm()));
        students.remove(0);
    }

    private Image getImage() {
        InputStream stream = null;
        try {
            stream = new FileInputStream(pathToStudentImage + students.get(0).toString().toLowerCase() + ".png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(stream);

        return image;
    }

    @FXML
    public void chooseCloud1() {
        getGui().getViewController().getMainPlayer().addToHall(getGui().getViewController().getCloud(0).getStudents());
        getGui().getViewController().getCloud(0).removeStudents();

        cmCloud message = new cmCloud(0);
        getGui().getClient().send(getGui().getGson().toJson(message, cmCloud.class));
    }

    @FXML
    public void chooseCloud2() {
        getGui().getViewController().getMainPlayer().addToHall(getGui().getViewController().getCloud(1).getStudents());
        getGui().getViewController().getCloud(1).removeStudents();

        cmCloud message = new cmCloud(1);
        getGui().getClient().send(getGui().getGson().toJson(message, cmCloud.class));
    }

    @FXML
    public void chooseCloud3() {
        getGui().getViewController().getMainPlayer().addToHall(getGui().getViewController().getCloud(2).getStudents());
        getGui().getViewController().getCloud(2).removeStudents();

        cmCloud message = new cmCloud(2);
        getGui().getClient().send(getGui().getGson().toJson(message, cmCloud.class));
    }

    @FXML
    public void chooseCloud4() {
        getGui().getViewController().getMainPlayer().addToHall(getGui().getViewController().getCloud(3).getStudents());
        getGui().getViewController().getCloud(3).removeStudents();

        cmCloud message = new cmCloud(3);
        getGui().getClient().send(getGui().getGson().toJson(message, cmCloud.class));
    }
}

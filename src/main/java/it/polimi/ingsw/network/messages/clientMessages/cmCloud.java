package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmCloud extends ClientMessage{

    private int cloud;

    public cmCloud() {
    }

    public cmCloud(int cloud) {
        this.cloud = cloud;
        setType("cloud");
    }

}

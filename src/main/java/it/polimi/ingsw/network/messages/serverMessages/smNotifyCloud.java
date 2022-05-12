package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyCloud extends ServerMessage {

    private int cloud;

    public smNotifyCloud() {
    }

    public smNotifyCloud(String message, int cloud) {
        super(message);
        this.cloud = cloud;
        setType("notify cloud");
    }

}

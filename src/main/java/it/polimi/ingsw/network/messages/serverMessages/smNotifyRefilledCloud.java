package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyRefilledCloud extends ServerMessage{

    private String cloud;

    public smNotifyRefilledCloud() {
    }

    public smNotifyRefilledCloud(String message, String cloud) {
        super(message);
        this.cloud = cloud;
        setType("notify refilled cloud");
    }

}

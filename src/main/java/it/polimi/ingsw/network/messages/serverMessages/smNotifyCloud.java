package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyCloud extends ServerMessage{
    int cloud;

    public smNotifyCloud(int cloud) {
        this.cloud = cloud;
    }
}

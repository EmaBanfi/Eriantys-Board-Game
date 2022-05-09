package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class smNotifyRefilledCloud extends ServerMessage{

    public smNotifyRefilledCloud() {
        super();
    }

    public smNotifyRefilledCloud(String cloud) {
        super(cloud);
    }

}

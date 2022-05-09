package it.polimi.ingsw.network.messages.serverMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SmGson {
    private final Gson gson;

    public SmGson(){
        gson =new GsonBuilder().registerTypeAdapter(ServerMessage.class,new ServerMessageDeserializer()).create();
    }

    public ServerMessage deserialize(String Json){
        return gson.fromJson(Json,ServerMessage.class);
    }

}

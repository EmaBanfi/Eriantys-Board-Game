package it.polimi.ingsw.communicationProtocolTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class smGson {
    Gson gson;
    public smGson(){
        gson=new GsonBuilder().registerTypeAdapter(ServerMessage.class,new MessageDeserializer()).create();
    }

    public ServerMessage deserialize(String Json){
        return gson.fromJson(Json,ServerMessage.class);
    }
}

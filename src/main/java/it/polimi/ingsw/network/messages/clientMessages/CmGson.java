package it.polimi.ingsw.network.messages.clientMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CmGson {

    private final Gson gson;

    public CmGson(){
        gson=new GsonBuilder().registerTypeAdapter(ClientMessage.class,new ClientMessageDeserializer()).create();
    }

    public ClientMessage deserialize(String Json){
        return gson.fromJson(Json,ClientMessage.class);
    }

}

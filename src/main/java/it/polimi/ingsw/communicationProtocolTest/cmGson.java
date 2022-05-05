package it.polimi.ingsw.communicationProtocolTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class cmGson {
    Gson gson;
    public cmGson(){
        gson=new GsonBuilder().registerTypeAdapter(ClientMessage.class,new ClientMessageDeserializer()).create();
    }

    public ClientMessage deserialize(String Json){
        return gson.fromJson(Json,ClientMessage.class);
    }
}

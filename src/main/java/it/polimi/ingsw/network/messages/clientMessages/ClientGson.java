package it.polimi.ingsw.network.messages.clientMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientGson {

    private final Gson gson;

    public ClientGson(){
        gson=new GsonBuilder().registerTypeAdapter(ClientMessage.class,new ClientMessageDeserializer()).create();
    }

    public ClientMessage deserialize(String Json){
        return gson.fromJson(Json,ClientMessage.class);
    }

}

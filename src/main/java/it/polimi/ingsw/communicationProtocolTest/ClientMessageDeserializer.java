package it.polimi.ingsw.communicationProtocolTest;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ClientMessageDeserializer implements JsonDeserializer<ClientMessage> {
    @Override
    public ClientMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement field = jsonObject.get("type");
        if(field!=null){
            switch (field.getAsString()){

            }
        }
        return null;
    }
}

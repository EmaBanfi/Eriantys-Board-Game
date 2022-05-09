package it.polimi.ingsw.network.messages.clientMessages;

import com.google.gson.*;
import it.polimi.ingsw.network.messages.serverMessages.smLoginFailedMessage;

import java.lang.reflect.Type;

public class ClientMessageDeserializer implements JsonDeserializer<ClientMessage> {
    @Override
    public ClientMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement field = jsonObject.get("type");
        if(field != null){
            switch (field.getAsString()){
                case "nickname":
                    return jsonDeserializationContext.deserialize(jsonObject, cmNickname.class);
                case "set status":
                    return jsonDeserializationContext.deserialize(jsonObject, cmSetGameStatus.class);
            }
        }
        return null;
    }
}

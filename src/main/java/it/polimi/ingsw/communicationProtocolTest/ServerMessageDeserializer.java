package it.polimi.ingsw.communicationProtocolTest;
import com.google.gson.*;


import java.lang.reflect.Type;

public class ServerMessageDeserializer implements JsonDeserializer<ServerMessage> {


    @Override
    public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement field = jsonObject.get("type");
        if(field!=null){
            switch (field.getAsString()){
                case "message":
                    return jsonDeserializationContext.deserialize(jsonObject,Message.class);
                case "login failed":
                    return jsonDeserializationContext.deserialize(jsonObject,LoginFailedMessage.class);
                case "notify":
                    return jsonDeserializationContext.deserialize(jsonObject,Notify.class);
            }
        }
        return null;
    }

}

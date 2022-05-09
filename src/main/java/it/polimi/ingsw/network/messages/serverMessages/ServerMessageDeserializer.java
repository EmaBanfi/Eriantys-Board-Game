package it.polimi.ingsw.network.messages.serverMessages;

import com.google.gson.*;


import java.lang.reflect.Type;

public class ServerMessageDeserializer implements JsonDeserializer<ServerMessage> {


    @Override
    public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement field = jsonObject.get("type");
        if(field!=null){
            switch (field.getAsString()){
                case "login failed":
                    return jsonDeserializationContext.deserialize(jsonObject, smLoginFailedMessage.class);
                case "notify":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotify.class);
                case "ask nickname":
                    return jsonDeserializationContext.deserialize(jsonObject, smAskNickname.class);
                case "ask status":
                    return jsonDeserializationContext.deserialize(jsonObject, smAskGameStatus.class);
                case "notify used deck":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyUsedDeck.class);
                case "notify chosen tower":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyChosenTower.class);
                case "notify refilled cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyRefilledCloud.class);
            }
        }
        return null;
    }

}

package it.polimi.ingsw.network.messages.clientMessages;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ClientMessageDeserializer implements JsonDeserializer<ClientMessage> {

    @Override
    public ClientMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement field = jsonObject.get("type");

        if (field != null)
            switch (field.getAsString()){
                case "nickname":
                    return jsonDeserializationContext.deserialize(jsonObject, cmNickname.class);
                case "set status":
                    return jsonDeserializationContext.deserialize(jsonObject, cmSetGameStatus.class);
                case "deck":
                    return jsonDeserializationContext.deserialize(jsonObject, cmDeck.class);
                case "tower":
                    return jsonDeserializationContext.deserialize(jsonObject, cmTower.class);
                case "support card":
                    return jsonDeserializationContext.deserialize(jsonObject, cmSupportCard.class);
                case "H to D":
                    return jsonDeserializationContext.deserialize(jsonObject, cmStudentsMovementsHToD.class);
                case "H to I":
                    return jsonDeserializationContext.deserialize(jsonObject, cmStudentsMovementsHToI.class);
                case "move mother":
                    return jsonDeserializationContext.deserialize(jsonObject, cmMoveMother.class);
                case "CCG1":
                    return jsonDeserializationContext.deserialize(jsonObject, cmCCG1.class);
                case "CCG2":
                    return jsonDeserializationContext.deserialize(jsonObject, cmCCG2.class);
                case "CCG3":
                    return jsonDeserializationContext.deserialize(jsonObject, cmCCG3.class);
                case "CCG4":
                    return jsonDeserializationContext.deserialize(jsonObject, cmCCG4.class);
                case "CCG5":
                    return jsonDeserializationContext.deserialize(jsonObject, cmCCG5.class);
                case "CCG6":
                    return jsonDeserializationContext.deserialize(jsonObject, cmCCG6.class);
                case "cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, cmCloud.class);
                case "refill request":
                    return jsonDeserializationContext.deserialize(jsonObject, cmRefillRequest.class);
            }

        return null;
    }

}

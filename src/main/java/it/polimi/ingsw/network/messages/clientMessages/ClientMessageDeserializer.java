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
                    return jsonDeserializationContext.deserialize(jsonObject, Nickname.class);
                case "set status":
                    return jsonDeserializationContext.deserialize(jsonObject, SetGameStatus.class);
                case "deck":
                    return jsonDeserializationContext.deserialize(jsonObject, Deck.class);
                case "tower":
                    return jsonDeserializationContext.deserialize(jsonObject, Tower.class);
                case "support card":
                    return jsonDeserializationContext.deserialize(jsonObject, cmSupportCard.class);
                case "H to D":
                    return jsonDeserializationContext.deserialize(jsonObject, StudentsMovementsHToD.class);
                case "H to I":
                    return jsonDeserializationContext.deserialize(jsonObject, StudentsMovementsHToI.class);
                case "move mother":
                    return jsonDeserializationContext.deserialize(jsonObject, MoveMother.class);
                case "CCG1":
                    return jsonDeserializationContext.deserialize(jsonObject, CCG1.class);
                case "CCG2":
                    return jsonDeserializationContext.deserialize(jsonObject, CCG2.class);
                case "CCG3":
                    return jsonDeserializationContext.deserialize(jsonObject, CCG3.class);
                case "CCG4":
                    return jsonDeserializationContext.deserialize(jsonObject, CCG4.class);
                case "CCG5":
                    return jsonDeserializationContext.deserialize(jsonObject, CCG5.class);
                case "CCG6":
                    return jsonDeserializationContext.deserialize(jsonObject, CCG6.class);
                case "cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, Cloud.class);
                case "refill request":
                    return jsonDeserializationContext.deserialize(jsonObject, RefillRequest.class);
            }

        return null;
    }

}

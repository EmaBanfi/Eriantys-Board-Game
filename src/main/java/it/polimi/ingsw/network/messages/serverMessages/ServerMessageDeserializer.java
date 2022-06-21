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
                case "add players":
                    return jsonDeserializationContext.deserialize(jsonObject, smAddPlayers.class);
                case "notify":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotify.class);
                case "nickname":
                    return jsonDeserializationContext.deserialize(jsonObject, smAskNickname.class);
                case "status":
                    return jsonDeserializationContext.deserialize(jsonObject, smAskGameStatus.class);
                case "used deck":
                    return jsonDeserializationContext.deserialize(jsonObject, smUsedDeck.class);
                case "chosen tower":
                    return jsonDeserializationContext.deserialize(jsonObject, smChosenTower.class);
                case "students on cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, smStudentsOnCloud.class);
                case "used support card":
                    return jsonDeserializationContext.deserialize(jsonObject, smUsedSupportCard.class);
                case "used character card":
                    return jsonDeserializationContext.deserialize(jsonObject, smUsedCharacterCard.class);
                case "students in dining hall":
                    return jsonDeserializationContext.deserialize(jsonObject, smStudentsInDiningHall.class);
                case "coins":
                    return jsonDeserializationContext.deserialize(jsonObject, smCoins.class);
                case "students on island":
                    return jsonDeserializationContext.deserialize(jsonObject, smStudentsOnIsland.class);
                case "students in hall":
                    return jsonDeserializationContext.deserialize(jsonObject, smStudentsInHall.class);
                case "teacher assignment":
                    return jsonDeserializationContext.deserialize(jsonObject, smTeacherAssignment.class);
                case "mother position":
                    return jsonDeserializationContext.deserialize(jsonObject, smMotherPosition.class);
                case "tower color":
                    return jsonDeserializationContext.deserialize(jsonObject, smTowerColor.class);
                case "merge":
                    return jsonDeserializationContext.deserialize(jsonObject, smMerge.class);
                case "chosen cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, smChosenCloud.class);
                case "results":
                    return jsonDeserializationContext.deserialize(jsonObject, smResults.class);
                case "students on card":
                    return jsonDeserializationContext.deserialize(jsonObject, smStudentsOnCard.class);
                case "block on island":
                    return jsonDeserializationContext.deserialize(jsonObject, smBlockOnIsland.class);
                case "blocks on card":
                    return jsonDeserializationContext.deserialize(jsonObject, smBlockOnCard.class);
                case "available cc":
                    return jsonDeserializationContext.deserialize(jsonObject, smAvailableCharacterCards.class);
                case "current player":
                    return jsonDeserializationContext.deserialize(jsonObject, smCurrentPlayer.class);
                case "not valid support card":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotValidSupportCard.class);
                case "resume turn":
                    return jsonDeserializationContext.deserialize(jsonObject, smResumeTurn.class);
                case "notify status":
                    return jsonDeserializationContext.deserialize(jsonObject, smGameStatus.class);
                case "closeAll":
                    return jsonDeserializationContext.deserialize(jsonObject, smCloseThemAll.class);
                case "pong":
                    return jsonDeserializationContext.deserialize(jsonObject, smPongPort.class);
            }
        }
        return null;
    }

}

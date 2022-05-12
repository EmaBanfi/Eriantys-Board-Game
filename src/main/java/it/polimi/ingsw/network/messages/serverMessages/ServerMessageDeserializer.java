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
                case "notify used support card":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyUsedSupportCard.class);
                case "notify player order":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyPlayerOrder.class);
                case "notify used character card":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyUsedCharacterCard.class);
                case "notify students in dining hall":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyStudentsInDiningHall.class);
                case "notify coins":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyCoins.class);
                case "notify students on island":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyStudentsOnIsland.class);
                case "notify students in hall":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyStudentsInHall.class);
                case "notify teacher assignment":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyTeacherAssignment.class);
                case "notify mother position":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyMotherPosition.class);
                case "notify tower color":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyTowerColor.class);
                case "notify no tower color":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyNoTowerColor.class);
                case "notify merge":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyMerge.class);
                case "notify no merge":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyNoMerge.class);
                case "notify cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyCloud.class);
                case "notify next round":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyNextRound.class);
                case "notify last round":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyLastRound.class);
                case "notify end game":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyEndGame.class);
                case "notify results":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyResults.class);
                case "notify students on card":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyStudentsOnCard.class);
                case "notify block on island":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyBlockOnIsland.class);
                case "notify blocks on card":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyBlocksOnCard.class);
                case "notify removed color":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyRemovedColor.class);
                case "notify ignore tower":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyIgnoreTower.class);
                case "notify available character cards":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyAvailableCharacterCards.class);
                case "notify current player":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyCurrentPlayer.class);
                case "notify ignore color":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotifyIgnoreColor.class);
                case "not valid support card":
                    return jsonDeserializationContext.deserialize(jsonObject, smNotValidSupportCard.class);
            }
        }
        return null;
    }

}

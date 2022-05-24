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
                    return jsonDeserializationContext.deserialize(jsonObject, LoginFailedMessage.class);
                case "notify":
                    return jsonDeserializationContext.deserialize(jsonObject, Notify.class);
                case "nickname":
                    return jsonDeserializationContext.deserialize(jsonObject, AskNickname.class);
                case "status":
                    return jsonDeserializationContext.deserialize(jsonObject, AskGameStatus.class);
                case "used deck":
                    return jsonDeserializationContext.deserialize(jsonObject, UsedDeck.class);
                case "chosen tower":
                    return jsonDeserializationContext.deserialize(jsonObject, ChosenTower.class);
                case "students on cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, StudentsOnCloud.class);
                case "used support card":
                    return jsonDeserializationContext.deserialize(jsonObject, UsedSupportCard.class);
                case "player order":
                    return jsonDeserializationContext.deserialize(jsonObject, PlayerOrder.class);
                case "used character card":
                    return jsonDeserializationContext.deserialize(jsonObject, UsedCharacterCard.class);
                case "students in dining hall":
                    return jsonDeserializationContext.deserialize(jsonObject, StudentsInDiningHall.class);
                case "coins":
                    return jsonDeserializationContext.deserialize(jsonObject, Coins.class);
                case "students on island":
                    return jsonDeserializationContext.deserialize(jsonObject, StudentsOnIsland.class);
                case "students in hall":
                    return jsonDeserializationContext.deserialize(jsonObject, StudentsInHall.class);
                case "teacher assignment":
                    return jsonDeserializationContext.deserialize(jsonObject, TeacherAssignment.class);
                case "mother position":
                    return jsonDeserializationContext.deserialize(jsonObject, MotherPosition.class);
                case "tower color":
                    return jsonDeserializationContext.deserialize(jsonObject, TowerColor.class);
                case "merge":
                    return jsonDeserializationContext.deserialize(jsonObject, Merge.class);
                case "chosen cloud":
                    return jsonDeserializationContext.deserialize(jsonObject, ChosenCloud.class);
                case "results":
                    return jsonDeserializationContext.deserialize(jsonObject, Results.class);
                case "students on card":
                    return jsonDeserializationContext.deserialize(jsonObject, StudentsOnCard.class);
                case "block on island":
                    return jsonDeserializationContext.deserialize(jsonObject, BlockOnIsland.class);
                case "blocks on card":
                    return jsonDeserializationContext.deserialize(jsonObject, BlockOnCard.class);
                case "ignore tower":
                    return jsonDeserializationContext.deserialize(jsonObject, IgnoreTower.class);
                case "available cc":
                    return jsonDeserializationContext.deserialize(jsonObject, AvailableCharacterCards.class);
                case "current player":
                    return jsonDeserializationContext.deserialize(jsonObject, CurrentPlayer.class);
                case "ignore color":
                    return jsonDeserializationContext.deserialize(jsonObject, IgnoreColor.class);
                case "not valid support card":
                    return jsonDeserializationContext.deserialize(jsonObject, NotValidSupportCard.class);
                case "resume turn":
                    return jsonDeserializationContext.deserialize(jsonObject, ResumeTurn.class);
            }
        }
        return null;
    }

}

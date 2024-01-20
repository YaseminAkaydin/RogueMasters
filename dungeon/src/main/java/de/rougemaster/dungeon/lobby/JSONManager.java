package de.rougemaster.dungeon.lobby;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.GameState;
import de.rougemaster.dungeon.lobby.messageData.RoomMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class JSONManager<T> {

    private final TypeToken<DataContainer<T>> ContainerTypeToken;

    public JSONManager(TypeToken<DataContainer<T>> dataContainerTypeToken) {
        this.ContainerTypeToken = dataContainerTypeToken;
    }

    public T read (String jsonString) {
        Gson gson = new Gson();
            DataContainer<T> dataContainer = gson.fromJson(jsonString, ContainerTypeToken.getType());
            return dataContainer.getData();
    }

    public String write (T object) {
        Gson gson = new Gson();
        DataContainer<T> container = new DataContainer<>(object);
        return gson.toJson(container);
    }
}

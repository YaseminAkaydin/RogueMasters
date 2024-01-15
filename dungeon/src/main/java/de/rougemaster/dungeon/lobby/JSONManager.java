package de.rougemaster.dungeon.lobby;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.GameState;

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

    public static void main(String[] args) {
        JSONManager<GameState> jsonManager =new JSONManager<>(new TypeToken<>(){});
        Dungeon d = new Dungeon(10, 2);
        GameState gameState = new GameState(
                List.of(new PlayableCharacter())
                ,List.of()
                ,new Dungeon(10, 2).getRoomList()
                .stream()
                .map((Room::getRoomMessage))
                .toList());
        System.out.println(jsonManager.write(gameState));
    }
}

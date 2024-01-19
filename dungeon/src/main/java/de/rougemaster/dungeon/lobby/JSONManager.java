package de.rougemaster.dungeon.lobby;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.GameState;
import de.rougemaster.dungeon.lobby.messageData.RoomMessage;

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
                .map(RoomMessage::new)
                .toList());
        System.out.println(jsonManager.write(gameState));
    }

    /**
     * {
     *   "data": {
     *     "playerList": [
     *       {
     *         "level": 0,
     *         "experience": 0,
     *         "inventory": [],
     *         "maxHp": 0,
     *         "hp": 10,
     *         "attack": 10,
     *         "defense": 0,
     *         "id": 0
     *       }
     *     ],
     *     "enemyList": [],
     *     "roomList": [
     *       {
     *         "id": 11,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": -1,
     *           "East": 12,
     *           "South": -1,
     *           "West": -1
     *         }
     *       },
     *       {
     *         "id": 12,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": -1,
     *           "East": 13,
     *           "South": -1,
     *           "West": 11
     *         }
     *       },
     *       {
     *         "id": 13,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": -1,
     *           "East": 14,
     *           "South": -1,
     *           "West": 12
     *         }
     *       },
     *       {
     *         "id": 14,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": 15,
     *           "East": 16,
     *           "South": -1,
     *           "West": 13
     *         }
     *       },
     *       {
     *         "id": 15,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": -1,
     *           "East": -1,
     *           "South": 14,
     *           "West": -1
     *         }
     *       },
     *       {
     *         "id": 16,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": -1,
     *           "East": -1,
     *           "South": 17,
     *           "West": 14
     *         }
     *       },
     *       {
     *         "id": 17,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": 16,
     *           "East": -1,
     *           "South": 18,
     *           "West": -1
     *         }
     *       },
     *       {
     *         "id": 18,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": 17,
     *           "East": 19,
     *           "South": -1,
     *           "West": -1
     *         }
     *       },
     *       {
     *         "id": 19,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": 20,
     *           "East": -1,
     *           "South": -1,
     *           "West": 18
     *         }
     *       },
     *       {
     *         "id": 20,
     *         "item": {
     *           "extraPoints": 15,
     *           "id": 1,
     *           "name": "Die Bibel",
     *           "description": "Religious stuff."
     *         },
     *         "players": [],
     *         "adjacentRooms": {
     *           "North": -1,
     *           "East": -1,
     *           "South": 19,
     *           "West": -1
     *         }
     *       }
     *     ]
     *   }
     * }
     * */
}

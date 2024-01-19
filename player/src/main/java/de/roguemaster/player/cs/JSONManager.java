package de.roguemaster.player.cs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.roguemaster.player.cs.DataContainer;

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

    /**
     * EXAMPLE JSON:
     * {
     *   "playerList": [
     *     {
     *       "level": 0,
     *       "experience": 0,
     *       "inventory": [],
     *       "maxHp": 0,
     *       "hp": 10,
     *       "attack": 10,
     *       "defense": 0,
     *       "id": 0
     *     }
     *   ],
     *   "enemyList": [
     *     {
     *       "killCount": 0,
     *       "dangerLevel": 0,
     *       "maxHp": 7,
     *       "hp": 7,
     *       "attack": 5,
     *       "defense": 7,
     *       "id": 0
     *     }
     *   ],
     *   "roomList": [
     *     {
     *       "id": 1,
     *       "item": {
     *         "extraPoints": 15,
     *         "id": 1,
     *         "name": "Die Bibel",
     *         "description": "Religious stuff."
     *       },
     *       "players": [],
     *       "adjacentRooms": {
     *         "North": -1,
     *         "East": -1,
     *         "West": -1,
     *         "South": 2
     *       }
     *     },
     *     {
     *       "id": 2,
     *       "item": {
     *         "extraPoints": 15,
     *         "id": 1,
     *         "name": "Die Bibel",
     *         "description": "Religious stuff."
     *       },
     *       "players": [],
     *       "adjacentRooms": {
     *         "North": 1,
     *         "East": 3,
     *         "West": 7,
     *         "South": 4
     *       }
     *     }...
     *   ]
     * }
     */
}

package de.roguemaster.player;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        JSONManager<String> jsonManager =new JSONManager<>(new TypeToken<>(){});

        /*
        System.out.println(jsonManager.read(gameState));


        */

    }
    /**
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
     *     },
     *     {
     *       "level": 0,
     *       "experience": 0,
     *       "inventory": [],
     *       "maxHp": 0,
     *       "hp": 10,
     *       "attack": 10,
     *       "defense": 0,
     *       "id": 0
     *     },
     *     {
     *       "level": 0,
     *       "experience": 0,
     *       "inventory": [],
     *       "maxHp": 0,
     *       "hp": 10,
     *       "attack": 10,
     *       "defense": 0,
     *       "id": 0
     *     },
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
     *  "enemyList": [
     *     {
     *       "killCount": 0, --- brauch ich nicht
     *       "name": "Skelenton/Zombie/Boss", --- um typ abzuleiten
     *       "dangerLevel": 0, --- ist ok zum anzeigen
     *       "maxHp": 7, --- ist gut zum anzeigen
     *       "hp": 7, --- ist gut zum anzeigen
     *       "attack": 5, --- brauch ich nicht
     *       "defense": 7, --- brauch ich nicht
     *       "id": 0 --- id immer sinnvoll
     *     }
     *   ],
     *
     *   "roomList": [
     *     {
     *       "id": 1,
     *       "items": ein item,
     *       "characters": [], =
     *       "adjacentRooms": {
     *         "North": -1,
     *         "East": -1,
     *         "South": 2,
     *         "West": -1
     *       }
     *     },
     *     {
     *       "id": 2,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": 1,
     *         "East": 3,
     *         "South": -1,
     *         "West": -1
     *       }
     *     },
     *     {
     *       "id": 3,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": 4,
     *         "East": -1,
     *         "South": -1,
     *         "West": 2
     *       }
     *     },
     *     {
     *       "id": 4,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": 5,
     *         "East": -1,
     *         "South": 3,
     *         "West": -1
     *       }
     *     },
     *     {
     *       "id": 5,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": 6,
     *         "East": -1,
     *         "South": 4,
     *         "West": 10
     *       }
     *     },
     *     {
     *       "id": 6,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": -1,
     *         "East": 7,
     *         "South": 5,
     *         "West": 9
     *       }
     *     },
     *     {
     *       "id": 7,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": -1,
     *         "East": -1,
     *         "South": 8,
     *         "West": 6
     *       }
     *     },
     *     {
     *       "id": 8,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": 7,
     *         "East": -1,
     *         "South": -1,
     *         "West": -1
     *       }
     *     },
     *     {
     *       "id": 9,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": -1,
     *         "East": 6,
     *         "South": -1,
     *         "West": -1
     *       }
     *     },
     *     {
     *       "id": 10,
     *       "items": [],
     *       "characters": [],
     *       "adjacentRooms": {
     *         "North": -1,
     *         "East": 5,
     *         "South": -1,
     *         "West": -1
     *       }
     *     }
     *   ]
     * }
     */
}

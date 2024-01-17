package de.rougemaster.dungeon.lobby.messageData;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.dungeon.RoomCardinalDirection;
import de.rougemaster.dungeon.item.Item;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomMessage {
    private int id;
    private Item item;
    private EnemyMessage enemy;
    private List<PlayerMessage> players;
    private final Map<RoomCardinalDirection, Integer> adjacentRooms;

    public RoomMessage (Room room) {
        this(
                room.getId(),
                room.getItem(),
                getEnemies(room.getCharacters()),
                getPlayers(room.getCharacters()),
                room.getAdjacentRoom(RoomCardinalDirection.North) == null ? -1 : room.getAdjacentRoom(RoomCardinalDirection.North).getId(),
                room.getAdjacentRoom(RoomCardinalDirection.South) == null ? -1 : room.getAdjacentRoom(RoomCardinalDirection.South).getId(),
                room.getAdjacentRoom(RoomCardinalDirection.West) == null ? -1 : room.getAdjacentRoom(RoomCardinalDirection.West).getId(),
                room.getAdjacentRoom(RoomCardinalDirection.East) == null ? -1 : room.getAdjacentRoom(RoomCardinalDirection.East).getId()
        );
    }

    public RoomMessage(int id, Item item, EnemyMessage enemies, List<PlayerMessage> players, int roomNorth, int roomSouth, int roomWest, int roomEast) {
        this.id = id;
        this.item = item;
        this.enemy = enemies;
        this.players = players;
        this.adjacentRooms = new HashMap<>();
        adjacentRooms.put(RoomCardinalDirection.North, roomNorth);
        adjacentRooms.put(RoomCardinalDirection.South, roomSouth);
        adjacentRooms.put(RoomCardinalDirection.East, roomEast);
        adjacentRooms.put(RoomCardinalDirection.West, roomWest);
    }

    private static List<PlayerMessage> getPlayers(List<Character> characters){
        List<PlayerMessage> playerMessageList = new ArrayList<>();
        for(Character c : characters){
            if(c instanceof PlayableCharacter){
                playerMessageList.add(new PlayerMessage((PlayableCharacter) c));
            }
        }
        return playerMessageList;
    }

    private static EnemyMessage getEnemies(List<Character> characters){
        for(Character c : characters){
            if(c instanceof EnemyCharacter){
                return new EnemyMessage((EnemyCharacter)c);
            }
        }
        return null;
    }

    public void setEnemy(EnemyCharacter enemy){
        this.enemy = new EnemyMessage(enemy);
    }

    public void addPlayer(PlayableCharacter playableCharacter){
        players.add(new PlayerMessage(playableCharacter));
    }

    public int getId() {
        return id;
    }
}

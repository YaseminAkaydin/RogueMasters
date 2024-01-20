package de.rougemaster.dungeon.lobby.messageData;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;

public class EnemyMessage {
    String name;
    int dangerLevel;
    int maxHp;
    int hp;
    int attack;
    int defense;
    int id;

    int currentRoomId;

    public EnemyMessage (EnemyCharacter enemyCharacter){
        this(   getName(enemyCharacter),
                enemyCharacter.getDangerLevel(),
                enemyCharacter.getMaxHp(),
                enemyCharacter.getHp(),
                enemyCharacter.getAttack(),
                enemyCharacter.getDefense(),
                enemyCharacter.getId(),
                enemyCharacter.getCurrentRoom().getId());

    }

    private EnemyMessage(String name, int dangerLevel, int maxHp, int hp, int attack, int defense, int id, int currentRoomId) {
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.maxHp = maxHp;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.id = id;
        this.currentRoomId = currentRoomId;
    }

    private static String getName (EnemyCharacter enemyCharacter) {
        if(enemyCharacter instanceof Skeleton){
            return "Skeleton";
        }
        if (enemyCharacter instanceof Zombie) {
            return "Zombie";
        }
        if (enemyCharacter instanceof Devil){
            return "Devil";
        }
        throw new IllegalArgumentException("Enemytyp nicht bekannt");
    }
}


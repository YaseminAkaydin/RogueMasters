package de.roguemaster.thread.messageData;


public class EnemyMessage {
    String name;
    int dangerLevel;
    int maxHp;
    int hp;
    int attack;
    int defense;
    int id;

    int currentRoomId;


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

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getId() {
        return id;
    }
}


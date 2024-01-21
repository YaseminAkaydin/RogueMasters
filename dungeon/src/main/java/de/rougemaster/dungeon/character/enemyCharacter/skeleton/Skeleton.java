package de.rougemaster.dungeon.character.enemyCharacter.skeleton;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.DungeonRoom;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.dungeon.RoomCardinalDirection;

import java.util.List;
import java.util.Map;

public class Skeleton extends EnemyCharacter {

    private static final int ExpDropAmount = 20;
    private static final int boneAttackBonusDamage = 5;

    public Skeleton(int dangerLevel) {
        super(ExpDropAmount);
        id = ++idCounter;
        this.dangerLevel = dangerLevel;

        if(dangerLevel <= 0) {
            dangerLevel = 1;
        }
        if(dangerLevel > 8) {
            dangerLevel = 8;
        }

        if(dangerLevel < 5){
            this.attack= 2;
            this.defense=3;
            this.maxHp=3;
            this.hp=3;
            for(int i = dangerLevel; i>1; i--){
                attack += 3;
                defense += 4;
                maxHp +=  4;
                hp += 4;
            }
        }

        if(dangerLevel >= 5){
            this.attack= 14;
            this.defense=19;
            this.maxHp=19;
            this.hp=19;
            for(int i = dangerLevel; i>1; i--){
                attack += 5;
                defense += 5;
                maxHp +=  6;
                hp += 6;
            }
        }

    }

    /**
     * Skeleton does a "sword"-attack against a character.
     * @param character enemy character
     */
    public void swordAttack(Character character){
        int netDamage = this.attack - character.getDefense();
        character.setHp(character.getHp()-netDamage<0?0:netDamage);
    }

    /**
     * Increases the defense of the Skeleton for a round in a fight
     */
    public void defend() {
        this.defense *= 2;
    }

    /**
     * Lowers the defense of the skeleton for a round in a fight.
     * WARNING: Only called by FightManager, which ensures defending is handled correctly.
     */
    public void stopDefending(){
        this.defense /= 2;
    }

    /**
     * Skeleton does a "bone"-attack against a character.
     * @param character enemy character
     */
    public void boneAttack(Character character){
        int netDamage = this.attack + boneAttackBonusDamage*(dangerLevel/2) - character.getDefense();
        character.setHp(character.getHp()-netDamage<0?0:netDamage);
    }

    /**
     * Skeleton does a "bonesplosion"-attack against a character.
     * @param character enemy character
     */
    public void bonesplosionAttack(Character character){
        int netDamage = (int)(this.attack * 2.5) - character.getDefense();
        character.setHp(character.getHp()-netDamage<0?0:netDamage);
        this.setHp(0);
    }

    /**
     * Teleports the Skelton after a fight into a room, where no Characters are in
     */
    public void skeletonRoam(){
        this.teleport(this.getCurrentRoom().getAdjacentRooms().get(0));
    }
}

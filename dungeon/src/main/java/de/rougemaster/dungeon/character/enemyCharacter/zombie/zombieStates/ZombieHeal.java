package de.rougemaster.dungeon.character.enemyCharacter.zombie.zombieStates;

import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.ZombieState;

public class ZombieHeal implements ZombieState {
    @Override
    public ZombieState fight(Zombie zombie) {
        return new ZombieBite();
    }

    @Override
    public ZombieState roam() {
        return new ZombieRoamWait();
    }
}

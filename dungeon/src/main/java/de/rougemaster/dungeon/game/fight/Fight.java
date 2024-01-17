package de.rougemaster.dungeon.game.fight;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.characterExceptions.InventoryItemMissingException;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import lombok.Getter;

import java.util.function.Consumer;

public class Fight {
    @Getter
    private final Character combatantOne;
    @Getter
    private final Character combatantTwo;
    private final Consumer<CombatAction> combatantOneExecutor;
    private final Consumer<CombatAction> combatantTwoExecutor;

    private CombatAction combatantOneAction;
    private CombatAction combatantTwoAction;

    public Fight(Character combatantOne, Character combatantTwo) {
        this.combatantOne = combatantOne;
        this.combatantTwo = combatantTwo;
        this.combatantOneExecutor = determineExecutor(combatantOne);
        this.combatantTwoExecutor = determineExecutor(combatantTwo);

        this.combatantOneAction = CombatAction.DO_NOTHING;
        this.combatantTwoAction = CombatAction.DO_NOTHING;
    }

    /**
     * Determines which internal executor is to be used.
     *
     * @param combatant character for which the executor needs to be determined
     * @return executor for given combatant
     */
    private Consumer<CombatAction> determineExecutor(Character combatant) {
        if (combatant instanceof PlayableCharacter) {
            return action -> executePlayerAction((PlayableCharacter) combatant, action);
        } else if (combatant instanceof Skeleton) {
            return action -> executeSkeletonAction((Skeleton) combatant, action);
        } else if (combatant instanceof Zombie) {
            return action -> executeZombieAction((Zombie) combatant, action);
        } else if (combatant instanceof Devil) {
            return action -> executeDevilAction((Devil) combatant, action);
        } else {
            return action -> {
            };
        }
    }

    /**
     * Resets both holders. Only used after the turn is executed.
     */
    private void resetActions() {
        this.combatantOneAction = CombatAction.DO_NOTHING;
        this.combatantTwoAction = CombatAction.DO_NOTHING;
    }


    /**
     * Fight logic of the next turn. Handles attacking, defending and using items.
     */
    public void executeTurn() {
        if (combatantTwoAction == CombatAction.DEFEND) {
            combatantTwoExecutor.accept(combatantTwoAction);
            combatantOneExecutor.accept(combatantOneAction);
            combatantTwoExecutor.accept(CombatAction.STOP_DEFENDING);
        } else if (combatantOneAction == CombatAction.DEFEND) {
            combatantOneExecutor.accept(combatantOneAction);
            combatantTwoExecutor.accept(combatantTwoAction);
            combatantOneExecutor.accept(CombatAction.STOP_DEFENDING);
        } else {
            combatantOneExecutor.accept(combatantOneAction);
            combatantTwoExecutor.accept(combatantTwoAction);
        }

        resetActions();
    }

    /**
     * The executor of all possible player actions.
     *
     * @param player the player
     * @param action action to be executed
     */
    private void executePlayerAction(PlayableCharacter player, CombatAction action) {
        Character enemy = (combatantOne == player) ? combatantTwo : combatantOne;
        //TODO: Implement fleeing

        switch (action) {
            case DO_NOTHING:
                break;
            case ATTACK:
                player.attackUsingEquipment(enemy);
                break;
            case DEFEND:
                player.defend();
                break;
            case STOP_DEFENDING:
                player.stopDefending();
                break;
            case USE_ITEM:
                //TODO: Implement using an item
//                try {
//                } catch (InventoryItemMissingException e) {
//                    // Handle exception
//                }
                break;

        }
    }

    /**
     * The executor of all possible skeleton actions.
     *
     * @param skeleton the player
     * @param action   action to be executed
     */
    private void executeSkeletonAction(Skeleton skeleton, CombatAction action) {
        Character player = (combatantOne == skeleton) ? combatantTwo : combatantOne;

        switch (action) {
            case DO_NOTHING:
                break;
            case SKELETON_SWORD_ATTACK:
                if (player != null) {
                    skeleton.swordAttack(player);
                }
                break;
            case SKELETON_BONE_ATTACK:
                if (player != null) {
                    skeleton.boneAttack(player);
                }
                break;
            case SKELETON_BONESPLOSION_ATTACK:
                if (player != null) {
                    skeleton.bonesplosionAttack(player);
                }
                break;
            case DEFEND:
                skeleton.defend();
                break;
            case STOP_DEFENDING:
                skeleton.stopDefending();
                break;

        }
    }

    /**
     * The executor of all possible zombie actions.
     *
     * @param zombie the player
     * @param action action to be executed
     */
    private void executeZombieAction(Zombie zombie, CombatAction action) {
        Character player = (combatantOne == zombie) ? combatantTwo : combatantOne;

        switch (action) {
            case DO_NOTHING:
                break;
            case ZOMBIE_CLAW_ATTACK:
                if (player != null) {
                    zombie.clawAttack(player);
                }
                break;
            case ZOMBIE_BITE_ATTACK:
                if (player != null) {
                    zombie.biteAttack(player);
                }
                break;
        }
    }

    /**
     * The executor of all possible devil actions.
     *
     * @param devil  the player
     * @param action action to be executed
     */
    private void executeDevilAction(Devil devil, CombatAction action) {
        Character player = (combatantOne == devil) ? combatantTwo : combatantOne;

        switch (action) {
            case DO_NOTHING:
                break;
            case DEVIL_FLAME_SWORD_ATTACK:
                if (player != null) {
                    devil.flameSwordAttack(player);
                }
                break;
            case DEVIL_SPIKE_SHIELD:
                devil.SpikeShield();
                if (player == combatantOne && combatantOneAction == CombatAction.ATTACK){
                    //TODO: how much damage?
                    player.setHp(player.getHp()-3);
                }
                break;
            case DEVIL_PLAYER_KILLER_ATTACK:
                if (player != null) {
                    if((player == combatantOne && combatantOneAction!=CombatAction.DO_NOTHING) || (player == combatantTwo && combatantTwoAction!=CombatAction.DO_NOTHING)){
                        devil.playerKillerAttack(player);
                    }
                }
                break;
            case DEFEND:
                devil.defend();
                break;
            case STOP_DEFENDING:
                devil.stopDefending();
                break;
        }
    }


    public void setCombatantAction(Character combatant, CombatAction action){
        if(combatant == combatantOne){
            setCombatantOneAction(action);
        }else {
            setCombatantTwoAction(action);
        }
    }
    /**
     * Sets the combat action of combatantOne, which will be executed in the next turn.
     *
     * @param combatantOneAction enum CombatAction
     */
    public void setCombatantOneAction(CombatAction combatantOneAction) {
        this.combatantOneAction = combatantOneAction;
    }

    /**
     * Sets the combat action of combatantTwo, which will be executed in the next turn.
     *
     * @param combatantTwoAction enum CombatAction
     */
    public void setCombatantTwoAction(CombatAction combatantTwoAction) {
        this.combatantTwoAction = combatantTwoAction;
    }

}

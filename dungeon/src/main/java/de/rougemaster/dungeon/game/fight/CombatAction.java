package de.rougemaster.dungeon.game.fight;

//TODO: add item usage for players
public enum CombatAction {
    // Generic actions
    DO_NOTHING,
    DEFEND,
    FLEE,
    STOP_DEFENDING,

    // Player specific actions
    ATTACK,
    USE_ITEM,

    // Skeleton specific actions
    SKELETON_SWORD_ATTACK,
    SKELETON_BONE_ATTACK,
    SKELETON_BONESPLOSION_ATTACK,

    // Zombie specific actions
    ZOMBIE_CLAW_ATTACK,
    ZOMBIE_BITE_ATTACK,

    // Devil specific actions
    DEVIL_FLAME_SWORD_ATTACK,
    DEVIL_SPIKE_SHIELD,
    DEVIL_PLAYER_KILLER_ATTACK
}

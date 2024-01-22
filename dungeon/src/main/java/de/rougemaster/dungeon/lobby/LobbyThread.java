package de.rougemaster.dungeon.lobby;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.enemy.EnemyFacade;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.character.Character;

import java.util.List;

public class LobbyThread implements Runnable {
    Lobby lobby;
    Game game;

    public LobbyThread(Lobby lobby) {
        this.lobby = lobby;
        this.game = lobby.getGame();

    }

    @Override
    public void run() {
        lobby.startGame();
        while (true) {
            if (game.getPlayerList().isEmpty()) {
                /*sendDeleteToAllEnemies();*/
                lobby.getGame().getTurnManager().reset();
                LobbyFacade lobbyFacade = LobbyFacade.getInstance();
                lobbyFacade.lobbyBroker.getLobbyBrokerRegister().removeLobby(lobby.getLobbyId());
                Thread.currentThread().interrupt();
                break;
            }

            checkAndDeleteCharacter();
            game.getTurnManager().executeTurn();
            lobby.nextTurn();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void sendDeleteToAllEnemies(){
        for (Character character : game.getGameState().getEnemyList()) {
            if (character instanceof Skeleton) {
                EnemyFacade.getInstance().deleteEnemy(lobby.getClientID(character), LobbyCharType.Skeleton);
            }
            if (character instanceof Zombie) {
                EnemyFacade.getInstance().deleteEnemy(lobby.getClientID(character), LobbyCharType.Zombie);
            }
            if (character instanceof Devil) {
                EnemyFacade.getInstance().deleteEnemy(lobby.getClientID(character), LobbyCharType.Devil);
            }
        }
    }

    /**
     * Checks if a character is dead and removes it from the game
     */
    public void checkAndDeleteCharacter() {
        EnemyFacade enemyFacade = EnemyFacade.getInstance();
        List<Character> charactersToKill = game.searchAndRemoveAllDeadPlayableCharacters();

        for (Character character : charactersToKill) {
            lobby.killCharacter(character);
            if (character instanceof EnemyCharacter) {
                if (character instanceof Skeleton) {
                    enemyFacade.deleteAndRequestEnemy(lobby.getClientID(character), lobby.getLobbyId(), LobbyCharType.Skeleton);
                }
                if (character instanceof Zombie) {
                    enemyFacade.deleteAndRequestEnemy(lobby.getClientID(character), lobby.getLobbyId(), LobbyCharType.Zombie);
                }
                if (character instanceof Devil) {
                    enemyFacade.deleteEnemy(lobby.getClientID(character), LobbyCharType.Devil);
                }
            }
            LobbyFacade.getInstance().unregisterUser(lobby.getClientID(character));
        }
    }

}


package de.rougemaster.dungeon.lobby;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.enemy.EnemyFacade;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.character.Character;

import java.util.List;

import static java.lang.System.currentTimeMillis;

public class LobbyThread implements Runnable{
    Lobby lobby;

    //TODO: Testen !!!
    public LobbyThread(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void run() {
        lobby.startGame();
        while (true) {
            long startTime = currentTimeMillis(); // Aufnahme der Startzeit
            Game game = lobby.getGame();
            List<Character> charactersToKill = game.searchAndRemoveAllDeadPlayableCharacters();
            EnemyFacade enemyFacade= EnemyFacade.getInstance();
            for (Character character: charactersToKill) {
                lobby.killCharacter(character);
                if(character instanceof EnemyCharacter){
                    if(character instanceof Skeleton){
                        enemyFacade.requestEnemy(lobby.getLobbyId(),LobbyCharType.Skeleton);
                    } else if (character instanceof Zombie) {
                        enemyFacade.requestEnemy(lobby.getLobbyId(),LobbyCharType.Zombie);
                    }
                }
                int clientId = lobby.getClienID(character);
                LobbyFacade.getInstance().unregisterUser(clientId);
            }
            game.getTurnManager().executeTurn();

            long endTime = currentTimeMillis(); // Aufnahme der Endzeit

            long executionTime = endTime - startTime; // Berechnung der Ausführungszeit

            /*System.out.println("ExecuteTurn() hat " + executionTime + " Millisekunden gedauert.");*/
            lobby.nextTurn();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}


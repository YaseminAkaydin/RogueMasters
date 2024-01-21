package de.rougemaster.dungeon.lobby;

import de.rougemaster.dungeon.enemy.EnemyFacade;
import de.rougemaster.dungeon.game.Game;

public class LobbyFactory {

        public Lobby createLobby() {
            Game game = new Game(10,2);
            Lobby lobby = new Lobby(game);

            //Find a valid lobbyId for the lobby
            int lobbyId = 0;
            boolean lobbyIdValid = false;
            do{
                lobbyId = LobbyBroker.getLobbyBroker().getLobbyBrokerRegister().generateValidLobbyId();
                lobbyIdValid = LobbyBroker.getLobbyBroker().getLobbyBrokerRegister().registerLobby(lobbyId, lobby);
            }while (!lobbyIdValid);
            lobby.setLobbyId(lobbyId);

            /*EnemyFacade enemyFacade = EnemyFacade.getInstance();
            enemyFacade.requestEnemy(lobbyId, LobbyCharType.Devil);*/
            /*enemyFacade.requestEnemy(lobbyId, LobbyCharType.Skeleton);
            enemyFacade.requestEnemy(lobbyId, LobbyCharType.Zombie);*/

            return lobby;
        }
}

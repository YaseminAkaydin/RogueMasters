package de.roguemaster.facade;

import de.roguemaster.enemy.Enemy;
import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.skeleton.Skeleton;
import de.roguemaster.enemy.zombie.Zombie;
import de.roguemaster.handler.EnemyHandler;

import java.util.HashMap;

public class EnemyRESTFacade {
    private static EnemyRESTFacade instance = null;
    private final HashMap<Integer, EnemyHandler> map;

    private final int port = 8812;

    private EnemyRESTFacade() {
        map = new HashMap<>();
    }

    /**
     * returns the instance of EnemyRESTFacade
     * @return instance of EnemyRESTFacade
     */
    public static EnemyRESTFacade getInstance() {
        if (instance == null) {
            instance = new EnemyRESTFacade();
        }
        return instance;
    }

    /**
     * creates an enemy and adds it to the map
     * @param lobbyID lobbyID of the lobby the enemy should be added to
     * @param typ type of the enemy
     */
    public void createEnemy(int lobbyID,EnemyTyp typ) {
        Enemy enemy = switch (typ) {
            case Devil -> new Devil();
            case Zombie -> new Zombie();
            case Skeleton -> new Skeleton();
            default -> throw new IllegalArgumentException("Invalid enemy type: " + typ);};

        EnemyHandler enemyHandler = new EnemyHandler(enemy,lobbyID ,port);
        System.out.println("Starting initialization of enemy: " + typ.toString().toLowerCase() + " in lobby: " + lobbyID);
        enemyHandler.initializeConnection();
        int counter = 0;

        while(enemyHandler.getClientID() == 0) {
            try {
                    counter++;
                    if(counter > 10) {
                    System.out.println("Enemy could not connect to server");
                    return;
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        map.put(enemyHandler.getClientID(),enemyHandler);
    }

    /**
     * deletes an enemy from the map
     * @param clientID clientID of the enemy that should be deleted
     */
    public void deleteEnemy(int clientID) {
        EnemyHandler enemyHandler = map.get(clientID);
        enemyHandler.shutdown();
        map.remove(clientID);
    }
}

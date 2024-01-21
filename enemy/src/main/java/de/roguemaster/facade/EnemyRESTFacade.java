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

    private final int port = 50518;

    private EnemyRESTFacade() {
        map = new HashMap<>();
    }

    public static EnemyRESTFacade getInstance() {
        if (instance == null) {
            instance = new EnemyRESTFacade();
        }
        return instance;
    }

    public void createEnemy(int lobbyID,EnemyTyp typ) {
        Enemy enemy = switch (typ) {
            case DEVIL -> new Devil();
            case ZOMBIE -> new Zombie();
            case SKELETON -> new Skeleton();
            default -> throw new IllegalArgumentException("Invalid enemy type: " + typ);};

        EnemyHandler enemyHandler = new EnemyHandler(enemy,lobbyID ,port);
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

    public void deleteEnemy(int clientID) {
        EnemyHandler enemyHandler = map.get(clientID);
        enemyHandler.shutdown();
        map.remove(clientID);
    }
}

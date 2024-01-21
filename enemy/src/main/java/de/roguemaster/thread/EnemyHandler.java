package de.roguemaster.thread;

import com.example.grpc.*;
import com.google.gson.reflect.TypeToken;
import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;
import de.roguemaster.thread.messageData.EnemyMessage;
import de.roguemaster.thread.messageData.GameStateMessage;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

import java.util.List;
import java.util.Map;


public class EnemyHandler {
    Enemy enemy;

    private final int lobbyID;
    private int mobID;
    private int clientID;

    private final String port;

    ManagedChannel asyncChannel;
    ManagedChannel blockingChannel;

    GrpcEnemyClient grpcEnemieClient;

    public EnemyHandler(Enemy enemy, int lobbyID, String port) {
        this.lobbyID = lobbyID;
        this.port = port;

        asyncChannel = Grpc.newChannelBuilder("localhost:" + port, InsecureChannelCredentials.create()).build();
        blockingChannel = Grpc.newChannelBuilder("localhost:" + port, InsecureChannelCredentials.create()).build();
    }

    public void createConnection() {
        grpcEnemieClient = new GrpcEnemyClient(lobbyID,blockingChannel,asyncChannel,this);
    }


    public void initializeConnection () {
        createConnection();
        tryConnecting();
        grpcEnemieClient.initStream();
        grpcEnemieClient.sendCommand(new CommandHolder("initialize", ""));
    }

    public void nextCommand(GameCommandResponse response) {
        JSONManager<GameStateMessage> jsonManager = new JSONManager<>(new TypeToken<>(){});
        GameStateMessage gameStateMessage = jsonManager.read(response.getMessage());
        EnemyMessage selfEnemyMesssage =  searchSelfEnemyMessage(gameStateMessage.getEnemyList());

        if(selfEnemyMesssage == null || selfEnemyMesssage.getHp() <= 0) {
            System.out.println("Enemy is dead");
            //TODO: SELFDESTRUCTIONG VIA ENEMYFACADE
            return;
        }

        //Update Enemy Stats from Server
        enemy.setHp(selfEnemyMesssage.getHp());
        enemy.setMaxHp(selfEnemyMesssage.getMaxHp());

        //Send Next Command
        CommandHolder nextCommand = enemy.getNextTurn(gameStateMessage.getFightMap().containsKey(mobID));
        grpcEnemieClient.sendCommand(nextCommand);
    }

    public void tryConnecting() {
        int counter = 0;
        JoinLobbyResponse joinResponse;
        do{
            joinResponse = grpcEnemieClient.sendJoinLobbyRequest(lobbyID);
            this.mobID = joinResponse.getCharacterID();
            this.clientID = joinResponse.getUserID();
            counter++;
            if(counter > 10) {
                System.out.println("Couldn't Connect");
            }
        }while(!joinResponse.getSuccess());
    }

    private EnemyMessage searchSelfEnemyMessage (List<EnemyMessage> enemyMessageList){
        for(EnemyMessage enemyMessage : enemyMessageList) {
            if(enemyMessage.getId() == mobID) {
                return enemyMessage;
            }
        }
        return null;
    }

    public int getClientID() {
        return clientID;
    }

    public void shutdown() {
        grpcEnemieClient.shutdown();
    }
}

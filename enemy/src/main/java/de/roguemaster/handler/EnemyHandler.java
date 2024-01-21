package de.roguemaster.handler;

import com.example.grpc.*;
import com.google.gson.reflect.TypeToken;
import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;
import de.roguemaster.facade.EnemyTyp;
import de.roguemaster.handler.messageData.EnemyMessage;
import de.roguemaster.handler.messageData.GameStateMessage;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

import java.util.List;


public class EnemyHandler {
    Enemy enemy;

    private final int lobbyID;
    private int mobID;
    private int clientID;
    private boolean success;

    private final int port;

    ManagedChannel asyncChannel;
    ManagedChannel blockingChannel;

    GrpcEnemyClient grpcEnemieClient;

    public EnemyHandler(Enemy enemy, int lobbyID, int port) {
        this.lobbyID = lobbyID;
        this.enemy = enemy;
        this.port = port;

        asyncChannel = Grpc.newChannelBuilder("localhost:" + port, InsecureChannelCredentials.create()).build();
        blockingChannel = Grpc.newChannelBuilder("localhost:" + port, InsecureChannelCredentials.create()).build();
    }

    public void createConnection() {
        grpcEnemieClient = new GrpcEnemyClient(lobbyID,blockingChannel,asyncChannel,this);
    }


    public void initializeConnection () {
        createConnection();
        tryConnecting(enemy.getTyp());
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

    public void tryConnecting(EnemyTyp typ) {
        int counter = 0;
        JoinLobbyResponse joinResponse;
        do{
            System.out.println("Sending " + counter + " join request for " + typ.toString());
            joinResponse = grpcEnemieClient.sendJoinLobbyRequest(lobbyID, typ.toString());
            this.mobID = joinResponse.getCharacterID();
            this.clientID = joinResponse.getUserID();
            this.success = joinResponse.getSuccess();
            counter++;
            if(counter > 10) {
                System.out.println("Couldn't Connect");
                break;
            }
        }while(!joinResponse.getSuccess());
    }

    private EnemyMessage searchSelfEnemyMessage(List<EnemyMessage> enemyMessageList){
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

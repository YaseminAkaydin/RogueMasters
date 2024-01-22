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
    private int charID;
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

    /**
     * Initializes the Connection to the Server
     */
    public void initializeConnection () {
        createConnection();
        tryConnecting(enemy.getTyp());
        grpcEnemieClient.initStream();
        grpcEnemieClient.sendCommand(new CommandHolder("initialize", ""));
    }

    /**
     * Sends the next Command to the Server
     * @param response Response from the Server
     */
    public void nextCommand(GameCommandResponse response) {
        JSONManager<GameStateMessage> jsonManager = new JSONManager<>(new TypeToken<>(){});
        GameStateMessage gameStateMessage = jsonManager.read(response.getMessage());
        EnemyMessage selfEnemyMesssage =  searchSelfEnemyMessage(gameStateMessage.getEnemyList());
        if(selfEnemyMesssage == null || selfEnemyMesssage.getHp() <= 0) {
            System.out.println("Enemy is dead"); // TODO: ???
            return;
        }

        //Update Enemy Stats from Server
        enemy.setHp(selfEnemyMesssage.getHp());
        enemy.setMaxHp(selfEnemyMesssage.getMaxHp());

        //Send Next Command
        CommandHolder nextCommand = enemy.getNextTurn(gameStateMessage.getFightMap().containsKey(charID));
        grpcEnemieClient.sendCommand(nextCommand);
    }

    /**
     * Tries to connect to the server
     * @param typ Type of the Enemy that should be connected
     * */
    public void tryConnecting(EnemyTyp typ) {
        int counter = 0;
        JoinLobbyResponse joinResponse;
        do{
            System.out.println("Sending " + counter + " join request for " + typ.toString());
            joinResponse = grpcEnemieClient.sendJoinLobbyRequest(lobbyID, typ.toString());
            this.charID = joinResponse.getCharacterID();
            this.success = joinResponse.getSuccess();
            this.clientID = joinResponse.getUserID();
            grpcEnemieClient.setClientID(clientID);
            counter++;
            if(counter > 10) {
                System.out.println("Couldn't Connect");
                break;
            }
        }while(!joinResponse.getSuccess());
    }

    /**
     * Searches for the EnemyMessage in a List of EnemyMessages with the same id
     * @param enemyMessageList List of EnemyMessages
     * @return EnemyMessage with the same id as the EnemyHandler
     */
    private EnemyMessage searchSelfEnemyMessage(List<EnemyMessage> enemyMessageList){
        for(EnemyMessage enemyMessage : enemyMessageList) {
            if(enemyMessage.getId() == charID) {
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

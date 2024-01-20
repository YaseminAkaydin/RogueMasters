package de.roguemaster.thread;

import com.example.grpc.*;
import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;



public class EnemyHandler {
    Enemy enemy;

    private final int lobbyID;
    private int mobID;
    private int clientID;

    private final String port;

    ManagedChannel asyncChannel;
    ManagedChannel blockingChannel;

    GrpcEnemyClient grpcEnemieClient;

    public EnemyHandler(Enemy enemy, int lobbyID, int mobID, String port) {
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

    public void nextCommand() {
        //TODO: getNextTurn muss know if Enemy is in a Fight
        CommandHolder nextCommand = enemy.getNextTurn(false);
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
}

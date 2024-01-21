package de.rougemaster.dungeon.enemy;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacterFactory;
import de.rougemaster.dungeon.lobby.LobbyCharType;

public class EnemyFacade {
    private static EnemyFacade instance = null;


    private EnemyFacade() {
    }

    public static EnemyFacade getInstance() {
        if (instance == null) {
            instance = new EnemyFacade();
        }
        return instance;
    }

    public void requestEnemy(int lobbyId, LobbyCharType enemyTyp) {
        HttpClient client = HttpClient.newHttpClient();
        String url = "http://localhost:8080/enemy/devil/" + lobbyId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*case (EnemyCharacterFactory.EnemyTyp.Devil):
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/enemy/devil/" + lobbyId))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        case (EnemyCharacterFactory.EnemyTyp
                .Zombie):
        case (EnemyCharacterFactory.EnemyTyp
                .Skeleton):
        default -> throw new IllegalArgumentException("Invalid enemy type: " + enemyTyp);*/
    }
}

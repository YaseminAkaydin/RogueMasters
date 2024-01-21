package de.roguemaster.spring;

import de.roguemaster.facade.EnemyRESTFacade;
import de.roguemaster.facade.EnemyTyp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enemy/skeleton")
public class SkeletonCDService {
    @PostMapping(value = "/")
    public ResponseEntity<Object> createSkeleton(@RequestBody int lobbyId, @RequestBody int port) {
        EnemyRESTFacade.getInstance().createEnemy(port,lobbyId, EnemyTyp.SKELETON);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteSkeleton(int clientID, int port ) {
        //TODO: check if clientID is valid and lobby is correct
        EnemyRESTFacade.getInstance().deleteEnemy(port,clientID);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello World");
    }
}

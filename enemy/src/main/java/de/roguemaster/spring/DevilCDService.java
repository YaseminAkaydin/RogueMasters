package de.roguemaster.spring;

import de.roguemaster.facade.EnemyRESTFacade;
import de.roguemaster.facade.EnemyTyp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enemy/devil")
public class DevilCDService {
    @PostMapping(value = "/")
    public ResponseEntity<Object> createSkeleton(@RequestBody int lobbyId, @RequestBody int port) {
        EnemyRESTFacade.getInstance().createEnemy(port,lobbyId, EnemyTyp.DEVIL);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteSkeleton(@RequestBody int clientID,@RequestBody int port) {
        //TODO: check if clientID is valid and lobby is correct
        EnemyRESTFacade.getInstance().deleteEnemy(port,clientID);
        return ResponseEntity.ok().build();
    }
}

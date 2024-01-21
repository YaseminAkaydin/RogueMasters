package de.roguemaster.spring;

import de.roguemaster.facade.EnemyRESTFacade;
import de.roguemaster.facade.EnemyTyp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/enemy/skeleton")
public class SkeletonCDService {
    @PostMapping(value = "/{lobbyId}")
    public ResponseEntity<Object> createSkeleton(@PathVariable("lobbyId") String lobbyId) {
        EnemyRESTFacade.getInstance().createEnemy(Integer.parseInt(lobbyId), EnemyTyp.Skeleton);
        return ResponseEntity.ok().build();
    }
    //localhost:8099/enemy/skeleton/18525

    @DeleteMapping("/client/{clientID}")
    public ResponseEntity<Object> deleteSkeleton(@PathVariable("clientID") String clientID) {
        //TODO: check if clientID is valid and lobby is correct
        EnemyRESTFacade.getInstance().deleteEnemy(Integer.parseInt(clientID));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello World");
    }
}

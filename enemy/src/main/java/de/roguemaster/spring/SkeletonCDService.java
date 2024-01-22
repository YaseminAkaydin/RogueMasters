package de.roguemaster.spring;

import de.roguemaster.facade.EnemyRESTFacade;
import de.roguemaster.facade.EnemyTyp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/enemy/skeleton")
public class SkeletonCDService {

    /**
     * Creates a new Skeleton
     * @param lobbyId the lobby where the Skeleton should be created
     * @return 200 if the Skeleton was created
     */
    @PostMapping(value = "/create/{lobbyId}")
    public ResponseEntity<Object> createSkeleton(@PathVariable("lobbyId") String lobbyId) {
        EnemyRESTFacade.getInstance().createEnemy(Integer.parseInt(lobbyId), EnemyTyp.Skeleton);
        return ResponseEntity.ok().build();
    }
    //localhost:8080/enemy/skeleton/

    /**
     * Deletes a Skeleton
     * @param clientID the id of the Skeleton
     * @return 200 if the Skeleton was deleted
     */
    @DeleteMapping("/delete/{clientID}")
    public ResponseEntity<Object> deleteSkeleton(@PathVariable("clientID") String clientID) {
        //TODO: check if clientID is valid and lobby is correct
        EnemyRESTFacade.getInstance().deleteEnemy(Integer.parseInt(clientID));
        return ResponseEntity.ok().build();
    }
}

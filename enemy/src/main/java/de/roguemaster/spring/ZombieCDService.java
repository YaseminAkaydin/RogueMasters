package de.roguemaster.spring;

import de.roguemaster.facade.EnemyRESTFacade;
import de.roguemaster.facade.EnemyTyp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enemy/zombie")
public class ZombieCDService {

    /**
     * Creates a new Zombie
     * @param lobbyId the lobby where the Zombie should be created
     * @return 200 if the Zombie was created
     */
    @PostMapping(value = "/create/{lobbyId}")
    public ResponseEntity<Object> createSkeleton(@PathVariable("lobbyId") String lobbyId) {
        EnemyRESTFacade.getInstance().createEnemy(Integer.parseInt(lobbyId), EnemyTyp.Zombie);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a Zombie
     * @param clientID the id of the Zombie
     * @return 200 if the Zombie was deleted
     */
    @DeleteMapping("/delete/{clientID}")
    public ResponseEntity<Object> deleteSkeleton(@PathVariable("lobbyId") String lobbyId,@PathVariable("clientID") String clientID) {
        //TODO: check if clientID is valid and lobby is correct
        EnemyRESTFacade.getInstance().deleteEnemy(Integer.parseInt(clientID));
        return ResponseEntity.ok().build();
    }
}

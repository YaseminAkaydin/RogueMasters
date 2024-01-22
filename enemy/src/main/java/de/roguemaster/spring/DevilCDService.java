package de.roguemaster.spring;

import de.roguemaster.facade.EnemyRESTFacade;
import de.roguemaster.facade.EnemyTyp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enemy/devil")
public class DevilCDService {
    /**
     * Creates a new Devil
     * @param lobbyId the lobby where the Devil should be created
     * @return 200 if the Devil was created
     */
    @PostMapping(value = "/create/{lobbyId}")
    public ResponseEntity<Object> createSkeleton(@PathVariable("lobbyId") String lobbyId) {
        EnemyRESTFacade.getInstance().createEnemy(Integer.parseInt(lobbyId), EnemyTyp.Devil);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a Devil
     * @param clientID the id of the Devil
     * @return 200 if the Devil was deleted
     */
    @DeleteMapping("/delete/{clientID}")
    public ResponseEntity<Object> deleteSkeleton(@PathVariable("clientID") String clientID) {
        //TODO: check if clientID is valid and lobby is correct
        EnemyRESTFacade.getInstance().deleteEnemy(Integer.parseInt(clientID));
        return ResponseEntity.ok().build();
    }
}

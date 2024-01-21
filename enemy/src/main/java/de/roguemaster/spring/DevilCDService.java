package de.roguemaster.spring;

import de.roguemaster.facade.EnemyRESTFacade;
import de.roguemaster.facade.EnemyTyp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enemy/devil")
public class DevilCDService {
    @PostMapping(value = "/{lobbyId}")
    public ResponseEntity<Object> createSkeleton(@PathVariable("lobbyId") String lobbyId) {
        EnemyRESTFacade.getInstance().createEnemy(Integer.parseInt(lobbyId), EnemyTyp.Devil);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/client/{clientID}")
    public ResponseEntity<Object> deleteSkeleton(@PathVariable("lobbyId") String lobbyId,@PathVariable("clientID") String clientID) {
        //TODO: check if clientID is valid and lobby is correct
        EnemyRESTFacade.getInstance().deleteEnemy(Integer.parseInt(clientID));
        return ResponseEntity.ok().build();
    }
}
